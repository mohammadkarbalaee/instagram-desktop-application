package sample.frontend.feed;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.backend.api.ApiHandler;
import sample.backend.api.Request;
import sample.backend.application.post.Post;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.ResourceBundle;

public class FeedController implements Initializable
{
    @FXML
    public Button addpostButton;
    @FXML
    public ImageView profileView;
    @FXML
    public Button setpicButton;
    @FXML
    public Label username;
    @FXML
    public Button profileButton;
    @FXML
    public Button searchButton;
    @FXML
    public TextField searchField;
    @FXML
    private GridPane postGrid;

    private final ApiHandler apiHandler = new ApiHandler();
    private final ArrayList<Post> posts = new ArrayList<>();
    private final ArrayList<String> followings = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        username.setText("hasan");
        try
        {
            viewProfilePic();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        viewPosts();
    }

    private void viewProfilePic() throws IOException
    {
        Request request = new Request("IS_PROFILE_PIC_SET","hasan");
        apiHandler.setRequest(request);
        apiHandler.sendRequest();

        if (apiHandler.receiveTrueFalse())
        {
            request = new Request("GET_PROFILE_PIC","hasan");
            apiHandler.setRequest(request);
            apiHandler.sendRequest();
            profileView.setImage(SwingFXUtils.toFXImage(apiHandler.receivePhoto(),null));
        }
        else
        {
            setpicButton.setVisible(true);
        }
    }

    private void viewPosts()
    {
        int columns = 0, rows = 1;

        try
        {
            mineData();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Collections.sort(posts);

        try
        {
            for (int i = 0; i < posts.size(); i++)
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../post/post.fxml"));

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
        mineFollowings();
        Image postImage;
        Integer commentsQuantity;
        Integer likesQuantity;
        Post mainPostContext;
        String postID;
        for (int i = 0; i < followings.size(); i++)
        {
            int postsQuantity = minePostsQuantity(followings.get(i));
            for (int j = 0; j < postsQuantity; j++)
            {
                postID = followings.get(i) + "/" + (j + 1);
                mainPostContext = minePostBody(postID);
                likesQuantity = mineLikesQuantity(postID);
                commentsQuantity = mineCommentsQuantity(postID);
                postImage = minePostImage(postID);

                posts.add(new Post(mainPostContext.getCaption(),mainPostContext.getOwner(),postImage,likesQuantity,commentsQuantity,mainPostContext.getDateTime()));
            }
        }
    }

    private void mineFollowings() throws IOException
    {
        Request getFollowingsRequest = new Request("GET_FOLLOWINGS","muhammad.ksht"/*ApplicationRunner.loggedinuser*/);
        apiHandler.setRequest(getFollowingsRequest);
        apiHandler.sendRequest();
        Collections.addAll(followings,apiHandler.receiveFollowersFollowings());
    }

    private Post minePostBody(String postID) throws IOException
    {
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

    private Integer minePostsQuantity(String username) throws IOException
    {
        Request getPostsQuantityRequest = new Request("GET_POSTS_QUANTITY", username);
        apiHandler.setRequest(getPostsQuantityRequest);
        apiHandler.sendRequest();
        return apiHandler.receiveQuantity();
    }

    @FXML
    public void onAddPostClick() throws IOException
    {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../post/savepost.fxml")));
        Scene scene = new Scene(root);
        Stage savePostStage = new Stage();
        savePostStage.initStyle(StageStyle.DECORATED);
        savePostStage.setScene(scene);
        savePostStage.show();
    }

    @FXML
    public void onSetPicClick() throws IOException
    {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../register/setProfilePic.fxml")));
        Scene scene = new Scene(root);
        Stage savePostStage = new Stage();
        savePostStage.initStyle(StageStyle.DECORATED);
        savePostStage.setScene(scene);
        savePostStage.show();
    }

    @FXML
    public void onProfileClick() throws IOException
    {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../profile/profile.fxml")));
        Scene scene = new Scene(root);
        Stage profileStage = new Stage();
        profileStage.initStyle(StageStyle.DECORATED);
        profileStage.setScene(scene);
        profileStage.setTitle("profile");
        profileStage.show();
    }

    public void onSearchClick() throws IOException
    {
        Request searchRequest = new Request("GET_SEARCH_RESULT",searchField.getText());
        apiHandler.setRequest(searchRequest);
        apiHandler.sendRequest();
        String result = apiHandler.receiveSearchResult();

        if (result == null)
        {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../search/notFound.fxml")));
            Scene scene = new Scene(root);
            Stage notFoundStage = new Stage();
            notFoundStage.initStyle(StageStyle.DECORATED);
            notFoundStage.setScene(scene);
            notFoundStage.setTitle("alert");
            notFoundStage.show();
        }
        else
        {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../search/found.fxml")));
            Scene scene = new Scene(root);
            Stage notFoundStage = new Stage();
            notFoundStage.initStyle(StageStyle.DECORATED);
            notFoundStage.setScene(scene);
            notFoundStage.setTitle("alert");
            notFoundStage.show();
        }
    }
}