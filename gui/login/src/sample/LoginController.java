package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

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

    public void cancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void okButtonOnAction(){
        //todo
        //main
    }
}
