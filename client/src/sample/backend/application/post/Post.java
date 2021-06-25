package sample.backend.application.post;

public class Post
{
    private String caption;
    private String owner;

    public Post(String owner, String caption)
    {
        this.caption = caption;
        this.owner = owner;
    }

    public Post(){}

    public String getCaption()
    {
        return caption;
    }

    public void setCaption(String caption)
    {
        this.caption = caption;
    }

    public String getOwner()
    {
        return owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }
}
