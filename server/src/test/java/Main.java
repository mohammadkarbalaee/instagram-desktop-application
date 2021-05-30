import api.Handler;
import api.Response;
import application.User;
import com.google.gson.Gson;
import api.RequestPipeline;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = new ServerSocket(8080);
        RequestPipeline queryPipeline = new RequestPipeline(serverSocket);
        Handler handler = new Handler(queryPipeline);
        handler.listen();
        Object object = handler.getObject();
        User user1 = (User) object;
        handler.listen();
        Object object2 = handler.getObject();
        User user2 = (User) object2;
        ArrayList<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        System.out.println(users);
    }
}