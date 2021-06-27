package sample.backend;

import com.google.gson.Gson;
import sample.backend.api.ApiHandler;
import sample.backend.api.Request;
import sample.backend.api.RequestPipeline;

import java.io.IOException;
import java.util.Scanner;

public class ClientBackendTester
{
    public static void main(String[] args) throws IOException
    {
        Scanner in = new Scanner(System.in);
        Gson gson = new Gson();
        RequestPipeline.build();
        Request request3 = new Request("GET_POSTS_QUANTITY","bdbgf");
        ApiHandler apiHandler = new ApiHandler(request3);
        apiHandler.sendRequest();
        System.out.println("QUANTITY " + apiHandler.receiveQuantity());
        Request request2 = new Request("GET_POST","hasan/1");
        apiHandler.setRequest(request2);
        apiHandler.sendRequest();
        System.out.println(apiHandler.receiveWantedPost().getCaption());
        Request request1 = new Request("GET_POSTS_QUANTITY","reyhan");
        apiHandler.setRequest(request1);
        apiHandler.sendRequest();
        System.out.println("QUANTITY " + apiHandler.receiveQuantity());
        Request request4 = new Request("GET_POST","reyhan/1");
        apiHandler.setRequest(request4);
        apiHandler.sendRequest();
        System.out.println(apiHandler.receiveWantedPost().getCaption());
    }
}
