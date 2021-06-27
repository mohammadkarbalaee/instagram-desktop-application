package application.requestahandlers.followersfollowings;

import api.ApiHandler;
import api.Response;
import application.datacomponents.followerfollowing.FollowerFollowingPack;
import application.datamanagement.database.DatabaseManager;

import java.io.IOException;
import java.sql.SQLException;

public class FollowersHandler
{
    private ApiHandler apiHandler;

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
}
