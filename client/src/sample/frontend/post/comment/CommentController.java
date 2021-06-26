package sample.frontend.post.comment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CommentController implements Initializable  {

    private String postedImageAddress;

    private double x = 0;
    private double y = 0;

    @FXML
    private ImageView postedImage;

    @FXML
    private Label commentsLabel;

    @FXML
    private Button firstCancelButton;

    public void setPostedImageAddress(String filePath){
        this.postedImageAddress = filePath;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //todo...connecting to database get posted Image Address
//        File post = new File(postedImageAddress);
//        Image postedImageView = new Image(post.toURI().toString());
//        postedImage.setImage(postedImageView);
//        showComments();
    }

    public void showComments(){
        //todo...connecting to database to get comments that related to posted image
       // commentsLabel.setText(commentsLabel.getText());
    }

    public void addNewComment(){
        //todo...when you send a comment we should get that and add to existing comments
    }

    public void openTextFieldOnAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Main main = new Main();
        main.setUp("sendComment.fxml",stage);
    }

    public void firstCancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) firstCancelButton.getScene().getWindow();
        stage.close();
    }
}
