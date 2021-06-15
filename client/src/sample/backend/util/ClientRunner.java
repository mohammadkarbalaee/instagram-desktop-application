package sample.backend.util;


import com.google.gson.Gson;
import sample.api.ApiHandler;
import sample.api.Request;
import sample.api.RequestPipeline;

import java.io.IOException;
import java.util.Scanner;

public class ClientRunner
{
    public static void main(String[] args) throws IOException
    {
        Scanner in = new Scanner(System.in);
        Gson gson = new Gson();
        RequestPipeline.build();
        Request
                request = new Request("GET_FOLLOWINGS_COUNT","muhammad.ksht");
        ApiHandler
                apiHandler = new ApiHandler(request);
        apiHandler.sendRequest();
        System.out.println(apiHandler.receiveFollowerFollowingQuantity());
    }
}
