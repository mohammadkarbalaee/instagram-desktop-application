package model;

public class Post
{
    private String PostImageSrc;
    private String profileImageSrc;
    private String username;
    private String date;
    private String numLikes;
    private String numComments;

    public String getPostImageSrc()
    {
        return PostImageSrc;
    }

    public void setPostImageSrc(String postImageSrc)
    {
        PostImageSrc = postImageSrc;
    }

    public String getProfileImageSrc()
    {
        return profileImageSrc;
    }

    public void setProfileImageSrc(String profileImageSrc)
    {
        this.profileImageSrc = profileImageSrc;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getNumLikes()
    {
        return numLikes;
    }

    public void setNumLikes(String numLikes)
    {
        this.numLikes = numLikes;
    }

    public String getNumComments()
    {
        return numComments;
    }

    public void setNumComments(String numComments)
    {
        this.numComments = numComments;
    }
}
