package api;

import application.datacomponents.comment.Comment;
import application.datacomponents.directmessage.ChatRoom;
import application.datacomponents.directmessage.Message;
import application.datacomponents.followerfollowing.FollowerFollowingPack;
import application.datacomponents.post.Post;
import application.datacomponents.signuplogin.User;
import application.requestahandlers.directmessages.DirectHandler;
import application.requestahandlers.followersfollowings.FollowersHandler;
import application.requestahandlers.posts.CommentHandler;
import application.requestahandlers.posts.PostHandler;
import application.requestahandlers.searches.SearchHandler;
import application.requestahandlers.signupslogins.LoginHandler;
import application.requestahandlers.signupslogins.SignupHandler;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class RequestTerminal implements Runnable
{
    private final Socket socket;
    private ApiHandler apiHandler;
    private final Gson gson = new Gson();

    public RequestTerminal(Socket socket) throws IOException
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
        buildHandlers();
        try
        {
            routePicker();
        }
        catch (SQLException | IOException throwables)
        {
            throwables.printStackTrace();
        }
    }

    private void buildHandlers()
    {
        SignupHandler.build(apiHandler);
        FollowersHandler.build(apiHandler);
        SearchHandler.build(apiHandler);
        LoginHandler.build(apiHandler);
        PostHandler.build(apiHandler);
        CommentHandler.build(apiHandler);
        DirectHandler.build(apiHandler);
    }

    private void routePicker() throws SQLException, IOException
    {
        while(true)
        {
            Request request = apiHandler.listenToClient();

            switch (request.getLabel())
            {
                case "IS_NEW":
                    SignupHandler.deliverIsNew(request.getBody());
                    break;
                case "SEND_USER":
                    SignupHandler.addUser(gson.fromJson(request.getBody(), User.class));
                    break;
                case "SEND_FOLLOWER":
                    FollowersHandler.addFollower(gson.fromJson(request.getBody(), FollowerFollowingPack.class));
                    break;
                case "GET_SEARCH_RESULT":
                    SearchHandler.deliverSearchReuslt(request.getBody());
                    break;
                case "GET_FOLLOWERS_COUNT":
                    FollowersHandler.deliverFollowersQuantity(request.getBody());
                    break;
                case "GET_FOLLOWINGS_COUNT":
                    FollowersHandler.deliverFollowingsQuantity(request.getBody());
                    break;
                case "GET_PASSWORD":
                    LoginHandler.deliverPassword(request.getBody());
                    break;
                case "SAVE_POST":
                    PostHandler.addPost(gson.fromJson(request.getBody(), Post.class));
                    break;
                case "GET_POSTS_QUANTITY":
                    PostHandler.deliverPostsQuantity(request.getBody());
                    break;
                case "GET_POST":
                    PostHandler.deliverPost(request.getBody());
                    break;
                case "SAVE_MESSAGE":
                    DirectHandler.addMessage(gson.fromJson(request.getBody(), Message.class));
                    break;
                case "GET_MESSAGES":
                    DirectHandler.deliverMessages(gson.fromJson(request.getBody(), ChatRoom.class));
                    break;
                case "ADD_COMMENT":
                    CommentHandler.addComment(gson.fromJson(request.getBody(), Comment.class));
                    break;
                case "GET_COMMENTS_QUANTITY":
                    CommentHandler.deliverCommentQuantity(request.getBody());
                    break;
                case "GET_COMMENTS":
                    CommentHandler.deliverComments(request.getBody());
                    break;
            }
        }
    }
}