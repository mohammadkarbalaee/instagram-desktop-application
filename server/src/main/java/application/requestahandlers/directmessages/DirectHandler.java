package application.requestahandlers.directmessages;

import api.ApiHandler;
import api.Response;
import application.datacomponents.directmessage.ChatRoom;
import application.datacomponents.directmessage.Message;
import application.datamanagement.database.DatabaseManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.SQLException;

public class DirectHandler
{
    private ApiHandler apiHandler;
    private static Gson gson = new Gson();

    public DirectHandler(ApiHandler apiHandler)
    {
        this.apiHandler = apiHandler;
    }

    public void deliverMessages(ChatRoom chatRoom) throws SQLException, IOException
    {
        Response response = new Response(DatabaseManager.getMessages(chatRoom));
        apiHandler.answerToClient(response);
    }

    public void addMessage(Message message) throws SQLException
    {
        ChatRoom chatRoom = new ChatRoom(message.getSender(),message.getReceiver());
        if (DatabaseManager.checkChatroomTableExistence(chatRoom))
        {
            DatabaseManager.addMessageToChatroom(chatRoom,message);
        }
        else
        {
            DatabaseManager.createChatroomTable(chatRoom);
            DatabaseManager.addMessageToChatroom(chatRoom,message);
        }
    }
}
