package server.api;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static server.api.AllQueries.SEND_ACCOUNT;

public class QueryPipeline
{
    private ServerSocket serverSocket;
    private Socket socket;
    private int levelCode;
    protected static DataInputStream dataInputStream;
    protected static DataOutputStream dataOutputStream;

    public QueryPipeline(ServerSocket serverSocket) throws IOException
    {
        initialize(serverSocket);
    }

    private void initialize(ServerSocket serverSocket) throws IOException
    {
        this.serverSocket = serverSocket;
        socket = serverSocket.accept();
        dataInputStream = new DataInputStream(this.socket.getInputStream());
        dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
    }

    public String listen() throws IOException
    {
        levelCode = dataInputStream.readInt();
        return action(toQuery(levelCode));
    }

    private AllQueries toQuery(int levelCode)
    {
        AllQueries query = null;
        switch (levelCode)
        {
            case 1: query = SEND_ACCOUNT;
        }
        return query;
    }

    private String action(AllQueries query) throws IOException
    {
        String json = null;
        switch (query)
        {
            case SEND_ACCOUNT: json = dataInputStream.readUTF(); break;
        }
        return json;
    }
}
