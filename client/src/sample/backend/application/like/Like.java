package sample.backend.application.like;
/**
 * @author Muhammad Karbalaee Shabani
 * a class, representing a Like object
 */
public class Like
{
    private String liker;
    private String post;

    public Like(String liker, String post)
    {
        this.liker = liker;
        this.post = post;
    }

    public String getPost()
    {
        return post;
    }

    public void setPost(String post)
    {
        this.post = post;
    }

    public String getLiker()
    {
        return liker;
    }

    public void setLiker(String liker)
    {
        this.liker = liker;
    }
}
