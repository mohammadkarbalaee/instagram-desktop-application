package sample.backend.application.post;

import javafx.scene.image.Image;

import java.time.LocalDateTime;
/**
 * @author Muhammad Karbalaee Shabani
 * a class, representing a Post object.
 * This class implements Comparable interface,so that being sorted with Collections.sort()
 */
public class Post implements Comparable<Post>
{
    private String postID;
    private String caption;
    private String owner;
    private Image image;
    private Integer likesQuantity;
    private Integer commentsQuantity;
    private LocalDateTime dateTime;

    public Post(String owner, String caption,LocalDateTime dateTime)
    {
        this.caption = caption;
        this.owner = owner;
        this.dateTime = dateTime;
    }

    public Post(){}

    public Post(String postID,String caption, String owner, Image image, Integer likesQuantity, Integer commentsQuantity,LocalDateTime dateTime)
    {
        this.postID = postID;
        this.caption = caption;
        this.owner = owner;
        this.image = image;
        this.likesQuantity = likesQuantity;
        this.commentsQuantity = commentsQuantity;
        this.dateTime = dateTime;
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

    public LocalDateTime getDateTime()
    {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime)
    {
        this.dateTime = dateTime;
    }

    /**
     * this method is overrided from Comparable interface. to give the instances of this
     * class comparable to one another.
     * @param secondPost
     * @return either 1 -1 or 0 as a comparison number
     */
    @Override
    public int compareTo(Post secondPost)
    {
        if(this.dateTime.isAfter(secondPost.dateTime))
        {
            return -1;
        }
        else if(this.dateTime.isBefore(secondPost.dateTime))
        {
            return +1;
        }
        return 0;
    }

    public String getPostID()
    {
        return postID;
    }

    public void setPostID(String postID)
    {
        this.postID = postID;
    }
}
