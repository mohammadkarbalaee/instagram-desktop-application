package sample.frontend.mainmenu;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import sample.backend.api.ApiHandler;
import sample.backend.api.Request;
import sample.backend.application.post.Post;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FeedController implements Initializable
{
    @FXML
    private GridPane postGrid;

    private final ApiHandler apiHandler = new ApiHandler();
    private final ArrayList<Post> posts = new ArrayList<>();



    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        int columns = 0, rows = 1;

        try
        {
            for (int i = 0; i < posts.size(); i++)
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("post.fxml"));

                VBox postBox = fxmlLoader.load();
                PostController postController = fxmlLoader.getController();
                postController.setData(posts.get(i));

                if (columns == 3)
                {
                    columns = 0;
                    ++rows;
                }

                postGrid.add(postBox, columns++, rows);
                GridPane.setMargin(postBox, new Insets(10));
            }
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    private void mineData() throws IOException
    {
        Image postImage;
        Integer commentsQuantity;
        Integer likesQuantity;
        Post mainPostContext;
        String postID;
        System.out.println(minePostsQuantity());
        for (int i = 1; i <= minePostsQuantity(); i++)
        {
            //postID = ApplicationRunner.getLoggedInUsername() + "/" + i;
            /*
            temporary value just for test
            */
            postID = "hasan" + "/" + i;
            postImage = minePostImage(postID);
            commentsQuantity = mineCommentsQuantity(postID);
            likesQuantity = mineLikesQuantity(postID);
            mainPostContext = minePostBody(postID);

            posts.add(new Post(mainPostContext.getCaption(),mainPostContext.getOwner(),postImage,likesQuantity,commentsQuantity));
        }

    }

    private Post minePostBody(String postID) throws IOException
    {
        System.out.println("i am in post quant");
        Request getPostBodyRequest = new Request("GET_POST",postID);
        apiHandler.setRequest(getPostBodyRequest);
        apiHandler.sendRequest();
        return apiHandler.receiveWantedPost();
    }

    private Integer mineLikesQuantity(String postID) throws IOException
    {
        Request getLikesQuantityRequest = new Request("GET_LIKES_QUANTITY",postID);
        apiHandler.setRequest(getLikesQuantityRequest);
        apiHandler.sendRequest();
        return apiHandler.receiveQuantity();
    }

    private Integer mineCommentsQuantity(String postID) throws IOException
    {
        Request getCommentsQuantityRequest = new Request("GET_COMMENTS_QUANTITY",postID);
        apiHandler.setRequest(getCommentsQuantityRequest);
        apiHandler.sendRequest();
        return apiHandler.receiveQuantity();
    }

    private Image minePostImage(String postID) throws IOException
    {
        Request getPostImageRequest = new Request("GET_POST_IMAGE",postID);
        apiHandler.setRequest(getPostImageRequest);
        apiHandler.sendRequest();
        return SwingFXUtils.toFXImage(apiHandler.receivePhoto(),null);
    }

    private Integer minePostsQuantity() throws IOException
    {
        Request getPostsQuantityRequest = new Request("GET_POSTS_QUANTITY", "hasan");
        apiHandler.setRequest(getPostsQuantityRequest);
        apiHandler.sendRequest();
        return apiHandler.receiveQuantity();
    }
}
