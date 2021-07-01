package sample.frontend.register;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import sample.backend.api.ApiHandler;
import sample.backend.api.Request;
import sample.backend.application.signuplogin.User;
import sample.frontend.ClientRunner;

import java.io.IOException;

import static sample.frontend.ClientRunner.getGson;

public class setBioController
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
        User bioUser = new User();
        bioUser.setBio(leaveComment.getText());
        bioUser.setUserName(ClientRunner.getLoggedInUsername());

        Request request = new Request("ADD_BIO",getGson().toJson(bioUser));
        apiHandler.setRequest(request);
        apiHandler.sendRequest();
    }

    public void secondCancelButtonOnAction()
    {
        Stage secondStage = (Stage) secondCancelButton.getScene().getWindow();
        secondStage.close();
    }
}