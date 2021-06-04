package api;

import com.google.gson.Gson;

import java.io.IOException;

public class ApiHandler
{
    RequestPipeline requestPipeline;
    Gson gson = new Gson();

    public ApiHandler(RequestPipeline requestPipeline) throws IOException
    {
        this.requestPipeline = requestPipeline;
    }

    public Request listen() throws IOException
    {
        return gson.fromJson(requestPipeline.getDataInputStream().readUTF(),Request.class);
    }

    public void sendResponse(Response response) throws IOException
    {
        requestPipeline.dataOutputStream.writeUTF(gson.toJson(response));
        requestPipeline.dataOutputStream.flush();
    }
}
