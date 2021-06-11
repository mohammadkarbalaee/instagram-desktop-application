package application.util;

import api.ApiHandler;
import api.Request;
import api.RequestPipeline;
import api.Response;
import application.datamanagement.DataManager;
import application.datamanagement.database.DatabaseManager;
import application.util.followerfollowing.FollowerFollowingPack;
import application.util.search.SearchResult;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class ClientHandler implements Runnable
{
    private final Socket socket;
    private ApiHandler apiHandler;
    private final Gson gson = new Gson();

    public ClientHandler(Socket socket) throws IOException
    {
        this.socket = socket;
        build();
    }

    private void build() throws IOException
    {
        RequestPipeline requestPipeline = new RequestPipeline(socket);
        apiHandler = new ApiHandler(requestPipeline);
    }

    @Override
    public void run()
    {
        try{ action();}
        catch (SQLException | IOException throwables) {throwables.printStackTrace();}
    }

    private void action() throws SQLException, IOException
    {
        while(true)
        {
            Request request = null;

            try {request = apiHandler.listenToClient();}
            catch (IOException e) {}

            assert request != null;
            if (request.getLabel().equals("IS_NEW"))
            {
                try
                {
                    if (!DatabaseManager.checkExistence(request.getBody()))
                    {
                        Response itsNew = new Response("true");
                        apiHandler.answerToClient(itsNew);
                    }
                }
                catch (SQLException | IOException throwables) {}
            }
            else if(request.getLabel().equals("SEND_USER"))
            {
                try
                {
                    DataManager.saveUser(gson.fromJson(request.getBody(), User.class));
                }
                catch (SQLException | IOException throwables) {}
            }
            else if (request.getLabel().equals("SEND_FOLLOWER"))
            {
                FollowerFollowingPack pack = gson.fromJson(request.getBody(),FollowerFollowingPack.class);
                DatabaseManager.setFollowerFollowing(pack);
            }
            else if(request.getLabel().equals("GET_SEARCH_RESULT"))
            {
                SearchResult searchResult = DatabaseManager.getSearchResult(request.getBody());
                if (searchResult == null)
                {
                    Response response = new Response("null");
                    apiHandler.answerToClient(response);
                }
                else
                {
                    Response response = new Response(gson.toJson(searchResult));
                    apiHandler.answerToClient(response);
                }
            }
            else if (request.getLabel().equals("GET_FOLLOWERS_COUNT"))
            {
                Response response = new Response(DatabaseManager.getFollowersQuantity(request.getBody()).toString());
                apiHandler.answerToClient(response);
            }
            else if (request.getLabel().equals("GET_FOLLOWINGS_COUNT"))
            {
                Response response = new Response(DatabaseManager.getFollowingsQuantity(request.getBody()).toString());
                apiHandler.answerToClient(response);
            }
        }
    }
}
