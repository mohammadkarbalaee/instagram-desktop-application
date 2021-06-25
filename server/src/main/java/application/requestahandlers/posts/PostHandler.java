package application.requestahandlers.posts;

import api.ApiHandler;
import api.Response;
import application.datacomponents.post.Post;
import application.datamanagement.database.DatabaseManager;
import application.datamanagement.file.PhotoManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.SQLException;

public class PostHandler
{
    private static ApiHandler apiHandler;
    private static Gson gson = new Gson();

    public static void build(ApiHandler newApiHandler)
    {
        apiHandler = newApiHandler;
    }

    public static void addPost(Post post) throws IOException, SQLException
    {
        DatabaseManager.savePost(post, PhotoManager.savePostPhoto(post.getOwner(),apiHandler.getPostImage()));
    }

    public static void deliverPost(String requestBody) throws SQLException, IOException
    {
        String[] pair = requestBody.split("/");
        Post wantedPost = DatabaseManager.getPost(pair[0],pair[1]);
        Response response = new Response(gson.toJson(wantedPost));
        apiHandler.answerToClient(response);
        apiHandler.sendPhoto(PhotoManager.getPostPhoto(DatabaseManager.getPostSavedAddress(pair[0],pair[1])));
    }

    public static void deliverPostsQuantity(String username) throws IOException, SQLException
    {
        Response response = new Response(DatabaseManager.getPostQuantity(username).toString());
        apiHandler.answerToClient(response);
    }
}
