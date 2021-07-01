package sample.frontend.register;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.backend.api.ApiHandler;
import sample.backend.api.Request;
import sample.frontend.ClientRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ProfilePicController
{
    @FXML
    public ImageView imageView;
    @FXML
    public Button openButton;
    @FXML
    public Button cancelButton;
    @FXML
    public Button saveButton;
    @FXML
    public Label alert;
    private File file = null;

    public void onFileChooserClick() throws IOException
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG photo","*.png"));
        file = fileChooser.showOpenDialog(null);
        updateImageView(file);
    }

    public void onCancelClick()
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void onSaveClick() throws IOException
    {
        if (file == null)
        {
            alert.setText("Choose a photo before saving");
        }
        else
        {
            Request setPicRequest = new Request("SET_PROFILE_PIC", ClientRunner.getLoggedInUsername());
            ApiHandler apiHandler = new ApiHandler(setPicRequest);
            apiHandler.sendRequest();
            apiHandler.sendPhoto(file);
        }

    }

    private void updateImageView(File file) throws IOException
    {
        BufferedImage bufferedImage = ImageIO.read(file);
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        imageView.setImage(image);
    }
}