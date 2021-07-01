package sample.frontend.search.userProfile.follow.followersStage;

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
import sample.backend.application.followerfollowing.Follower;
import sample.frontend.ClientRunner;
import sample.frontend.search.userProfile.follow.components.follower.FollowerController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class FollowersControllers implements Initializable
{
    @FXML
    private GridPane gridPaneFollow;

    private ArrayList<String> followersUsernames = new ArrayList<>();
    private ArrayList<Follower> followers = new ArrayList<>();
    private ApiHandler apiHandler = new ApiHandler();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            viewFollowers();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void viewFollowers() throws IOException
    {
        mineData();

        int columns = 0, rows = 1;

        try
        {
            for (Follower follower : followers)
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../components/follower/follower.fxml"));

                VBox followBox = fxmlLoader.load();

                FollowerController followersController = fxmlLoader.getController();
                followersController.setData(follower);

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
        mineMainPack(ClientRunner.getSearchedUsername());
        System.out.println(followersUsernames.size());
        Image profilePic;
        Request isSetRequest;

        for (int i = 0; i < followersUsernames.size(); i++)
        {
            isSetRequest = new Request("IS_PROFILE_PIC_SET",followersUsernames.get(i));
            apiHandler.setRequest(isSetRequest);
            apiHandler.sendRequest();

            if (apiHandler.receiveTrueFalse())
            {
                profilePic = mineProfileImage(followersUsernames.get(i));
            }
            else
            {
                profilePic = new Image(getClass().getResourceAsStream("../../../feed/photos/userProf1.png"));
            }

            followers.add(new Follower(followersUsernames.get(i),profilePic));
        }
    }

    private void mineMainPack(String username) throws IOException
    {
        Request getPostBodyRequest = new Request("GET_FOLLOWERS",username);
        apiHandler.setRequest(getPostBodyRequest);
        apiHandler.sendRequest();
        String[] usernames = apiHandler.receiveFollowersFollowings();
        if (usernames == null)
        {
            return;
        }
        Collections.addAll(followersUsernames,usernames);
    }

    private Image mineProfileImage(String username) throws IOException
    {
        Request getProfilePicRequest = new Request("GET_PROFILE_PIC",username);
        apiHandler.setRequest(getProfilePicRequest);
        apiHandler.sendRequest();
        return SwingFXUtils.toFXImage(apiHandler.receivePhoto(),null);
    }

}