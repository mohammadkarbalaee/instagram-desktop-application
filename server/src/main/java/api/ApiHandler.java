package api;

import com.google.gson.Gson;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ApiHandler
{
    private final Gson gson = new Gson();
    private RequestPipeline requestPipeline = null;

    public ApiHandler(RequestPipeline requestPipeline)
    {
        this.requestPipeline = requestPipeline;
    }

    public Request listenToClient() throws IOException
    {
        return gson.fromJson(requestPipeline.getDataInputStream().readUTF(),Request.class);
    }

    public void answerToClient(Response response) throws IOException
    {
        requestPipeline.getDataOutputStream().writeUTF(gson.toJson(response));
        requestPipeline.getDataOutputStream().flush();
    }

    public BufferedImage getImage() throws IOException
    {
        return ImageIO.read(requestPipeline.getDataInputStream());
    }

    public void sendPhoto(BufferedImage postImage) throws IOException
    {
        byte[] byteData = toByteArray(postImage);
        requestPipeline.getDataOutputStream().writeInt(byteData.length);
        requestPipeline.getDataOutputStream().flush();
        requestPipeline.getDataOutputStream().write(byteData);
        requestPipeline.getDataOutputStream().flush();
    }

    public byte[] toByteArray(BufferedImage bufferedImage) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage,"png", baos);
        return baos.toByteArray();

    }
}
