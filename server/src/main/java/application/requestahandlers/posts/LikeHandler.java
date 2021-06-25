package application.requestahandlers.posts;

import api.ApiHandler;
import api.Response;
import application.datacomponents.like.Like;
import application.datamanagement.database.DatabaseManager;

import java.io.IOException;
import java.sql.SQLException;

public class LikeHandler
{
    private static ApiHandler apiHandler;

    public static void build(ApiHandler newApiHandler)
    {
        apiHandler = newApiHandler;
    }

    public static void addLike(Like like) throws SQLException
    {
        DatabaseManager.addLike(like);
    }

    public static void deliverLikesQuantity(String post) throws IOException, SQLException
    {
        Response response = new Response(DatabaseManager.getLikesQuantity(post).toString());
        apiHandler.answerToClient(response);
    }

    public static void deliverLikes(String post) throws IOException, SQLException
    {
        Response response = new Response(DatabaseManager.getLikes(post));
        apiHandler.answerToClient(response);
    }

    public static void deliverIsNew(Like like) throws IOException, SQLException
    {
        if (DatabaseManager.isLikeNew(like))
        {
            Response isNew = new Response("true");
            apiHandler.answerToClient(isNew);
        }
        else
        {
            Response isNew = new Response("false");
            apiHandler.answerToClient(isNew);
        }
    }
}
