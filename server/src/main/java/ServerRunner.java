import api.RequestTerminal;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerRunner
{
    public static void main(String[] args) throws IOException, SQLException
    {
        ServerSocket serverSocket =  new ServerSocket(8080);
        ExecutorService threadPool = Executors.newFixedThreadPool(100);

        while (true)
        {
            RequestTerminal clientHandler = new RequestTerminal(serverSocket.accept());
            threadPool.execute(clientHandler);
        }
    }
}
