package application.datacomponents.search;

import java.util.ArrayList;

public class SearchResult
{
    private ArrayList<String> followers = new ArrayList<>();
    private ArrayList<String> followings = new ArrayList<>();
    private String bio;
    private String username;
    private String email;

    public ArrayList<String> getFollowers()
    {
        return followers;
    }

    public void addFollowers(String follower)
    {
        followers.add(follower);
    }

    public ArrayList<String> getFollowings()
    {
        return followings;
    }

    public void addFollowings(String following)
    {
        followings.add(following);
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