package sample.backend.application.followerfollowing;
/**
 * @author Muhammad Karbalaee Shabani
 * a class, representing a FollowerFollowingPack object
 */
public class FollowerFollowingPack
{
    private String username;
    private String follower;
    private boolean isForUnfollow;

    public FollowerFollowingPack(String username, String follower, boolean isForUnfollow)
    {
        this.username = username;
        this.follower = follower;
        this.isForUnfollow = isForUnfollow;
    }

    public String getUsername()
    {
        return username;
    }

    public String getFollower()
    {
        return follower;
    }

    public boolean isForUnfollow()
    {
        return isForUnfollow;
    }
}