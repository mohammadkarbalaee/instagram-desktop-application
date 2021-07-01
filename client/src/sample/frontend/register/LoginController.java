package sample.frontend.register;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.backend.application.signuplogin.SignUpperLogInner;
import sample.backend.application.signuplogin.User;
import sample.frontend.ClientRunner;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public Label usernameAsterisk;
    @FXML
    public Label passwordAsterisk;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label doesNotExist;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void cancelButtonOnAction()
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void okButtonOnAction() throws IOException
    {
        User oldUser = new User(usernameField.getText(),"",passwordField.getText());
        SignUpperLogInner loginner = new SignUpperLogInner(oldUser);
        if (loginner.checkUserUniqueness())
        {
            if (loginner.isPasswordMatch())
            {
                ClientRunner.setLoggedInUsername(usernameField.getText());
                gotoMain();
            }
            else
            {
                passwordAsterisk.setText("*");
                passwordField.setPromptText("wrong password");
            }
        }
        else
        {
            usernameAsterisk.setText("*");
            usernameField.setPromptText("user not found");
            doesNotExist.setText("This account does not exist,you have to sign up first.");
        }
    }

    public void gotoMain() throws IOException
    {
        ClientRunner.setLoggedInUsername(usernameField.getText());
        Parent root = FXMLLoader.load(getClass().getResource("../feed/feed.fxml"));
        Stage mainStage = new Stage();
        mainStage.initStyle(StageStyle.DECORATED);
        mainStage.setScene(new Scene(root,1600,900));
        mainStage.show();
    }
}