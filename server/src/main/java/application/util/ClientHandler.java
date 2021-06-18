package application.util;

import api.ApiHandler;
import api.Request;
import api.RequestPipeline;
import api.Response;
import application.datamanagement.database.DatabaseManager;
import application.util.directmessage.ChatRoom;
import application.util.directmessage.Message;
import application.util.followerfollowing.FollowerFollowingPack;
import application.util.post.Post;
import application.util.search.SearchResult;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class ClientHandler implements Runnable
{
    private final Socket socket;
    private ApiHandler apiHandler;
    private final Gson gson = new Gson();

    public ClientHandler(Socket socket) throws IOException
    {
        this.socket = socket;
        build();
    }

    private void build() throws IOException
    {
        RequestPipeline requestPipeline = new RequestPipeline(socket);
        apiHandler = new ApiHandler(requestPipeline);
    }

    @Override
    public void run()
    {
        try{ action();}
        catch (SQLException | IOException throwables) {throwables.printStackTrace();}
    }

    private void action() throws SQLException, IOException
    {
        while(true)
        {
            Request request = null;

            try {request = apiHandler.listenToClient();}
            catch (IOException e) {}

            assert request != null;
            if (request.getLabel().equals("IS_NEW"))
            {
                try
                {
                    if (!DatabaseManager.checkExistence(request.getBody()))
                    {
                        Response isNew = new Response("true");
                        apiHandler.answerToClient(isNew);
                    }
                    else
                    {
                        Response isNew = new Response("false");
                        apiHandler.answerToClient(isNew);
                    }
                }
                catch (SQLException | IOException throwables) {}
            }
            else if(request.getLabel().equals("SEND_USER"))
            {
                try
                {
                    DatabaseManager.addUserRecord(gson.fromJson(request.getBody(), User.class));
                }
                catch (SQLException throwables) {}
            }
            else if (request.getLabel().equals("SEND_FOLLOWER"))
            {
                FollowerFollowingPack pack = gson.fromJson(request.getBody(),FollowerFollowingPack.class);
                DatabaseManager.setFollowerFollowing(pack);
            }
            else if(request.getLabel().equals("GET_SEARCH_RESULT"))
            {
                SearchResult searchResult = DatabaseManager.getSearchResult(request.getBody());
                if (searchResult == null)
                {
                    Response response = new Response("null");
                    apiHandler.answerToClient(response);
                }
                else
                {
                    Response response = new Response(gson.toJson(searchResult));
                    apiHandler.answerToClient(response);
                }
            }
            else if (request.getLabel().equals("GET_FOLLOWERS_COUNT"))
            {
                Response response = new Response(DatabaseManager.getFollowersQuantity(request.getBody()).toString());
                apiHandler.answerToClient(response);
            }
            else if (request.getLabel().equals("GET_FOLLOWINGS_COUNT"))
            {
                Response response = new Response(DatabaseManager.getFollowingsQuantity(request.getBody()).toString());
                apiHandler.answerToClient(response);
            }
            else if (request.getLabel().equals("GET_PASSWORD"))
            {
                Response response = new Response(DatabaseManager.getPassword(request.getBody()));
                apiHandler.answerToClient(response);
            }
            else if (request.getLabel().equals("SAVE_POST"))
            {
                Post post = gson.fromJson(request.getBody(),Post.class);
                DatabaseManager.savePost(post);
            }
            else if (request.getLabel().equals("GET_POSTS_QUANTITY"))
            {
                Response response = new Response(DatabaseManager.getPostQuantity(request.getBody()).toString());
                apiHandler.answerToClient(response);
            }
            else if (request.getLabel().equals("GET_POST"))
            {
                String[] pair = request.getBody().split("/");
                Post wantedPost = DatabaseManager.getPost(pair[0],pair[1]);
                Response response = new Response(gson.toJson(wantedPost));
                apiHandler.answerToClient(response);
            }
            else if (request.getLabel().equals("SAVE_MESSAGE"))
            {
                Message message = gson.fromJson(request.getBody(),Message.class);
                ChatRoom chatRoom = new ChatRoom(message.getSender(),message.getReceiver());
                if (DatabaseManager.checkChatroomTableExistence(chatRoom))
                {
                    System.out.println("i am adding");
                    DatabaseManager.addMessageToChatroom(chatRoom,message);
                }
                else
                {
                    System.out.println("i am making");
                    System.out.println(chatRoom.getChatroomTableName());
                    DatabaseManager.createChatroomTable(chatRoom);
                    DatabaseManager.addMessageToChatroom(chatRoom,message);
                }
            }
            else if (request.getLabel().equals("GET_MESSAGES"))
            {
                ChatRoom chatRoom = gson.fromJson(request.getBody(),ChatRoom.class);
                Response response = new Response(DatabaseManager.getMessages(chatRoom));
                apiHandler.answerToClient(response);
            }
        }
    }
}