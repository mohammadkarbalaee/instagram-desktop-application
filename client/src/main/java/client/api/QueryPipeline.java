package client.api;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class QueryPipeline
{
    private Socket socket;
    protected static DataInputStream dataInputStream;
    protected static DataOutputStream dataOutputStream;

    public void initialize(Socket socket) throws IOException
    {
        this.socket = socket;
        dataInputStream = new DataInputStream(this.socket.getInputStream());
        dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
    }

    public String readUTF() throws IOException
    {
        return dataInputStream.readUTF();
    }
}
