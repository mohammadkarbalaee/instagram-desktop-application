package api;

import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class RequestPipeline
{
    private ServerSocket serverSocket;
    private Socket socket;
    protected DataInputStream dataInputStream;
    protected DataOutputStream dataOutputStream;

    public RequestPipeline(ServerSocket serverSocket) throws IOException
    {
        this.serverSocket = serverSocket;
        initialize();
    }

    private void initialize() throws IOException
    {
        socket = serverSocket.accept();
        dataInputStream = new DataInputStream(this.socket.getInputStream());
        dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
    }

    public Request listen() throws IOException
    {
        Gson gson = new Gson();
        return gson.fromJson(dataInputStream.readUTF(),Request.class);
    }

    public DataInputStream getDataInputStream()
    {
        return dataInputStream;
    }

    public DataOutputStream getDataOutputStream()
    {
        return dataOutputStream;
    }
}
