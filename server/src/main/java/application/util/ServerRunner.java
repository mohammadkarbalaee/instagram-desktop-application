package application.util;

import application.datamanagement.database.DatabaseManager;
import application.util.directmessage.ChatRoom;

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
            ClientHandler clientHandler = new ClientHandler(serverSocket.accept());
            threadPool.execute(clientHandler);
        }
    }
}
