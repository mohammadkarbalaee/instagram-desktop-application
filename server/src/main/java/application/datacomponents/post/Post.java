package application.datacomponents.post;

import java.time.LocalDateTime;

public class Post
{
    private String caption;
    private String owner;
    private LocalDateTime dateTime;

    public Post(String owner, String caption,LocalDateTime dateTime)
    {
        this.caption = caption;
        this.owner = owner;
        this.dateTime = dateTime;
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

    public LocalDateTime getDateTime()
    {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime)
    {
        this.dateTime = dateTime;
    }
}

