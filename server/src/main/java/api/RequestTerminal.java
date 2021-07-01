package api;

import application.datacomponents.comment.Comment;
import application.datacomponents.directmessage.ChatRoom;
import application.datacomponents.directmessage.Message;
import application.datacomponents.followerfollowing.FollowerFollowingPack;
import application.datacomponents.like.Like;
import application.datacomponents.post.Post;
import application.datacomponents.signuplogin.User;
import application.requestahandlers.directmessages.DirectHandler;
import application.requestahandlers.followersfollowings.FollowersHandler;
import application.requestahandlers.posts.CommentHandler;
import application.requestahandlers.posts.LikeHandler;
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
    private SignupHandler signupHandler;
    private FollowersHandler followersHandler;
    private SearchHandler searchHandler;
    private LoginHandler loginHandler;
    private PostHandler postHandler;
    private CommentHandler commentHandler;
    private DirectHandler directHandler;
    private LikeHandler likeHandler;

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
        signupHandler = new SignupHandler(apiHandler);
        followersHandler = new FollowersHandler(apiHandler);
        searchHandler = new SearchHandler(apiHandler);
        loginHandler = new LoginHandler(apiHandler);
        postHandler = new PostHandler(apiHandler);
        commentHandler = new CommentHandler(apiHandler);
        directHandler = new DirectHandler(apiHandler);
        likeHandler = new LikeHandler(apiHandler);
    }

    private void routePicker() throws SQLException, IOException
    {
        while(true)
        {
            Request request = null;

            try {request = apiHandler.listenToClient();}
            catch (IOException e) {}

            switch (request.getLabel())
            {
                case "IS_NEW":
                    signupHandler.deliverIsNew(request.getBody());
                    break;
                case "SEND_USER":
                    signupHandler.addUser(gson.fromJson(request.getBody(), User.class));
                    break;
                case "SEND_FOLLOWER":
                    followersHandler.addFollower(gson.fromJson(request.getBody(), FollowerFollowingPack.class));
                    break;
                case "GET_SEARCH_RESULT":
                    searchHandler.deliverSearchReuslt(request.getBody());
                    break;
                case "GET_FOLLOWERS_COUNT":
                    followersHandler.deliverFollowersQuantity(request.getBody());
                    break;
                case "GET_FOLLOWINGS_COUNT":
                    followersHandler.deliverFollowingsQuantity(request.getBody());
                    break;
                case "GET_PASSWORD":
                    loginHandler.deliverPassword(request.getBody());
                    break;
                case "GET_BIO":
                    loginHandler.deliverBio(request.getBody());
                    break;
                case "SAVE_POST":
                    postHandler.addPost(gson.fromJson(request.getBody(), Post.class));
                    break;
                case "GET_POSTS_QUANTITY":
                    postHandler.deliverPostsQuantity(request.getBody());
                    break;
                case "GET_POST":
                    postHandler.deliverPost(request.getBody());
                    break;
                case "GET_POST_IMAGE":
                    postHandler.deliverPostImage(request.getBody());
                    break;
                case "SAVE_MESSAGE":
                    directHandler.addMessage(gson.fromJson(request.getBody(), Message.class));
                    break;
                case "GET_MESSAGES":
                    directHandler.deliverMessages(gson.fromJson(request.getBody(), ChatRoom.class));
                    break;
                case "ADD_COMMENT":
                    commentHandler.addComment(gson.fromJson(request.getBody(), Comment.class));
                    break;
                case "GET_COMMENTS_QUANTITY":
                    commentHandler.deliverCommentQuantity(request.getBody());
                    break;
                case "GET_COMMENTS":
                    commentHandler.deliverComments(request.getBody());
                    break;
                case "ADD_LIKE":
                    likeHandler.addLike(gson.fromJson(request.getBody(), Like.class));
                    break;
                case "GET_LIKES_QUANTITY":
                    likeHandler.deliverLikesQuantity(request.getBody());
                    break;
                case "GET_LIKERS":
                    likeHandler.deliverLikes(request.getBody());
                    break;
                case "GET_IS_LIKE_NEW":
                    likeHandler.deliverIsNew(gson.fromJson(request.getBody(),Like.class));
                    break;
                case "ADD_DISLIKE":
                    likeHandler.addDisLike(gson.fromJson(request.getBody(),Like.class));
                    break;
                case "GET_FOLLOWINGS":
                    followersHandler.deliverFollowings(request.getBody());
                    break;
                case "GET_FOLLOWERS":
                    followersHandler.deliverFollowers(request.getBody());
                    break;
                case "SET_PROFILE_PIC":
                    signupHandler.setProfilePic(request.getBody());
                    break;
                case "IS_PROFILE_PIC_SET":
                    signupHandler.deliverIsPicSet(request.getBody());
                    break;
                case "GET_PROFILE_PIC":
                    signupHandler.deliverProfilePic(request.getBody());
                    break;
                case "IS_FOLLOWED":
                    followersHandler.deliverIsFollowed(request.getBody());
                    break;
                case "IS_LIKED":
                    likeHandler.deliverIsLiked(request.getBody());
                    break;
                case "ADD_BIO":
                    loginHandler.setBio(request.getBody());
                    break;
            }
        }
    }
}