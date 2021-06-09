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
    private final DatabaseManager databaseManager = new DatabaseManager();
    private final DataManager dataManager = new DataManager();
    private final Gson gson = new Gson();

    public ClientHandler(Socket socket) throws IOException, SQLException
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

            if (request.getLabel().equals("IS_NEW"))
            {
                try
                {
                    if (!databaseManager.checkExistence(request.getBody()))
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
                    dataManager.saveUser(gson.fromJson(request.getBody(), User.class));
                }
                catch (SQLException | IOException throwables) {}
            }
            else if (request.getLabel().equals("SEND_FOLLOWER"))
            {
                FollowerFollowingPack pack = gson.fromJson(request.getBody(),FollowerFollowingPack.class);
                databaseManager.setFollowerFollowing(pack);
            }
            else if(request.getLabel().equals("GET_SEARCH_RESULT"))
            {
                SearchResult searchResult = databaseManager.getSearchResult(request.getBody());
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
        }
    }
}
