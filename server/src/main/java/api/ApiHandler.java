package api;

import com.google.gson.Gson;

import java.io.IOException;

public class ApiHandler
{
    private final Gson gson = new Gson();
    private final RequestPipeline requestPipeline;

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
}
