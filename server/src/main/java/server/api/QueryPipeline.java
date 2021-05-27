package server.api;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class QueryPipeline
{
    private ServerSocket serverSocket;
    private Socket socket;
    protected static DataInputStream dataInputStream;
    protected static DataOutputStream dataOutputStream;

    public void initialize(ServerSocket serverSocket) throws IOException
    {
        this.serverSocket = serverSocket;
        socket = serverSocket.accept();
        dataInputStream = new DataInputStream(this.socket.getInputStream());
        dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
    }
}
