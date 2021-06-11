package sample;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import model.Post;
import javafx.scene.image.ImageView;


public class PostController
{

    @FXML
    private ImageView postImage;

    @FXML
    private ImageView profileImage;

    @FXML
    private Label username;

    @FXML
    private Label date;

    @FXML
    private Label likes;

    @FXML
    private Label comments;





    public void setData(Post post)
    {
        Image imageForPost = new Image(getClass().getResourceAsStream(post.getPostImageSrc()));
        postImage.setImage(imageForPost);

        Image imageForProfile = new Image(getClass().getResourceAsStream(post.getProfileImageSrc()));
        profileImage.setImage(imageForProfile);


        username.setText(post.getUsername());
        date.setText(post.getDate());
        likes.setText(post.getNumLikes());
        comments.setText(post.getNumComments());
    }
}
