package application.requestahandlers.posts;

import api.ApiHandler;
import application.datacomponents.comment.Comment;
import application.datamanagement.database.DatabaseManager;
import com.google.gson.Gson;

import java.sql.SQLException;

public class CommentHandler
{
    private static ApiHandler apiHandler;
    private static Gson gson = new Gson();

    public static void build(ApiHandler newApiHandler)
    {
        apiHandler = newApiHandler;
    }

    public static void addComment(Comment newComment) throws SQLException
    {
        DatabaseManager.addComment(newComment);
    }
}
