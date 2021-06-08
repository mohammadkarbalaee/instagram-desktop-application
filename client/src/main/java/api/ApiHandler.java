package api;

import com.google.gson.Gson;

import java.io.IOException;

public class ApiHandler
{
    private final Gson gson = new Gson();
    private Request request;

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
            return true;
        }
        else
        {
            return false;
        }
    }
}
