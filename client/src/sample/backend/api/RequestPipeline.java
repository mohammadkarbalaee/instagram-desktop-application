package sample.backend.api;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Muhammad Karabalaee Shabani
 * a class to represent a RequestPipeline, which is socket connnetion
 * between the server and client and the input/output streams on this socket to send requests and
 * receive the related responses
 */
public class RequestPipeline
{
    private static Socket socket;
    static
    {
        try
        {
            socket = new Socket("localhost",8080);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;

    public RequestPipeline()
    {

    }

    public static void build() throws IOException
    {
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public static DataInputStream getDataInputStream()
    {
        return dataInputStream;
    }

    public static DataOutputStream getDataOutputStream()
    {
        return dataOutputStream;
    }
}
