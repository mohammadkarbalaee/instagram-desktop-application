package api;

import com.google.gson.Gson;
import application.search.SearchResult;

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
}
