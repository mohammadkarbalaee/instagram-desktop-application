import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket =  new ServerSocket(8080);
        ExecutorService threadPool = Executors.newFixedThreadPool(8);

    }
}