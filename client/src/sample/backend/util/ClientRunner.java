package sample.backend.util;


import com.google.gson.Gson;
import sample.api.ApiHandler;
import sample.api.Request;
import sample.api.RequestPipeline;
import sample.backend.post.Post;

import java.io.IOException;
import java.util.Scanner;

public class ClientRunner
{
    public static void main(String[] args) throws IOException
    {
        Scanner in = new Scanner(System.in);
        Gson gson = new Gson();
        RequestPipeline.build();
        Request request = new Request("GET_POST","muhammad.ksht/1");
        ApiHandler apiHandler = new ApiHandler(request);
        apiHandler.sendRequest();
        Post post = apiHandler.receiveWantedPost();
        System.out.println(post.getCaption());
    }
}
