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
        Request request = new Request("GET_FOLLOWINGS_COUNT","muhammad.ksht");
        ApiHandler apiHandler = new ApiHandler(request);
        apiHandler.sendRequest();
        System.out.println(apiHandler.receiveFollowerFollowingQuantity());
    }
}
