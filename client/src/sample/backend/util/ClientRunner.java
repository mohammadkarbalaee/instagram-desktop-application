package sample.backend.util;


import com.google.gson.Gson;
import sample.api.ApiHandler;
import sample.api.Request;
import sample.api.RequestPipeline;
import sample.backend.directmessage.ChatRoom;
import sample.backend.directmessage.Message;
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
        ChatRoom chatRoom = new ChatRoom("reyhan","muhammad_ksht");
        Request request = new Request("GET_MESSAGES",gson.toJson(chatRoom));
        ApiHandler apiHandler = new ApiHandler(request);
        apiHandler.sendRequest();
        Message[] messages = apiHandler.receiveChatroomMessages();
        System.out.println(messages[0].getText());
    }
}
