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
import sample.frontend.ClientRunner;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

/**
 * @author Reyhaneh Saffar
 * this class and its methods control a chatroom
 */
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

    /**
     * the methods which gets automatically called when starting the stage
     * @param location
     * @param resources
     */
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

    /**
     * this method fills the List messages with the array of messages that it
     * gets from the server and database
     * @throws IOException
     */
    private void mineChats() throws IOException
    {
        usernameLabel.setText(ClientRunner.getInChatUsername());
        ChatRoom chatRoom = new ChatRoom(ClientRunner.getLoggedInUsername(), ClientRunner.getInChatUsername());
        Request request = new Request("GET_MESSAGES", ClientRunner.getGson().toJson(chatRoom));
        apiHandler.setRequest(request);
        apiHandler.sendRequest();
        Message[] messagesArray = apiHandler.receiveChatroomMessages();
        if (messagesArray == null)
        {
            return;
        }
        Collections.addAll(messages,messagesArray);
    }

    /**
     * is called when the send button is pressed.
     * sends a new message to server and adds it to the messages arrayList in this class
     * for the rest of the chat
     * @throws IOException
     */
    @FXML
    public void sendButtonOnAction() throws IOException
    {
        Message newMessage = new Message(ClientRunner.getLoggedInUsername(), ClientRunner.getInChatUsername(),messageTextField.getText());
        messages.add(newMessage);

        Request request = new Request("SAVE_MESSAGE", ClientRunner.getGson().toJson(newMessage));
        apiHandler.setRequest(request);
        apiHandler.sendRequest();

        continueChat();
        messageTextField.clear();
    }

    /**
     * shows the chats with making objects of Hbox and shows then upon the
     * chatroom stages one by one
     */
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

    /**
     * called after send button to show the newly added chats
     */
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

    /**
     * to exit the chatroom
     */
    @FXML
    public void closeButtonOnAction()
    {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}