package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView logoImageView;
    @FXML
    private ImageView titleImageView;
    @FXML
    private ImageView instagramImageView;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        File logofile = new File("photo/logo.jpg");
        Image logoImage = new Image(logofile.toURI().toString());
        logoImageView.setImage(logoImage);

        File titleFile = new File("photo/title.jpg");
        Image titleImage = new Image(titleFile.toURI().toString());
        titleImageView.setImage(titleImage);

        File instagramFile = new File("photo/instagram.png");
        Image instagramImage = new Image(instagramFile.toURI().toString());
        instagramImageView.setImage(instagramImage);
    }

    public void cancelButtonOnAction(ActionEvent event){

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void loginButtonOnAction(ActionEvent event){

        //todo
        if (username.getText().isEmpty() == false && password.getText().isEmpty() == false){
            validateLogin();
        } else {
            loginMessageLabel.setText("Please enter username and password.");
        }
    }

    public void validateLogin(){

    }
}
