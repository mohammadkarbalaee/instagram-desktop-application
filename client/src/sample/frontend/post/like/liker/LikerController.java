package sample.frontend.post.like.liker;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import sample.backend.application.like.Liker;


public class LikerController
{
    @FXML
    private ImageView profileID;

    @FXML
    private Label userID;


    public void setData(Liker liker)
    {
        profileID.setImage(liker.getProfilePic());
        userID.setText(liker.getUsername());
    }
}
