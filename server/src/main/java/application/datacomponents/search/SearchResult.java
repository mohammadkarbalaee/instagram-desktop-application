package application.datacomponents.search;

import javafx.scene.image.Image;

public class SearchResult
{
    private String username;
    private Image profileImage;

    public SearchResult(String username, Image profileImage)
    {
        this.username = username;
        this.profileImage = profileImage;
    }

    public SearchResult()
    {

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
