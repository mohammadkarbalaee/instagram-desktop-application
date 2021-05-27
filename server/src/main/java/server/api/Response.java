package server.api;

import java.io.IOException;
import java.net.ServerSocket;

public class Response extends QueryPipeline
{
    public void respondingToClient() throws IOException
    {
        QueryPipeline.dataOutputStream.writeUTF("here is your account");
    }
}
