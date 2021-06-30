package sample.frontend.directmessage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.backend.api.ApiHandler;
import sample.backend.api.Request;
import sample.backend.application.directmessage.ChatRoom;
import sample.backend.application.directmessage.Message;
import sample.frontend.ApplicationRunner;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class ChatroomController implements Initializable
{
    private ArrayList<Message> messages = new ArrayList<>();
    private ApiHandler apiHandler = new ApiHandler();

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField messageTextField;

    @FXML
    private Button closeButton;

    @FXML
    private VBox chatRoomVBox;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            mineChats();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        showChat();
    }

    private void mineChats() throws IOException
    {
        usernameLabel.setText(ApplicationRunner.getInChatUsername());
        ChatRoom chatRoom = new ChatRoom(ApplicationRunner.getLoggedInUsername(),ApplicationRunner.getInChatUsername());
        Request request = new Request("GET_MESSAGES",ApplicationRunner.getGson().toJson(chatRoom));
        apiHandler.setRequest(request);
        apiHandler.sendRequest();
        Message[] messagesArray = apiHandler.receiveChatroomMessages();
        if (messagesArray == null)
        {
            return;
        }
        Collections.addAll(messages,messagesArray);
    }

    @FXML
    public void sendButtonOnAction() throws IOException
    {
        Message newMessage = new Message(ApplicationRunner.getLoggedInUsername(),ApplicationRunner.getInChatUsername(),messageTextField.getText());
        messages.add(newMessage);

        Request request = new Request("SAVE_MESSAGE",ApplicationRunner.getGson().toJson(newMessage));
        apiHandler.setRequest(request);
        apiHandler.sendRequest();
        continueChat();
        messageTextField.clear();
    }

    public void showChat()
    {
        if (messages.size() != 0)
        {
            try
            {
                for (int i = 0; i < messages.size(); i++)
                {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("Message.fxml"));

                    HBox hBox = fxmlLoader.load();

                    MessageController itemController = fxmlLoader.getController();

                    itemController.setMessageLabel(messages.get(i));

                    chatRoomVBox.getChildren().add(hBox);
                }
            }
            catch (IOException e)
            {

            }
        }
    }

    public void continueChat()
    {
        int index = 0;

        if (messages.size() != 0)
        {
            index = messages.size() - 1;
        }

        if (!messageTextField.getText().isEmpty())
        {
            try
            {
                for (int i = index; i < messages.size(); i++)
                {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("Message.fxml"));

                    HBox hBox = fxmlLoader.load();

                    MessageController itemController = fxmlLoader.getController();

                    itemController.setMessageLabel(messages.get(i));

                    chatRoomVBox.getChildren().add(hBox);
                }

            }
            catch (IOException e)
            {

            }
        }
    }

    @FXML
    public void closeButtonOnAction()
    {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
