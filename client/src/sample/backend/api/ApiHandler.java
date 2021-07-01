package sample.backend.api;

import sample.backend.application.comment.Comment;
import sample.backend.application.directmessage.Message;
import sample.backend.application.like.Like;
import sample.backend.application.post.Post;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static sample.frontend.ClientRunner.getGson;

/**
 * @author Muhammad Karbalaee Shabani
 * this class contains methods that handle requests and responses when communicating with server
 */

public class ApiHandler
{
    private Request request;

    /**
     * empty constructor for usecases whithout given request when making new object
     */
    public ApiHandler() {}

    /**
     * constructor to initialize a new instance with an object of Request
     * @param request
     */
    public ApiHandler(Request request)
    {
        this.request = request;
    }

    /**
     * a setter for Request field
     * @param request
     */
    public void setRequest(Request request)
    {
        this.request = request;
    }

    /**
     * to send the request to server through socket
     * @throws IOException
     */
    public void sendRequest() throws IOException
    {
        RequestPipeline.getDataOutputStream().writeUTF(getGson().toJson(request));
        RequestPipeline.getDataOutputStream().flush();
    }

    /**
     *
     * @return the request field
     */
    public Request getRequest()
    {
        return request;
    }

    /**
     * used to receive the response to uniqueness of a user.
     * this method is used after an "IS_NEW"-labeled request
     * @return a boolean which is true if the user is new and otherwise,false
     * @throws IOException
     */
    public boolean receiveIsNew() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = getGson().fromJson(json,Response.class);
        String response = responseObject.getBody();
        if (response.equals("true"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * this method is used after a request of "GET_SEARCH_RESULT"
     * @return an string containing the username of the searched user
     * @throws IOException
     */
    public String receiveSearchResult() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = getGson().fromJson(json,Response.class);
        String searchResultJson = responseObject.getBody();
        if (searchResultJson.equals("null"))
        {
            return null;
        }
        else
        {
            return searchResultJson;
        }
    }

    /**
     *
     * @return this method is used with every request that acquires a single integer as the response
     * namely,"GET_FOLLOWERS_COUNT" "GET_FOLLOWINGS_COUNT" "GET_POSTS_QUANTITY" "GET_COMMENTS_QUANTITY"
     *"GET_LIKES_QUANTITY"
     * @throws IOException
     */
    public Integer receiveQuantity() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = getGson().fromJson(json,Response.class);
        String body = responseObject.getBody();
        return Integer.parseInt(body);
    }

    /**
     *
     * @return the non-jason string received from "GET_PASSWORD" "GET_PASSWORD requests
     * @throws IOException
     */
    public String receivePlainString() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = getGson().fromJson(json,Response.class);
        return responseObject.getBody();
    }

    /**
     *
     * @return a post which was requested with "GET_POST"
     * @throws IOException
     */
    public Post receiveWantedPost() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = getGson().fromJson(json,Response.class);
        String body = responseObject.getBody();
        return getGson().fromJson(body,Post.class);
    }

    /**
     *
     * @param file an object of File class. returned from the FileChooser object in setProfilePic
     *             and in savePost
     * @throws IOException
     */
    public void sendPhoto(File file) throws IOException
    {
        BufferedImage bufferedImage = ImageIO.read(file);
        ImageIO.write(bufferedImage,"png",RequestPipeline.getDataOutputStream());
        bufferedImage.flush();
        RequestPipeline.getDataOutputStream().flush();
    }

    /**
     * every photo is received with this method after being requested
     * @return a BufferedImage
     * @throws IOException
     */
    public BufferedImage receivePhoto() throws IOException
    {
        byte[] byteData = new byte[RequestPipeline.getDataInputStream().readInt()];
        RequestPipeline.getDataInputStream().readFully(byteData);
        InputStream byteStream = new ByteArrayInputStream(byteData);
        return ImageIO.read(byteStream);
    }

    /**
     * to receive the the requested data by "GET_MESSAGES" request
     * @return and Array of Messages
     * @throws IOException
     */
    public Message[] receiveChatroomMessages() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = getGson().fromJson(json,Response.class);
        String body = responseObject.getBody();
        if (body == null)
        {
            return null;
        }
        String[] messageObjects = body.split("/");
        Message[] messages = new Message[messageObjects.length];
        for (int i = 0; i < messages.length; i++)
        {
            if (messageObjects[0].equals(""))
            {
                return null;
            }
            messages[i] = getGson().fromJson(messageObjects[i],Message.class);
        }
        return messages;
    }

    /**
     * to receive the the requested data by "GET_COMMENTS" request
     * @return and Array of Comments
     * @throws IOException
     */
    public Comment[] receiveComments() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = getGson().fromJson(json,Response.class);
        String body = responseObject.getBody();
        String[] commentObjects = body.split("@");
        Comment[] comments = new Comment[commentObjects.length];
        for (int i = 0; i < comments.length; i++)
        {
            if (commentObjects[i].equals(""))
            {
                break;
            }
            comments[i] = getGson().fromJson(commentObjects[i],Comment.class);
        }
        return comments;
    }

    /**
     * receives the respone for "GET_LIKERS" request
     * @return an String array of usernames of the people who liked the post
     * @throws IOException
     */
    public String[] receiveLikes() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = getGson().fromJson(json,Response.class);
        String body = responseObject.getBody();
        String[] likeObjects = body.split("@");
        String[] likers = new String[likeObjects.length];
        for (int i = 0; i < likers.length; i++)
        {
            if (likeObjects[i].equals(""))
            {
                return null;
            }
            Like temp = getGson().fromJson(likeObjects[i],Like.class);
            likers[i] = temp.getLiker();
        }
        return likers;
    }

    /**
     * receives the respone for "GET_FOLLOWERS" or "GET_FOLLOWINGS" request
     * @return an String of the usernames of the people whom either followers or followings
     * @throws IOException
     */
    public String[] receiveFollowersFollowings() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = getGson().fromJson(json,Response.class);
        String followingsUsernames = responseObject.getBody();
        if (followingsUsernames.equals(""))
        {
            return null;
        }
        return followingsUsernames.split("/");
    }

    /**
     * every request which expects a boolean as the response is handled by this method
     * namely,"IS_FOLLOWED" "IS_LIKED" "IS_PROFILE_PIC_SET"
     * @return a boolean
     * @throws IOException
     */
    public boolean receiveTrueFalse() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = getGson().fromJson(json,Response.class);
        String response = responseObject.getBody();
        if (response.equals("true"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}