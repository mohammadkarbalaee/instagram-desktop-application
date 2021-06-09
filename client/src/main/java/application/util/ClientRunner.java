package application.util;

import api.ApiHandler;
import api.Request;
import api.RequestPipeline;
import application.followerfollowing.FollowerFollowingPack;
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
        FollowerFollowingPack pack = new FollowerFollowingPack("reyhan","muhammad.ksht", false);
        Request request1 = new Request("SEND_FOLLOWER",gson.toJson(pack));
        ApiHandler apiHandler = new ApiHandler(request1);
        apiHandler.sendRequest();
        Request request2 = new Request("GET_SEARCH_RESULT","reyhan");
        apiHandler.setRequest(request2);
        apiHandler.sendRequest();
        System.out.println(apiHandler.receiveSearchResult());
        in.next();
    }
}
