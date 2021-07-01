package sample.frontend.feed;

import javafx.embed.swing.SwingFXUtils;
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
import sample.frontend.ClientRunner;
import sample.frontend.post.comment.CommentController;
import sample.frontend.post.comment.CommentMain;
import sample.frontend.post.like.LikeController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static sample.frontend.ClientRunner.getGson;


public class PostController
{
    private ApiHandler apiHandler = new ApiHandler();

    @FXML
    public ImageView heart;
    @FXML
    public Label date;
    @FXML
    public ImageView profileImage;
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
    private boolean isLiked;


    public void setData(Post post) throws IOException
    {
        this.post = post;

        updateLikeStatus();

        mineProfilePic(post.getOwner());

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

    private void mineProfilePic(String postOwner) throws IOException
    {
        Request request = new Request("GET_PROFILE_PIC", postOwner);
        apiHandler.setRequest(request);
        apiHandler.sendRequest();
        profileImage.setImage(SwingFXUtils.toFXImage(apiHandler.receivePhoto(),null));
    }

    private void updateLikeStatus() throws IOException
    {
        Like like = new Like(ClientRunner.getLoggedInUsername(),post.getPostID());
        Request request = new Request("IS_LIKED",getGson().toJson(like));
        apiHandler.setRequest(request);
        apiHandler.sendRequest();
        isLiked = apiHandler.receiveTrueFalse();
        if (isLiked)
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
        CommentController.setOwner(ClientRunner.getLoggedInUsername());
        CommentMain commentRunner = new CommentMain();
        commentRunner.setUp("comment.fxml");
    }

    public void onAddLikeClick() throws IOException
    {
        Like newLike = new Like(ClientRunner.getLoggedInUsername(),post.getPostID());
        Request request;

        if (isLiked)
        {
            request = new Request("ADD_DISLIKE",getGson().toJson(newLike));
            isLiked = !isLiked;
        }
        else
        {
            request = new Request("ADD_LIKE",getGson().toJson(newLike));
            isLiked = !isLiked;
        }
        apiHandler.setRequest(request);
        apiHandler.sendRequest();
        updateLikeStatus();
    }

}