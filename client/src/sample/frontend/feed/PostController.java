package sample.frontend.feed;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.backend.api.ApiHandler;
import sample.backend.api.Request;
import sample.backend.application.like.Like;
import sample.backend.application.post.Post;
import sample.frontend.ApplicationRunner;
import sample.frontend.post.comment.CommentController;
import sample.frontend.post.comment.CommentMain;
import sample.frontend.post.like.LikeController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class PostController
{
    private Gson gson = new Gson();
    private ApiHandler apiHandler = new ApiHandler();

    @FXML
    public ImageView heart;
    @FXML
    public Label date;
    @FXML
    public ImageView profileImage;
    @FXML
    public Button likeID;
    @FXML
    public Button addLikeButton;
    @FXML
    private ImageView postImage;

    @FXML
    private Label owner;

    @FXML
    private Label likes;

    @FXML
    private Label comments;

    private Post post;


    public void setData(Post post) throws IOException
    {
        this.post = post;

        addLikeStatus();

        postImage.setImage(post.getImage());
        owner.setText(post.getOwner());
        likes.setText(post.getLikesQuantity().toString());
        comments.setText(post.getCommentsQuantity().toString());
        String dateTimeToShow = post.getDateTime().getYear() + " " +
                post.getDateTime().getMonth() + " " +
                post.getDateTime().getDayOfMonth() + " " +
                post.getDateTime().getHour() + ":" +
                post.getDateTime().getMinute() + "min";
        date.setText(dateTimeToShow);
    }

    private void addLikeStatus() throws IOException
    {
        Like like = new Like(ApplicationRunner.getLoggedInUsername(),post.getPostID());
        Request request = new Request("IS_LIKED",gson.toJson(like));
        apiHandler.setRequest(request);
        apiHandler.sendRequest();
        if (apiHandler.receiveTrueFalse())
        {
            InputStream stream = new FileInputStream("C:\\Users\\Muhammad\\Desktop\\instagram-app\\client\\src\\sample\\frontend\\feed\\photos\\heart_red.png");
            Image redHeart = new Image(stream);
            heart.setImage(redHeart);
        }
        else
        {
            InputStream stream = new FileInputStream("C:\\Users\\Muhammad\\Desktop\\instagram-app\\client\\src\\sample\\frontend\\feed\\photos\\heart_10MMM0px.png");
            Image blankHeart = new Image(stream);
            heart.setImage(blankHeart);
        }
    }

    public void likeBTN() throws IOException
    {
        LikeController.setPostID(post.getPostID());
        Parent root = FXMLLoader.load(getClass().getResource("../post/like/like.fxml"));
        Scene scene = new Scene(root);
        Stage likeStage = new Stage();
        likeStage.initStyle(StageStyle.DECORATED);
        likeStage.setScene(scene);
        likeStage.show();
    }

    public void commentBTN() throws Exception
    {
        CommentController.setPostID(post.getPostID());
        CommentController.setCaption(post.getCaption());
        CommentController.setOwner(post.getOwner());
        CommentMain commentRunner = new CommentMain();
        commentRunner.setUp("comment.fxml");
    }

    public void onAddLikeClick() throws IOException
    {
        Like newLike = new Like(ApplicationRunner.getLoggedInUsername(),post.getPostID());
        Request request = new Request("ADD_LIKE",gson.toJson(newLike));
        apiHandler.setRequest(request);
        apiHandler.sendRequest();
    }
}