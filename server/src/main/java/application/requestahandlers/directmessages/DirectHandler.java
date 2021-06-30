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
        ChatRoom chatRoom2 = new ChatRoom(chatRoom.getReceiver(), chatRoom.getSender());

        Response response;

        if (DatabaseManager.checkChatroomTableExistence(chatRoom))
        {
            response = new Response(DatabaseManager.getMessages(chatRoom));
        }
        else if (DatabaseManager.checkChatroomTableExistence(chatRoom2))
        {
            response = new Response(DatabaseManager.getMessages(chatRoom2));
        }
        else
        {
            response = new Response(null);
        }

        apiHandler.answerToClient(response);
    }

    public void addMessage(Message message) throws SQLException
    {
        ChatRoom chatRoom1 = new ChatRoom(message.getSender(),message.getReceiver());
        ChatRoom chatRoom2 = new ChatRoom(message.getReceiver(),message.getSender());

        if (DatabaseManager.checkChatroomTableExistence(chatRoom1))
        {
            DatabaseManager.addMessageToChatroom(chatRoom1,message);
        }
        else if (DatabaseManager.checkChatroomTableExistence(chatRoom2))
        {
            DatabaseManager.addMessageToChatroom(chatRoom2,message);
        }
        else
        {
            DatabaseManager.createChatroomTable(chatRoom1);
            DatabaseManager.addMessageToChatroom(chatRoom1,message);
        }
    }
}