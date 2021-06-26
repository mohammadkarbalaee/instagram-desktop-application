package sample.backend.application.post;

import javafx.scene.image.Image;

public class Post
{
    private String caption;
    private String owner;
    private Image image;
    private Integer likesQuantity;
    private Integer commentsQuantity;

    public Post(String owner, String caption)
    {
        this.caption = caption;
        this.owner = owner;
    }

    public Post(){}

    public Post(String caption, String owner, Image image, Integer likesQuantity, Integer commentsQuantity)
    {
        this.caption = caption;
        this.owner = owner;
        this.image = image;
        this.likesQuantity = likesQuantity;
        this.commentsQuantity = commentsQuantity;
    }

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

    public Image getImage()
    {
        return image;
    }

    public void setImage(Image image)
    {
        this.image = image;
    }

    public Integer getLikesQuantity()
    {
        return likesQuantity;
    }

    public void setLikesQuantity(Integer likesQuantity)
    {
        this.likesQuantity = likesQuantity;
    }

    public Integer getCommentsQuantity()
    {
        return commentsQuantity;
    }

    public void setCommentsQuantity(Integer commentsQuantity)
    {
        this.commentsQuantity = commentsQuantity;
    }
}
