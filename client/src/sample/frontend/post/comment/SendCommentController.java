package sample.frontend.post.comment;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import sample.backend.api.ApiHandler;
import sample.backend.api.Request;
import sample.backend.application.comment.Comment;

import java.io.IOException;

public class SendCommentController {

    private Gson gson = new Gson();
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
        Request request = new Request("ADD_COMMENT",gson.toJson(newComment));
        apiHandler.setRequest(request);
        apiHandler.sendRequest();
    }

    public void secondCancelButtonOnAction()
    {
        Stage secondStage = (Stage) secondCancelButton.getScene().getWindow();
        secondStage.close();
    }
}