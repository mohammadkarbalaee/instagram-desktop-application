package sample.frontend.post.comment;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import sample.backend.api.ApiHandler;
import sample.backend.api.Request;
import sample.backend.application.comment.Comment;

import java.io.IOException;

import static sample.frontend.ClientRunner.getGson;

public class SendCommentController
{
    private ApiHandler apiHandler = new ApiHandler();

    @FXML
    public Button sendButton;
    @FXML
    public TextArea leaveComment;

    @FXML
    private Button secondCancelButton;

    public void sendButtonOnAction() throws IOException
    {
        Comment newComment = new Comment(leaveComment.getText(),CommentController.getOwner(),CommentController.getPostID());
        Request request = new Request("ADD_COMMENT",getGson().toJson(newComment));
        apiHandler.setRequest(request);
        apiHandler.sendRequest();
    }

    public void secondCancelButtonOnAction()
    {
        Stage secondStage = (Stage) secondCancelButton.getScene().getWindow();
        secondStage.close();
    }
}