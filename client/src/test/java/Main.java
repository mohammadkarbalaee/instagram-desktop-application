import application.User;
import api.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.Socket;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        Socket socket = new Socket("localhost",8080);
        RequestPipeline queryPipeline = new RequestPipeline(socket);
        Gson gson = new Gson();
        User user = new User("muhammad","m@m.m","1234");
        Request request = new Request("SEND_USER",gson.toJson(user));
        Handler handler = new Handler(request,queryPipeline);
        handler.send();
        User user2 = new User("rey","r@r.r","1234");
        Request request2 = new Request("SEND_USER",gson.toJson(user2));
        handler.setRequest(request2);
        handler.send();
    }
}