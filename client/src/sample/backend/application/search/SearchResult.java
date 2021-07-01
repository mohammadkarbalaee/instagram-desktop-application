package sample.backend.application.search;

import javafx.scene.image.Image;
/**
 * @author Muhammad Karbalaee Shabani
 * a class, representing a SearchResult object
 */
public class SearchResult
{
    private String username;
    private Image profileImage;

    public SearchResult(String username, Image profileImage)
    {
        this.username = username;
        this.profileImage = profileImage;
    }

    public Image getProfileImage()
    {
        return profileImage;
    }

    public void setProfileImage(Image profileImage)
    {
        this.profileImage = profileImage;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
}
