package sample.backend;

import com.google.gson.Gson;
import sample.backend.api.ApiHandler;
import sample.backend.api.Request;
import sample.backend.api.RequestPipeline;
import sample.backend.comment.Comment;

import java.io.IOException;
import java.util.Scanner;

public class ClientBackendTester
{
    public static void main(String[] args) throws IOException
    {
        Scanner in = new Scanner(System.in);
        Gson gson = new Gson();
        RequestPipeline.build();
        Request request = new Request("GET_COMMENTS","reyhan/1");
        ApiHandler apiHandler = new ApiHandler(request);
        apiHandler.sendRequest();
        Comment[] comments = apiHandler.receiveComments();
        for (int i = 0; i < comments.length; i++)
        {
            System.out.println(comments[i].getText());
        }
    }
}
