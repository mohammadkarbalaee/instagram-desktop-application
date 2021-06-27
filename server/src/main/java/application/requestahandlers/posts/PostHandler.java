package application.requestahandlers.posts;

import api.ApiHandler;
import api.Response;
import application.datacomponents.post.Post;
import application.datamanagement.database.DatabaseManager;
import application.datamanagement.file.PhotoManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

public class PostHandler
{
    private ApiHandler apiHandler;
    private static Gson gson = new Gson();

    public PostHandler(ApiHandler apiHandler)
    {
        this.apiHandler = apiHandler;
    }

    public void addPost(Post post) throws IOException, SQLException
    {
        DatabaseManager.savePost(post, PhotoManager.savePostPhoto(post.getOwner(),apiHandler.getImage()));
    }

    public void deliverPost(String postID) throws SQLException, IOException
    {
        String[] pair = postID.split("/");
        System.out.println(Arrays.toString(pair));
        Post wantedPost = DatabaseManager.getPost(pair[0],pair[1]);
        System.out.println(wantedPost.getCaption());
        Response response = new Response(gson.toJson(wantedPost));
        System.out.println(response.getBody());
        apiHandler.answerToClient(response);
    }

    public void deliverPostsQuantity(String username) throws IOException, SQLException
    {
        Response response = new Response(DatabaseManager.getPostQuantity(username).toString());
        apiHandler.answerToClient(response);
    }

    public void deliverPostImage(String postID) throws SQLException, IOException
    {
        String[] pair = postID.split("/");
        apiHandler.sendPhoto(PhotoManager.getPhoto(DatabaseManager.getPostSavedAddress(pair[0],pair[1])));
    }
}
