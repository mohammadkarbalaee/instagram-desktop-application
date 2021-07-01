package sample.frontend.register;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.backend.api.ApiHandler;
import sample.backend.application.signuplogin.SignUpperLogInner;
import sample.backend.application.signuplogin.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class SigningController implements Initializable
{
    @FXML
    public Label passwordAsterisk;
    @FXML
    public Label emailAsterisk;
    @FXML
    public Label usernameAsterisk;
    @FXML
    public ProgressBar progressbar;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private TextField email;
    @FXML
    private Label isNotNew;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void okButtonSignupOnAction() throws IOException
    {
        User newUser = new User(username.getText(),email.getText(),password.getText());
        SignUpperLogInner signUpper =  new SignUpperLogInner(newUser);
        ApiHandler apiHandler = new ApiHandler();
        if (!signUpper.checkUserUniqueness())
        {
            if(signUpper.checkPasswordValidation())
            {
                if (signUpper.checkEmailValidation())
                {
                    apiHandler.setRequest(signUpper.makeRequest());
                    apiHandler.sendRequest();
                    email.setPromptText("email");
                    username.setPromptText("username");
                    password.setPromptText("password");
                    emailAsterisk.setText("");
                    username.setText("");
                    passwordAsterisk.setText("");
                    isNotNew.setText("");

                    alreadyHaveAnAccount();
                }
                else
                {
                    email.setPromptText("invalid email address");
                    emailAsterisk.setText("*");
                }
            }
            else
            {
                password.setPromptText("password is too weak");
                passwordAsterisk.setText("*");
            }
        }
        else
        {
            isNotNew.setText("An account with this info already exists\ntry another username or login instead");
            username.setPromptText("user already exists");
            usernameAsterisk.setText("*");
        }
    }

    public void alreadyHaveAnAccount() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Stage loginStage = new Stage();
            loginStage.initStyle(StageStyle.DECORATED);
            loginStage.setScene(new Scene(root));
            loginStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
