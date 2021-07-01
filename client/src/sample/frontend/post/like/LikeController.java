package sample.frontend.post.like;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import sample.backend.api.ApiHandler;
import sample.backend.api.Request;
import sample.backend.application.like.Liker;
import sample.frontend.post.like.liker.LikerController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
/**
 * @author Muhammad Karbalaee Shabani
 * controls the like stage
 */
public class LikeController implements Initializable
{
    @FXML
    private GridPane gridPaneLike;

    private ArrayList<String> likersUsernames = new ArrayList<>();
    private ArrayList<Liker> likers = new ArrayList<>();
    private ApiHandler apiHandler = new ApiHandler();
    private static String postID;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            viewLikers();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void setPostID(String postID)
    {
        LikeController.postID = postID;
    }

    private void viewLikers() throws IOException
    {
        mineData();

        int columns = 0, rows = 1;

        try
        {
            for (Liker liker : likers)
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("liker/liker.fxml"));

                VBox likeBox = fxmlLoader.load();

                LikerController likersController = fxmlLoader.getController();
                likersController.setData(liker);

                if (columns == 1)
                {
                    columns = 0;
                    ++rows;
                }

                gridPaneLike.add(likeBox, columns++, rows);
                GridPane.setMargin(likeBox, new Insets(10));
            }
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    /**
     * gets data from server and sets it
     * @throws IOException
     */
    private void mineData() throws IOException
    {
        mineMainPack(postID);
        Image profilePic;
        Request isSetRequest;

        for (int i = 0; i < likersUsernames.size(); i++)
        {
            isSetRequest = new Request("IS_PROFILE_PIC_SET", likersUsernames.get(i));
            apiHandler.setRequest(isSetRequest);
            apiHandler.sendRequest();

            if (apiHandler.receiveTrueFalse())
            {
                profilePic = mineProfileImage(likersUsernames.get(i));
            }
            else
            {
                profilePic = new Image(getClass().getResourceAsStream("../../feed/photos/userProf1.png"));
            }

            likers.add(new Liker(likersUsernames.get(i),profilePic));
        }
    }

    private void mineMainPack(String postID) throws IOException
    {
        Request getPostBodyRequest = new Request("GET_LIKERS",postID);
        apiHandler.setRequest(getPostBodyRequest);
        apiHandler.sendRequest();
        String[] likers = apiHandler.receiveLikes();
        if (likers == null)
        {
            return;
        }
        Collections.addAll(likersUsernames,likers);
    }

    /**
     * gets the photo of post likers from the server
     * @param username
     * @return
     * @throws IOException
     */
    private Image mineProfileImage(String username) throws IOException
    {
        Request getProfilePicRequest = new Request("GET_PROFILE_PIC",username);
        apiHandler.setRequest(getProfilePicRequest);
        apiHandler.sendRequest();
        return SwingFXUtils.toFXImage(apiHandler.receivePhoto(),null);
    }

}