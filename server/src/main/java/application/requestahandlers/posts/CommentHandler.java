package application.requestahandlers.posts;

import api.ApiHandler;
import api.Response;
import application.datacomponents.comment.Comment;
import application.datamanagement.database.DatabaseManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.SQLException;

public class CommentHandler
{
    private ApiHandler apiHandler;
    private static Gson gson = new Gson();

    public CommentHandler(ApiHandler apiHandler)
    {
        this.apiHandler = apiHandler;
    }

    public void addComment(Comment newComment) throws SQLException
    {
        DatabaseManager.addComment(newComment);
    }

    public void deliverCommentQuantity(String post) throws SQLException, IOException
    {
        Response response = new Response(DatabaseManager.getCommentsQuanity(post).toString());
        apiHandler.answerToClient(response);
    }

    public void deliverComments(String post) throws IOException, SQLException
    {
        Response response = new Response(DatabaseManager.getComments(post));
        apiHandler.answerToClient(response);
    }
}
