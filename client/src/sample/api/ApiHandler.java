package sample.api;

import com.google.gson.Gson;
import sample.backend.directmessage.Message;
import sample.backend.post.Post;
import sample.backend.search.SearchResult;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ApiHandler
{
    private final Gson gson = new Gson();
    private Request request;

    public ApiHandler() {}

    public ApiHandler(Request request)
    {
        this.request = request;
    }

    public void setRequest(Request request)
    {
        this.request = request;
    }

    public void sendRequest() throws IOException
    {
        RequestPipeline.getDataOutputStream().writeUTF(gson.toJson(request));
        RequestPipeline.getDataOutputStream().flush();
    }

    public boolean receiveIS_NEWresponse() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = gson.fromJson(json,Response.class);
        String response = responseObject.getBody();
        if (response.equals("true"))
        {
            System.out.println(response);
            return true;
        }
        else
        {
            return false;
        }
    }

    public SearchResult receiveSearchResult() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = gson.fromJson(json,Response.class);
        String searchResultJson = responseObject.getBody();
        return gson.fromJson(searchResultJson,SearchResult.class);
    }

    public Integer receiveFollowerFollowingQuantity() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = gson.fromJson(json,Response.class);
        String body = responseObject.getBody();
        return Integer.parseInt(body);
    }

    public String receivePassword() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = gson.fromJson(json,Response.class);
        return responseObject.getBody();
    }

    public Integer receivePostsQuantity() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = gson.fromJson(json,Response.class);
        String body = responseObject.getBody();
        return Integer.parseInt(body);
    }

    public Post receiveWantedPost() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = gson.fromJson(json,Response.class);
        String body = responseObject.getBody();
        return gson.fromJson(body,Post.class);
    }

    public void sendPhoto(String location) throws IOException
    {
        File file = new File(location);
        BufferedImage bufferedImage = ImageIO.read(file);
        ImageIO.write(bufferedImage,"png",RequestPipeline.getDataOutputStream());
        bufferedImage.flush();
        RequestPipeline.getDataOutputStream().flush();
    }

    public BufferedImage receivePostPhoto() throws IOException
    {
        return ImageIO.read(RequestPipeline.getDataInputStream());
    }

    public Message[] receiveChatroomMessages() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = gson.fromJson(json,Response.class);
        String body = responseObject.getBody();
        String[] messageObjects = body.split("/");
        Message[] messages = new Message[messageObjects.length];
        for (int i = 0; i < messages.length; i++)
        {
            messages[i] = gson.fromJson(messageObjects[i],Message.class);
        }
        return messages;
    }
}