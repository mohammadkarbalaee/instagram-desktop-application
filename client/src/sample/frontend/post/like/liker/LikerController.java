package sample.frontend.post.like.liker;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import sample.backend.application.followerfollowing.Follower;


public class LikerController
{
    @FXML
    private ImageView profileID;

    @FXML
    private Label userID;


    public void setData(Follower follower)
    {
        profileID.setImage(follower.getProfilePic());
        userID.setText(follower.getUsername());
    }
}
