package api;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RequestPipeline
{
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Socket socket;

    public RequestPipeline(Socket socket) throws IOException
    {
        this.socket = socket;
        build();
    }

    private void build() throws IOException
    {
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
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
