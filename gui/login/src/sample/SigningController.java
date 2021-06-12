package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SigningController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private ImageView instagramImageView;
    @FXML
    private ImageView logoImageView;
    @FXML
    private ImageView titleImageView;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private TextField email;

    public void cancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File instagramFile = new File("Photo/instagram.png");
        Image instagramImage = new Image(instagramFile.toURI().toString());
        instagramImageView.setImage(instagramImage);

        File logoFile = new File("Photo/logo.jpg");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoImageView.setImage(logoImage);

        File titleFile = new File("Photo/title.jpg");
        Image titleImage = new Image(titleFile.toURI().toString());
        titleImageView.setImage(titleImage);
    }

    public void signingUp(){
        //todo
        //darabase
    }

    public void loginButtonOnAction() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Stage loginStage = new Stage();
            loginStage.initStyle(StageStyle.UNDECORATED);
            loginStage.setScene(new Scene(root, 520, 400));
            loginStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
