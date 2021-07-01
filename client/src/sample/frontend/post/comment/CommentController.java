package sample.frontend.post.comment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.backend.api.ApiHandler;
import sample.backend.api.Request;
import sample.backend.application.comment.Comment;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

/**
 * @author Reyhaneh Saffar
 * controls events and actions in comment stage
 */
public class CommentController implements Initializable
{
    private static String postID;
    private static String captionText;
    private static String owner;

    private ArrayList<Comment> comments = new ArrayList<>();
    private ApiHandler apiHandler = new ApiHandler();

    private double x = 0;
    private double y = 0;

    @FXML
    public Label caption;

    @FXML
    private Button firstCancelButton;

    @FXML
    private VBox commentVBox;

    public static void setPostID(String postID)
    {
        CommentController.postID = postID;
    }

    public static void setOwner(String owner)
    {
        CommentController.owner = owner;
    }

    public static String getOwner()
    {
        return owner;
    }

    public static String getPostID()
    {
        return postID;
    }

    public static void setCaption(String caption)
    {
        CommentController.captionText = caption;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            mineComments();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            for (int i = 0; i < comments.size(); i++)
            {
                if (comments.get(0) == null)
                {
                    break;
                }
                FXMLLoader fxmlLoader = new FXMLLoader();

                fxmlLoader.setLocation(getClass().getResource("setComment.fxml"));

                HBox hBox = fxmlLoader.load();

                SetComment itemController = fxmlLoader.getController();

                itemController.setCommentLabel(comments.get(i));

                commentVBox.getChildren().add(hBox);
            }
        }
        catch (IOException e)
        {

        }
    }

    /**
     * gets all comments of a post from server
     * @throws IOException
     */
    private void mineComments() throws IOException
    {
        caption.setText(captionText);
        Request request = new Request("GET_COMMENTS",postID);
        apiHandler.setRequest(request);
        apiHandler.sendRequest();
        Comment[] commentsArray = apiHandler.receiveComments();
        Collections.addAll(comments,commentsArray);
    }

    /**
     * shows the add new comment stage
     * @throws IOException
     */
    public void openTextFieldOnAction() throws IOException
    {
        CommentMain main = new CommentMain();
        main.setUp("sendComment.fxml");
    }

    /**
     * closes the entire comment stage
     */
    public void firstCancelButtonOnAction()
    {
        Stage stage = (Stage) firstCancelButton.getScene().getWindow();
        stage.close();
    }
}