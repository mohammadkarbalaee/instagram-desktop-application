package application.requestahandlers.followersfollowings;

import api.ApiHandler;
import api.Response;
import application.datacomponents.followerfollowing.FollowerFollowingPack;
import application.datamanagement.database.DatabaseManager;

import java.io.IOException;
import java.sql.SQLException;

public class FollowersHandler
{
    private static ApiHandler apiHandler;

    public static void build(ApiHandler newApiHandler)
    {
        apiHandler = newApiHandler;
    }

    public static void addFollower(FollowerFollowingPack pack) throws SQLException
    {
        DatabaseManager.setFollowerFollowing(pack);
    }

    public static void deliverFollowersQuantity(String username) throws IOException, SQLException
    {
        Response response = new Response(DatabaseManager.getFollowersQuantity(username).toString());
        apiHandler.answerToClient(response);
    }

    public static void deliverFollowingsQuantity(String username) throws IOException, SQLException
    {
        Response response = new Response(DatabaseManager.getFollowingsQuantity(username).toString());
        apiHandler.answerToClient(response);
    }
}
