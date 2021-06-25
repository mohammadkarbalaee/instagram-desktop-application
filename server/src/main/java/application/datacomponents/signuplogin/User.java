package application.datacomponents.signuplogin;

public class User
{
    private String userName;
    private String email;
    private String password;
    private String bio = null;

    public User(String userName, String email, String password)
    {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }


    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setBio(String bio)
    {
        this.bio = bio;
    }

    public String getBio()
    {
        return bio;
    }
}