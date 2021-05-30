package api;

import com.google.gson.Gson;

import java.io.IOException;

public class Handler
{
    Request request;
    RequestPipeline requestPipeline;

    public Handler(Request request,RequestPipeline requestPipeline)
    {
        this.request = request;
        this.requestPipeline = requestPipeline;
    }

    public void setRequest(Request request)
    {
        this.request = request;
    }

    public void setRequestPipeline(RequestPipeline requestPipeline)
    {
        this.requestPipeline = requestPipeline;
    }

    public void send() throws IOException
    {
        Gson gson = new Gson();
        requestPipeline.getDataOutputStream().writeUTF(gson.toJson(request));
        requestPipeline.getDataOutputStream().flush();
    }
}
