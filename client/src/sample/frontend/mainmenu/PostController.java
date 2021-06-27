package sample.frontend.mainmenu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import sample.backend.application.post.Post;

import java.io.IOException;


public class PostController
{
    @FXML
    public Label date;
    @FXML
    public ImageView profileImage;
    @FXML
    private ImageView postImage;

    @FXML
    private Label owner;

    @FXML
    private Label likes;

    @FXML
    private Label comments;


    public void setData(Post post) throws IOException
    {
        postImage.setImage(post.getImage());
        owner.setText(post.getOwner());
        likes.setText(post.getLikesQuantity().toString());
        comments.setText(post.getCommentsQuantity().toString());
        String dateTimeToShow = String.valueOf
                (
                        post.getDateTime().getYear() + " " +
                        post.getDateTime().getMonth() + " " +
                        post.getDateTime().getDayOfMonth() + " " +
                        post.getDateTime().getHour() + ":" +
                        post.getDateTime().getMinute() + "min"
                );
        date.setText(dateTimeToShow);
    }
}
