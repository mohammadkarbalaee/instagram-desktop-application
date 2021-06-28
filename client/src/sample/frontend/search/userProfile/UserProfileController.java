package sample.frontend.search.userProfile;

import com.google.gson.Gson;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.backend.api.ApiHandler;
import sample.backend.api.Request;
import sample.backend.application.followerfollowing.FollowerFollowingPack;
import sample.backend.application.post.Post;
import sample.frontend.feed.PostController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class UserProfileController implements Initializable
{
    @FXML
    public ImageView profileIMG;
    @FXML
    public Label username;
    @FXML
    public Label numFollowersID;
    @FXML
    public Label numFollowingID;
    @FXML
    public Button followers;
    @FXML
    public Button followings;
    @FXML
    public Label bio;
    @FXML
    public Button messageButton;
    @FXML
    public Button followButton;
    @FXML
    private GridPane postGrid;

    private final ApiHandler apiHandler = new ApiHandler();
    private final ArrayList<Post> posts = new ArrayList<>();
    private final Gson gson = new Gson();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            viewAppearances();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        viewPosts();
    }

    private void viewAppearances() throws IOException
    {
        bio.setText(mineBio("reyhan"));
        username.setText("reyhan");
        Request request;
        request = new Request("IS_PROFILE_PIC_SET","reyhan");
        apiHandler.setRequest(request);
        apiHandler.sendRequest();
        if (apiHandler.receiveTrueFalse())
        {
            profileIMG.setImage(mineProfileImage("reyhan"));
        }
        else
        {
            profileIMG.setImage(new Image(getClass().getResourceAsStream("../../feed/photos/userProf1.png")));
        }
        FollowerFollowingPack pack = new FollowerFollowingPack("hasan","reyhan",false);
        request = new Request("IS_FOLLOWED",gson.toJson(pack));
        apiHandler.setRequest(request);
        apiHandler.sendRequest();
        if (apiHandler.receiveTrueFalse())
        {
            followButton.setText("unfollow");
        }
        else
        {
            followButton.setText("follow");
        }
        numFollowersID.setText(String.valueOf(mineFollowersQuantity("reyhan")));
        numFollowingID.setText(String.valueOf(mineFollowingsQuantity("reyhan")));
    }

    private String mineBio(String username) throws IOException
    {
        Request request = new Request("GET_BIO",username);
        apiHandler.setRequest(request);
        apiHandler.sendRequest();
        return apiHandler.receivePlainString();
    }

    private Integer mineFollowersQuantity(String username) throws IOException
    {
        Request request = new Request("GET_FOLLOWERS_COUNT",username);
        apiHandler.setRequest(request);
        apiHandler.sendRequest();
        return apiHandler.receiveQuantity();
    }

    private Integer mineFollowingsQuantity(String username) throws IOException
    {
        Request request = new Request("GET_FOLLOWINGS_COUNT",username);
        apiHandler.setRequest(request);
        apiHandler.sendRequest();
        return apiHandler.receiveQuantity();
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
                fxmlLoader.setLocation(getClass().getResource("../../post/post.fxml"));

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
        int postsQuantity = minePostsQuantity("reyhan");
        for (int i = 1; i <= postsQuantity; i++)
        {
            postID = "reyhan" + "/" + i;
            mainPostContext = minePostBody(postID);
            likesQuantity = mineLikesQuantity(postID);
            commentsQuantity = mineCommentsQuantity(postID);
            postImage = minePostImage(postID);

            posts.add(new Post(mainPostContext.getCaption(),mainPostContext.getOwner(),postImage,likesQuantity,commentsQuantity,mainPostContext.getDateTime()));
        }
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

    public void onFollowerClick() throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../../profile/follow/followersStage/followersStage.fxml"));
        Scene scene = new Scene(root);
        Stage followersStage = new Stage();
        followersStage.initStyle(StageStyle.DECORATED);
        followersStage.setScene(scene);
        followersStage.show();
    }

    public void onFollowingClick() throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../../profile/follow/followingStage/followingsStage.fxml"));
        Scene scene = new Scene(root);
        Stage profileStage = new Stage();
        profileStage.initStyle(StageStyle.DECORATED);
        profileStage.setScene(scene);
        profileStage.show();
    }

    private Image mineProfileImage(String username) throws IOException
    {
        Request getProfilePicRequest = new Request("GET_PROFILE_PIC",username);
        apiHandler.setRequest(getProfilePicRequest);
        apiHandler.sendRequest();
        return SwingFXUtils.toFXImage(apiHandler.receivePhoto(),null);
    }

    public void onMessageClick()
    {

    }

    public void onFollowClick() throws IOException
    {
        if (followButton.getText().equals("unfollow"))
        {
            FollowerFollowingPack pack = new FollowerFollowingPack("hasan","reyhan",true);
            apiHandler.setRequest(new Request("SEND_FOLLOWER",gson.toJson(pack)));
            apiHandler.sendRequest();
        }
        else
        {
            FollowerFollowingPack pack = new FollowerFollowingPack("hasan","reyhan",false);
            apiHandler.setRequest(new Request("SEND_FOLLOWER",gson.toJson(pack)));
            apiHandler.sendRequest();
        }
    }

}