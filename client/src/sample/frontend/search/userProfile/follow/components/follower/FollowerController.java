package sample.frontend.search.userProfile.follow.components.follower;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import sample.backend.application.followerfollowing.Follower;


public class FollowerController
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
