package sample.frontend.register;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.backend.application.signuplogin.SignUpperLogInner;
import sample.backend.application.signuplogin.User;
import sample.frontend.ApplicationRunner;

import java.io.File;
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
    private ImageView logoLogin;
    @FXML
    private ImageView instagramImageViewer;
    @FXML
    private ImageView titleImageViewer;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button okButton;
    @FXML
    private Label doesNotExist;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File instagram = new File("Photo/instagramLoginImage.png");
        Image instagramImageView = new Image(instagram.toURI().toString());
        instagramImageViewer.setImage(instagramImageView);

        File title = new File("Photo/titleLoginImage.jpg");
        Image titleImageView = new Image(title.toURI().toString());
        titleImageViewer.setImage(titleImageView);

        File logo = new File("Photo/logoLogin.jpg");
        Image logoImageView = new Image(logo.toURI().toString());
        logoLogin.setImage(logoImageView);
    }

    public void cancelButtonOnAction(ActionEvent event)
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void okButtonOnAction() throws IOException
    {
        gotoMain();
        User oldUser = new User(usernameField.getText(),"",passwordField.getText());
        SignUpperLogInner loginner = new SignUpperLogInner(oldUser);
        if (!loginner.checkUserUniqueness())
        {
            if (loginner.isPasswordMatch())
            {
                ApplicationRunner.setLoggedInUsername(usernameField.getText());
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
        Parent root = FXMLLoader.load(getClass().getResource("../feed/feed.fxml"));
        Stage mainStage = new Stage();
        mainStage.initStyle(StageStyle.UNDECORATED);
        mainStage.setScene(new Scene(root, 520, 400));
        mainStage.show();
    }
}