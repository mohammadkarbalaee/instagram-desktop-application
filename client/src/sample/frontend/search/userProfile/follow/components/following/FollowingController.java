package sample.frontend.search.userProfile.follow.components.following;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import sample.backend.application.followerfollowing.Following;


public class FollowingController
{
    @FXML
    private ImageView profileID;

    @FXML
    private Label userID;


    public void setData(Following following)
    {
        profileID.setImage(following.getProfilePic());
        userID.setText(following.getUsername());
    }
}
