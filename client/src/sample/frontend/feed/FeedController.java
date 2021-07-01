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
import sample.frontend.ClientRunner;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author Hasan Roknabady
 * contains methods to control events and action within the Feed stage
 */
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
        username.setText(ClientRunner.getLoggedInUsername());
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

    /**
     * gets the profile pic of the logged in user and shows it in the provided imageView
     * @author Muahmmad Karbalaee Shabani
     * @throws IOException
     */
    private void viewProfilePic() throws IOException
    {
        Request request = new Request("IS_PROFILE_PIC_SET", ClientRunner.getLoggedInUsername());
        apiHandler.setRequest(request);
        apiHandler.sendRequest();

        if (apiHandler.receiveTrueFalse())
        {
            request = new Request("GET_PROFILE_PIC", ClientRunner.getLoggedInUsername());
            apiHandler.setRequest(request);
            apiHandler.sendRequest();
            profileView.setImage(SwingFXUtils.toFXImage(apiHandler.receivePhoto(),null));
        }
        else
        {
            setpicButton.setVisible(true);
        }
    }

    /**
     * @author Hasan Roknabady
     * views the posts of the people who the logged in user follows
     */
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

    /**
     * gets datas of every post one by one from server and makes new instances of this class Post
     * and adds them to the arraylist field in this class
     * @author Muhammad Karbalaee Shabani
     * @throws IOException
     */
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

                posts.add(new Post(postID,mainPostContext.getCaption(),mainPostContext.getOwner(),postImage,likesQuantity,commentsQuantity,mainPostContext.getDateTime()));
            }
        }
    }

    /**
     * gets all the followings of the logged in user
     * @author Muhammad Karbalaee Shabani
     * @throws IOException
     */
    private void mineFollowings() throws IOException
    {
        Request getFollowingsRequest = new Request("GET_FOLLOWINGS", ClientRunner.getLoggedInUsername());
        apiHandler.setRequest(getFollowingsRequest);
        apiHandler.sendRequest();
        String[] followingsArray = apiHandler.receiveFollowersFollowings();
        if (followingsArray == null)
        {
            return;
        }
        Collections.addAll(followings,followingsArray);
    }

    /**
     * gets the caption and username of a post wrapped in an incomplete instance of Post
     * @author Muhammad Karbalaee Shabani
     * @param postID
     * @return a Post
     * @throws IOException
     */
    private Post minePostBody(String postID) throws IOException
    {
        Request getPostBodyRequest = new Request("GET_POST",postID);
        apiHandler.setRequest(getPostBodyRequest);
        apiHandler.sendRequest();
        return apiHandler.receiveWantedPost();
    }

    /**
     *
     * @param postID
     * @return how many likes a post has
     * @throws IOException
     */
    private Integer mineLikesQuantity(String postID) throws IOException
    {
        Request getLikesQuantityRequest = new Request("GET_LIKES_QUANTITY",postID);
        apiHandler.setRequest(getLikesQuantityRequest);
        apiHandler.sendRequest();
        return apiHandler.receiveQuantity();
    }

    /**
     *
     * @param postID
     * @return how many comments a post has
     * @throws IOException
     */
    private Integer mineCommentsQuantity(String postID) throws IOException
    {
        Request getCommentsQuantityRequest = new Request("GET_COMMENTS_QUANTITY",postID);
        apiHandler.setRequest(getCommentsQuantityRequest);
        apiHandler.sendRequest();
        return apiHandler.receiveQuantity();
    }

    /**
     *
     * @param postID
     * @return what the image of this post is
     * @throws IOException
     */
    private Image minePostImage(String postID) throws IOException
    {
        Request getPostImageRequest = new Request("GET_POST_IMAGE",postID);
        apiHandler.setRequest(getPostImageRequest);
        apiHandler.sendRequest();
        return SwingFXUtils.toFXImage(apiHandler.receivePhoto(),null);
    }

    /**
     *
     * @param username
     * @return how many posts a user has. here all users are in the followings list of the logged in user
     * @throws IOException
     */
    private Integer minePostsQuantity(String username) throws IOException
    {
        Request getPostsQuantityRequest = new Request("GET_POSTS_QUANTITY", username);
        apiHandler.setRequest(getPostsQuantityRequest);
        apiHandler.sendRequest();
        return apiHandler.receiveQuantity();
    }

    /**
     * opens up the add post stage
     * @throws IOException
     */
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

    /**
     * opens up the setPic stage
     * @throws IOException
     */
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

    /**
     * opens up the profile stage
     * @throws IOException
     */
    @FXML
    public void onProfileClick() throws IOException
    {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../profile/profile.fxml")));
        Scene scene = new Scene(root,1290,900);
        Stage profileStage = new Stage();
        profileStage.initStyle(StageStyle.DECORATED);
        profileStage.setScene(scene);
        profileStage.setTitle("profile");
        profileStage.show();
    }

    /**
     * opens ups the alert stage
     * @throws IOException
     */
    public void onSearchClick() throws IOException
    {
        ClientRunner.setSearchedUsername(searchField.getText());
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

    /**
     * opens up the bio setting stage
     * @throws IOException
     */
    public void onBioClick() throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("../register/setbio.fxml"));
        Stage bioStage = new Stage();
        bioStage.initStyle(StageStyle.DECORATED);
        bioStage.setScene(new Scene(root));
        bioStage.show();
    }
}