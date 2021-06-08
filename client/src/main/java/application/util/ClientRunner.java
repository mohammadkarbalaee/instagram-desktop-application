package application.util;

import api.ApiHandler;
import api.Request;
import api.RequestPipeline;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Scanner;

public class ClientRunner
{
    public static void main(String[] args) throws IOException
    {
        Scanner in = new Scanner(System.in);
        Gson gson = new Gson();
        RequestPipeline.build();
        FollowerFollowingPack pack = new FollowerFollowingPack("reyhan","muhammad.ksht", true);
        Request request = new Request("SEND_FOLLOWER",gson.toJson(pack));
        ApiHandler apiHandler = new ApiHandler(request);
        apiHandler.sendRequest();
        in.next();
    }
}
