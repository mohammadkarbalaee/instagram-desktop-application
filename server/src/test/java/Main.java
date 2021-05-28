import application.User;
import com.google.gson.Gson;
import server.api.QueryPipeline;

import java.io.IOException;
import java.net.ServerSocket;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = new ServerSocket(8080);
        QueryPipeline queryPipeline = new QueryPipeline(serverSocket);
        String json = queryPipeline.listen();
        Gson gson = new Gson();
        User user = gson.fromJson(json,User.class);
        System.out.println(user.toString());
    }
}