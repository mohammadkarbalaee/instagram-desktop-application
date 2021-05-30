package api;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RequestPipeline
{
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public RequestPipeline(Socket socket) throws IOException
    {
        initialize(socket);
    }

    private void initialize(Socket socket) throws IOException
    {
        this.socket = socket;
        dataInputStream = new DataInputStream(this.socket.getInputStream());
        dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
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
