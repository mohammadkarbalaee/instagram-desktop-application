package sample.frontend.post.comment;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class SendCommentController {

    @FXML
    private TextArea leaveComment;

    @FXML
    private Button secondCancelButton;

    public void sendButtonOnAction(){
        //todo...getting user comment and sent to database also show in comment label
        leaveComment.getText();
    }

    public void secondCancelButtonOnAction(){
        Stage secondStage = (Stage) secondCancelButton.getScene().getWindow();
        secondStage.close();
    }

}
