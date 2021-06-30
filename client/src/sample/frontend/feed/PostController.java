package sample.frontend.feed;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.backend.application.post.Post;
import sample.frontend.post.comment.CommentController;
import sample.frontend.post.comment.CommentMain;
import sample.frontend.post.like.LikeController;

import java.io.IOException;


public class PostController
{
    @FXML
    public Label date;
    @FXML
    public ImageView profileImage;
    @FXML
    public Button likeID;
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
}
