package sample.backend.api;

import com.google.gson.Gson;
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

public class ApiHandler
{
    private final Gson gson = new Gson();
    private Request request;

    public ApiHandler() {}

    public ApiHandler(Request request)
    {
        this.request = request;
    }

    public void setRequest(Request request)
    {
        this.request = request;
    }

    public void sendRequest() throws IOException
    {
        RequestPipeline.getDataOutputStream().writeUTF(gson.toJson(request));
        RequestPipeline.getDataOutputStream().flush();
    }

    public Request getRequest()
    {
        return request;
    }

    public boolean receiveIsNew() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = gson.fromJson(json,Response.class);
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

    public String receiveSearchResult() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = gson.fromJson(json,Response.class);
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

    public Integer receiveQuantity() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = gson.fromJson(json,Response.class);
        String body = responseObject.getBody();
        return Integer.parseInt(body);
    }

    public String receivePlainString() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = gson.fromJson(json,Response.class);
        return responseObject.getBody();
    }

    public Post receiveWantedPost() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = gson.fromJson(json,Response.class);
        String body = responseObject.getBody();
        return gson.fromJson(body,Post.class);
    }

    public void sendPhoto(File file) throws IOException
    {
        BufferedImage bufferedImage = ImageIO.read(file);
        ImageIO.write(bufferedImage,"png",RequestPipeline.getDataOutputStream());
        bufferedImage.flush();
        RequestPipeline.getDataOutputStream().flush();
    }

    public BufferedImage receivePhoto() throws IOException
    {
        byte[] byteData = new byte[RequestPipeline.getDataInputStream().readInt()];
        RequestPipeline.getDataInputStream().readFully(byteData);
        InputStream byteStream = new ByteArrayInputStream(byteData);
        return ImageIO.read(byteStream);
    }

    public Message[] receiveChatroomMessages() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = gson.fromJson(json,Response.class);
        String body = responseObject.getBody();
        String[] messageObjects = body.split("/");
        Message[] messages = new Message[messageObjects.length];
        for (int i = 0; i < messages.length; i++)
        {
            messages[i] = gson.fromJson(messageObjects[i],Message.class);
        }
        return messages;
    }

    public Comment[] receiveComments() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = gson.fromJson(json,Response.class);
        String body = responseObject.getBody();
        String[] commentObjects = body.split("@");
        Comment[] comments = new Comment[commentObjects.length];
        for (int i = 0; i < comments.length; i++)
        {
            comments[i] = gson.fromJson(commentObjects[i],Comment.class);
        }
        return comments;
    }

    public Like[] receiveLikes() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = gson.fromJson(json,Response.class);
        String body = responseObject.getBody();
        String[] likeObjects = body.split("@");
        Like[] likes = new Like[likeObjects.length];
        for (int i = 0; i < likes.length; i++)
        {
            likes[i] = gson.fromJson(likeObjects[i],Like.class);
        }
        return likes;
    }

    public String[] receiveFollowersFollowings() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = gson.fromJson(json,Response.class);
        String followingsUsernames = responseObject.getBody();
        System.out.println("in api followings " + followingsUsernames);
        if (followingsUsernames.equals(""))
        {
            return null;
        }
        return followingsUsernames.split("/");
    }

    public boolean receiveTrueFalse() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = gson.fromJson(json,Response.class);
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