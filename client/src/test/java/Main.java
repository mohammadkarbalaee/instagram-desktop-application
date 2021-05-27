import server.api.QueryHandler;

import java.io.IOException;
import java.net.ServerSocket;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = new ServerSocket(8080);
        QueryHandler queryHandler = new QueryHandler();
        queryHandler.initialize(serverSocket);
        queryHandler.listen();
    }
}