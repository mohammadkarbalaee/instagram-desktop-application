package sample.backend.application.comment;
/**
 * @author Muhammad Karbalaee Shabani
 * a class, representing a Comment object
 */
public class Comment
{
    private String text;
    private String author;
    private String post;

    public Comment(String text, String author, String post)
    {
        this.text = text;
        this.author = author;
        this.post = post;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getPost()
    {
        return post;
    }

    public void setPost(String post)
    {
        this.post = post;
    }
}