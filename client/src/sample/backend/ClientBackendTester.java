package sample.backend;

import com.google.gson.Gson;
import sample.backend.api.ApiHandler;
import sample.backend.api.Request;
import sample.backend.api.RequestPipeline;
import sample.backend.application.like.Like;

import java.io.IOException;
import java.util.Scanner;

public class ClientBackendTester
{
    public static void main(String[] args) throws IOException
    {
        Scanner in = new Scanner(System.in);
        Gson gson = new Gson();
        RequestPipeline.build();
        Like like = new Like("muhammad.ksht","reyhan/1");
        Request request = new Request("IS_LIKED",gson.toJson(like));
        ApiHandler apiHandler = new ApiHandler(request);
        apiHandler.sendRequest();
        System.out.println(apiHandler.receiveTrueFalse());
    }
}
