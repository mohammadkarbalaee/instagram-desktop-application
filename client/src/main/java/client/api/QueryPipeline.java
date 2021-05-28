package client.api;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class QueryPipeline
{
    private Socket socket;
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;

    public QueryPipeline(Socket socket) throws IOException
    {
        initialize(socket);
    }

    private void initialize(Socket socket) throws IOException
    {
        this.socket = socket;
        dataInputStream = new DataInputStream(this.socket.getInputStream());
        dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
    }

    public void sendRequest(Request request) throws IOException
    {
        request.askingServer(dataOutputStream);
        switch (request.getQuery())
        {
            case SEND_ACCOUNT: request.sendAccount(dataOutputStream); break;
        }
    }

    public String readUTF() throws IOException
    {
        return dataInputStream.readUTF();
    }
}
