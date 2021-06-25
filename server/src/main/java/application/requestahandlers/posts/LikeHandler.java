package application.requestahandlers.posts;

import api.ApiHandler;
import application.datacomponents.like.Like;
import application.datamanagement.database.DatabaseManager;

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
}
