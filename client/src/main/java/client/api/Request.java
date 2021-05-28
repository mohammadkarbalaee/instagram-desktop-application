package client.api;

import application.User;
import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;

public class Request
{
    private User userToSend;
    private AllQueries query;

    public Request(AllQueries query,User user)
    {
        userToSend = user;
        this.query = query;
    }

    public void askingServer(DataOutputStream dataOutputStream) throws IOException
    {
        dataOutputStream.writeInt(query.getLevelCode());
    }

    public void sendAccount(DataOutputStream dataOutputStream) throws IOException
    {
        Gson gson = new Gson();
        String userJsonString = gson.toJson(userToSend);
        dataOutputStream.writeUTF(userJsonString);
    }

    public AllQueries getQuery()
    {
        return query;
    }

}