package sample.frontend.profile.follow.followingStage;

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
import sample.backend.application.followerfollowing.Following;
import sample.frontend.ClientRunner;
import sample.frontend.profile.follow.components.following.FollowingController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class FollowingsController implements Initializable
{
    @FXML
    private GridPane gridPaneFollow;

    private ArrayList<String> followingsUsernames = new ArrayList<>();
    private ArrayList<Following> followings = new ArrayList<>();
    private ApiHandler apiHandler = new ApiHandler();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            viewFollowings();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void viewFollowings() throws IOException
    {
        mineData();

        int columns = 0, rows = 1;

        try
        {
            for (Following following : followings)
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../components/following/following.fxml"));

                VBox followBox = fxmlLoader.load();

                FollowingController followingsController = fxmlLoader.getController();
                followingsController.setData(following);

                if (columns == 1)
                {
                    columns = 0;
                    ++rows;
                }

                gridPaneFollow.add(followBox, columns++, rows);
                GridPane.setMargin(followBox, new Insets(10));
            }
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    private void mineData() throws IOException
    {
        mineMainPack(ClientRunner.getLoggedInUsername());
        Image profilePic;
        Request isSetRequest;
        for (int i = 0; i < followingsUsernames.size(); i++)
        {
            isSetRequest = new Request("IS_PROFILE_PIC_SET",followingsUsernames.get(i));
            apiHandler.setRequest(isSetRequest);
            apiHandler.sendRequest();

            if (apiHandler.receiveTrueFalse())
            {
                profilePic = mineProfileImage(followingsUsernames.get(i));
            }
            else
            {
                profilePic = new Image(getClass().getResourceAsStream("../../../feed/photos/userProf1.png"));
            }

            followings.add(new Following(followingsUsernames.get(i),profilePic));
        }
    }

    private void mineMainPack(String username) throws IOException
    {
        Request getPostBodyRequest = new Request("GET_FOLLOWINGS",username);
        apiHandler.setRequest(getPostBodyRequest);
        apiHandler.sendRequest();
        String[] usernames = apiHandler.receiveFollowersFollowings();
        if (usernames == null)
        {
            return;
        }
        Collections.addAll(followingsUsernames,usernames);
    }

    private Image mineProfileImage(String username) throws IOException
    {
        Request getProfilePicRequest = new Request("GET_PROFILE_PIC",username);
        apiHandler.setRequest(getProfilePicRequest);
        apiHandler.sendRequest();
        return SwingFXUtils.toFXImage(apiHandler.receivePhoto(),null);
    }

}