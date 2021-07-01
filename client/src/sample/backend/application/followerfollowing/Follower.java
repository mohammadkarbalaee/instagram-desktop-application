package sample.backend.application.followerfollowing;

import javafx.scene.image.Image;
/**
 * @author Muhammad Karbalaee Shabani
 * a class, representing a Follower object
 */
public class Follower
{
    private String username;
    private Image profilePic;

    public Follower(String username, Image profilePic)
    {
        this.username = username;
        this.profilePic = profilePic;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public Image getProfilePic()
    {
        return profilePic;
    }

    public void setProfilePic(Image profilePic)
    {
        this.profilePic = profilePic;
    }
}
