package sample.backend.application.search;

import java.util.ArrayList;

public class SearchResult
{
    private ArrayList<String> followers;
    private ArrayList<String> followings;
    private String bio;
    private String username;
    private String email;

    public ArrayList<String> getFollowers()
    {
        return followers;
    }

    public void setFollowers(ArrayList<String> followers)
    {
        this.followers = followers;
    }

    public ArrayList<String> getFollowings()
    {
        return followings;
    }

    public void setFollowings(ArrayList<String> followings)
    {
        this.followings = followings;
    }

    public String getBio()
    {
        return bio;
    }

    public void setBio(String bio)
    {
        this.bio = bio;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
