package sample.frontend.post;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.backend.api.ApiHandler;
import sample.backend.api.Request;
import sample.backend.application.post.Post;
import sample.frontend.ClientRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author Muhammad Karbalaee Shabani
 * controls the events and action within the savePost stage
 */
public class SavepostController
{
    @FXML
    public TextField captionField;
    private String caption = null;
    private File file = null;
    @FXML
    public Label alert;
    @FXML
    public ImageView imageView;
    @FXML
    public Button openButton;
    @FXML
    public Button saveButton;
    @FXML
    public Button cancelButton;

    /**
     * opens a filechooser in which the user can pick a photo of format png
     * after being picked, this method will set the File to the file field in this class
     * @throws IOException
     */
    public void onFileChooserClick() throws IOException
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG photo","*.png"));
        file = fileChooser.showOpenDialog(null);
        updateImageView(file);
    }

    /**
     * sets the imageview to the picture that user has chose to post
     * @param file
     * @throws IOException
     */
    private void updateImageView(File file) throws IOException
    {
        BufferedImage bufferedImage = ImageIO.read(file);
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        imageView.setImage(image);
    }

    /**
     * closes the stage
     */
    public void onCancelClick()
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * sends a new post to server
     * @throws IOException
     */
    public void onSaveClick() throws IOException
    {
        caption = captionField.getText();
        if (file == null)
        {
            alert.setText("Choose a photo before saving");
        }
        else
        {
            Post newPost = new Post(ClientRunner.getLoggedInUsername(),caption, LocalDateTime.now());
            Request savePostRequest = new Request("SAVE_POST", ClientRunner.getGson().toJson(newPost));
            ApiHandler apiHandler = new ApiHandler(savePostRequest);
            apiHandler.sendRequest();
            apiHandler.sendPhoto(file);
        }
    }
}