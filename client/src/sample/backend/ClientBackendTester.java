package sample.backend;

import com.google.gson.Gson;
import sample.backend.api.ApiHandler;
import sample.backend.api.Request;
import sample.backend.api.RequestPipeline;
import sample.backend.application.followerfollowing.FollowerFollowingPack;

import java.io.IOException;
import java.util.Scanner;

public class ClientBackendTester
{
    public static void main(String[] args) throws IOException
    {
        Scanner in = new Scanner(System.in);
        Gson gson = new Gson();
        RequestPipeline.build();
        FollowerFollowingPack followerFollowingPack = new FollowerFollowingPack("hasan","muhammad.ksht",false);
        Request request = new Request("SEND_FOLLOWER",gson.toJson(followerFollowingPack));
        ApiHandler apiHandler = new ApiHandler(request);
        apiHandler.sendRequest();
    }
}
