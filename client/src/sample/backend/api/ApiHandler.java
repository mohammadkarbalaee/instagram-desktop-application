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

public class ApiHandler
{
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
        RequestPipeline.getDataOutputStream().writeUTF(getGson().toJson(request));
        RequestPipeline.getDataOutputStream().flush();
    }

    public Request getRequest()
    {
        return request;
    }

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

    public Integer receiveQuantity() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = getGson().fromJson(json,Response.class);
        String body = responseObject.getBody();
        return Integer.parseInt(body);
    }

    public String receivePlainString() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = getGson().fromJson(json,Response.class);
        return responseObject.getBody();
    }

    public Post receiveWantedPost() throws IOException
    {
        String json = RequestPipeline.getDataInputStream().readUTF();
        Response responseObject = getGson().fromJson(json,Response.class);
        String body = responseObject.getBody();
        return getGson().fromJson(body,Post.class);
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