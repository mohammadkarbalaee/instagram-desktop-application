package api;

import application.User;
import com.google.gson.Gson;

import java.io.IOException;

public class Handler
{
    Response response;
    Request request;
    RequestPipeline requestPipeline;

    public Handler(RequestPipeline requestPipeline)
    {
        this.requestPipeline = requestPipeline;
    }

    public void listen() throws IOException
    {
        Gson gson = new Gson();
        request = gson.fromJson(requestPipeline.getDataInputStream().readUTF(),Request.class);
    }

    public String getLabel()
    {
        return request.getLabel();
    }

    public Object getObject()
    {
        Gson gson = new Gson();
        if (request.getLabel().equals("SEND_USER"))
        {
            return gson.fromJson(request.getSerializedObject(), User.class);
        }
        else
        {
            return null;
        }
    }
}
