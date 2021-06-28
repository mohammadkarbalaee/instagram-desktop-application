package application.requestahandlers.followersfollowings;

import api.ApiHandler;
import api.Response;
import application.datacomponents.followerfollowing.FollowerFollowingPack;
import application.datamanagement.database.DatabaseManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.SQLException;

public class FollowersHandler
{
    private ApiHandler apiHandler;
    private Gson gson = new Gson();

    public FollowersHandler(ApiHandler apiHandler)
    {
        this.apiHandler = apiHandler;
    }

    public void addFollower(FollowerFollowingPack pack) throws SQLException
    {
        DatabaseManager.setFollowerFollowing(pack);
    }

    public void deliverFollowersQuantity(String username) throws IOException, SQLException
    {
        Response response = new Response(DatabaseManager.getFollowersQuantity(username).toString());
        apiHandler.answerToClient(response);
    }

    public void deliverFollowingsQuantity(String username) throws IOException, SQLException
    {
        Response response = new Response(DatabaseManager.getFollowingsQuantity(username).toString());
        apiHandler.answerToClient(response);
    }

    public void deliverFollowings(String username) throws SQLException, IOException
    {
        Response response = new Response(DatabaseManager.getFollowings(username));
        apiHandler.answerToClient(response);
    }

    public void deliverFollowers(String username) throws IOException, SQLException
    {
        Response response = new Response(DatabaseManager.getFollowers(username));
        apiHandler.answerToClient(response);
    }

    public void deliverIsFollowed(String body) throws IOException, SQLException
    {
        FollowerFollowingPack pack = gson.fromJson(body,FollowerFollowingPack.class);
        Response response = new Response(DatabaseManager.isFollowed(pack.getUsername(),pack.getFollower()));
        apiHandler.answerToClient(response);
    }
}
