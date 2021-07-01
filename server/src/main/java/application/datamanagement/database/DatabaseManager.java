package application.datamanagement.database;

import application.datacomponents.comment.Comment;
import application.datacomponents.like.Like;
import application.datacomponents.signuplogin.User;
import application.datacomponents.directmessage.ChatRoom;
import application.datacomponents.directmessage.Message;
import application.datacomponents.followerfollowing.FollowerFollowingPack;
import application.datacomponents.post.Post;
import application.datacomponents.search.SearchResult;
import com.google.gson.Gson;

import java.sql.*;
import java.time.LocalDateTime;


public class DatabaseManager
{
    private static final String URL_CONNECTION = "jdbc:mysql://localhost:3306/jdbc?user=root";
    private static Connection CONNECTION;
    private static final Gson gson = new Gson();

    static
    {
        try
        {
            CONNECTION = DriverManager.getConnection(URL_CONNECTION);
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    synchronized public static void addUserRecord(User user) throws SQLException
    {
        String insertQuery = "INSERT INTO users(username,password,email,bio)" +
                " VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(insertQuery);

        preparedStatement.setString(1, user.getUserName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getEmail());
        if (user.getBio() != null)
        {
            preparedStatement.setString(4,user.getBio());
        }
        else
        {
            preparedStatement.setString(4,"");
        }

        preparedStatement.execute();

        preparedStatement.close();
    }

    synchronized public static boolean isUserNew(String usernameToCheck) throws SQLException
    {
        String searchQuery = "SELECT username FROM users " +
                "WHERE username = ?";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(searchQuery);
        preparedStatement.setString(1,usernameToCheck);
        ResultSet resultSet = preparedStatement.executeQuery();
        boolean result;
        if (!resultSet.next())
        {
            result = false;
        }
        else
        {
            result = true;
        }

        preparedStatement.close();

        return result;
    }

    synchronized public static void setFollowerFollowing(FollowerFollowingPack pack) throws SQLException
    {
        String query1;
        String query2;
        PreparedStatement statement1;
        PreparedStatement statement2;

        if (pack.isForUnfollow())
        {
            query1 = "DELETE FROM followers " +
                    "WHERE username = ?";
            query2 = "DELETE FROM followings " +
                    "WHERE username = ?";

            statement1 = CONNECTION.prepareStatement(query1);
            statement2 = CONNECTION.prepareStatement(query2);

            statement1.setString(1,pack.getUsername());
            statement2.setString(1,pack.getFollower());
        }
        else
        {
            query1 = "INSERT INTO followers(username,follower)" +
                    " VALUES (?, ?)";
            query2 = "INSERT INTO followings(username,following)" +
                    " VALUES (?, ?)";

            statement1 = CONNECTION.prepareStatement(query1);
            statement2 = CONNECTION.prepareStatement(query2);

            statement1.setString(1,pack.getUsername());
            statement1.setString(2,pack.getFollower());
            statement2.setString(1,pack.getFollower());
            statement2.setString(2,pack.getUsername());
        }

        statement1.execute();
        statement2.execute();

        statement1.close();
        statement2.close();
    }

    synchronized public static SearchResult getSearchResult(String username) throws SQLException
    {
        if (isUserNew(username))
        {
            SearchResult searchResult = new SearchResult();
            searchResult.setUsername(username);
            return searchResult;
        }
        else
        {
            return null;
        }
    }

    synchronized public static Integer getFollowersQuantity(String username) throws SQLException
    {
        int quantity = 0;
        String getFollowersQuery = "SELECT * FROM followers " +
                "WHERE username = ?";
        PreparedStatement statement = CONNECTION.prepareStatement(getFollowersQuery);
        statement.setString(1,username);
        ResultSet followersSet = statement.executeQuery();
        while (followersSet.next())
        {
            quantity++;
        }
        statement.close();
        return quantity;
    }

    synchronized public static Integer getFollowingsQuantity(String username) throws SQLException
    {
        int quantity = 0;
        String getFollowingsQuery = "SELECT * FROM followings " +
                "WHERE username = ?";
        PreparedStatement statement = CONNECTION.prepareStatement(getFollowingsQuery);
        statement.setString(1,username);
        ResultSet followingsSet = statement.executeQuery();
        while (followingsSet.next())
        {
            quantity++;
        }
        statement.close();
        return quantity;
    }

    synchronized public static String getPassword(String username) throws SQLException
    {
        String getPasswordQuery = "SELECT password FROM users " +
                "WHERE username = ?";
        PreparedStatement statement = CONNECTION.prepareStatement(getPasswordQuery);
        statement.setString(1,username);
        ResultSet passwordSet = statement.executeQuery();
        String password = null;
        while (passwordSet.next())
        {
            password = passwordSet.getString("password");
        }
        statement.close();
        return password;
    }

    synchronized public static void savePost(Post post,String saveAddress) throws SQLException
    {
        Integer nth = getPostQuantity(post.getOwner()) + 1;
        String savePostQuery = "INSERT INTO posts(owner,caption,image_path,nth,date)" +
                " VALUES (?, ?, ?,?,?)";
        PreparedStatement statement = CONNECTION.prepareStatement(savePostQuery);
        statement.setString(1,post.getOwner());
        statement.setString(2,post.getCaption());
        statement.setString(3,saveAddress);
        statement.setInt(4,nth);
        statement.setString(5, String.valueOf(post.getDateTime()));
        statement.execute();
        statement.close();
    }

    synchronized public static Integer getPostQuantity(String owner) throws SQLException
    {
        int quantity = 0;
        String getPostsQuery = "SELECT owner FROM posts " +
                "WHERE owner = ?";
        PreparedStatement statement = CONNECTION.prepareStatement(getPostsQuery);
        statement.setString(1,owner);
        ResultSet postsSet = statement.executeQuery();
        while (postsSet.next())
        {
            quantity++;
        }
        statement.close();
        return quantity;
    }

    synchronized public static Post getPost(String owner,String nth) throws SQLException
    {
        Post wantedPost = new Post();
        String getPostQuery = "SELECT * FROM posts " +
                "WHERE owner = ? AND nth = ?";
        PreparedStatement statement = CONNECTION.prepareStatement(getPostQuery);
        statement.setString(1,owner);
        statement.setInt(2,Integer.parseInt(nth));
        ResultSet postSet = statement.executeQuery();
        while (postSet.next())
        {
            wantedPost.setOwner(owner);
            wantedPost.setCaption(postSet.getString("caption"));
            wantedPost.setDateTime(LocalDateTime.parse(postSet.getString("date")));
        }
        statement.close();
        return wantedPost;
    }

    synchronized public static String getPostSavedAddress(String owner,String nth) throws SQLException
    {
        String address = null;
        String getPostQuery = "SELECT * FROM posts " +
                "WHERE owner = ? AND nth = ?";
        PreparedStatement statement = CONNECTION.prepareStatement(getPostQuery);
        statement.setString(1,owner);
        statement.setInt(2,Integer.parseInt(nth));
        ResultSet postSet = statement.executeQuery();
        while (postSet.next())
        {
            address = postSet.getString("image_path");
        }
        statement.close();
        return address;
    }

    synchronized public static boolean checkChatroomTableExistence(ChatRoom chatRoom)
    {
        String checkQuery = "SELECT * FROM " + chatRoom.getChatroomTableName();
        try
        {
            PreparedStatement statement = CONNECTION.prepareStatement(checkQuery);
            statement.execute();
            statement.close();
            return true;
        }
        catch (SQLException throwables)
        {
            return false;
        }
    }

    synchronized public static void createChatroomTable(ChatRoom chatRoom) throws SQLException
    {
        String createTableQuery = "create table if not exists jdbc." + chatRoom.getChatroomTableName() + "\n(\n" +
                "  message_id_number int not null AUTO_INCREMENT,\n" +
                "  sender varchar(50) not null,\n" +
                "  receiver varchar(50) not null,\n" +
                "  message varchar(10000) not null,\n" +
                "  primary key (message_id_number)\n" +
                ")";
        Statement statement = CONNECTION.createStatement();
        statement.execute(createTableQuery);
        statement.close();
    }

    synchronized public static void addMessageToChatroom(ChatRoom chatRoom, Message message) throws SQLException
    {
        String insertQuery = "INSERT INTO " + chatRoom.getChatroomTableName() +
                "(sender,receiver,message)" +
                " VALUES (?, ?, ?)";
        PreparedStatement statement = CONNECTION.prepareStatement(insertQuery);
        statement.setString(1,message.getSender());
        statement.setString(2,message.getReceiver());
        statement.setString(3,message.getText());
        statement.execute();
        statement.close();
    }

    synchronized public static String getMessages(ChatRoom chatRoom) throws SQLException
    {
        String getMessagesQuery = "SELECT * FROM " + chatRoom.getChatroomTableName();
        Statement statement = CONNECTION.createStatement();
        ResultSet resultSet = null;
        try
        {
            resultSet = statement.executeQuery(getMessagesQuery);
        }
        catch (SQLException throwables)
        {
            return null;
        }
        String messagesJson = "";
        Message message;
        String sender;
        String receiver;
        String text;

        while (resultSet.next())
        {
            sender = resultSet.getString("sender");
            receiver = resultSet.getString("receiver");
            text = resultSet.getString("message");
            message = new Message(sender,receiver,text);
            messagesJson += gson.toJson(message) + "/";
        }
        statement.close();
        return messagesJson;
    }

    synchronized public static void addComment(Comment newComment) throws SQLException
    {
        String insertQuery = "INSERT INTO comments" +
                "(text,author,post)" +
                " VALUES (?, ?, ?)";
        PreparedStatement statement = CONNECTION.prepareStatement(insertQuery);
        statement.setString(1,newComment.getText());
        statement.setString(2,newComment.getAuthor());
        statement.setString(3,newComment.getPost());
        statement.execute();
        statement.close();
    }

    synchronized public static Integer getCommentsQuanity(String post) throws SQLException
    {
        int quantity = 0;
        String query = "SELECT post FROM comments " +
                "WHERE post = ?";
        PreparedStatement statement = CONNECTION.prepareStatement(query);
        statement.setString(1,post);
        ResultSet postsSet = statement.executeQuery();
        while (postsSet.next())
        {
            quantity++;
        }
        statement.close();
        return quantity;
    }

    synchronized public static String getComments(String post) throws SQLException
    {
        String query = "SELECT * FROM comments WHERE post = ?";
        PreparedStatement statement = CONNECTION.prepareStatement(query);
        statement.setString(1,post);
        ResultSet resultSet = statement.executeQuery();
        String commentsJson = "";
        Comment comment;
        String text;
        String author;

        while (resultSet.next())
        {
            author = resultSet.getString("author");
            text = resultSet.getString("text");
            comment = new Comment(text,author,post);
            commentsJson += gson.toJson(comment) + "@";
        }
        statement.close();
        return commentsJson;
    }

    synchronized public static void addLike(Like like) throws SQLException
    {
        String insertQuery = "INSERT INTO likes" +
                "(liker,post)" +
                " VALUES (?, ?)";
        PreparedStatement statement = CONNECTION.prepareStatement(insertQuery);
        statement.setString(1,like.getLiker());
        statement.setString(2,like.getPost());
        statement.execute();
        statement.close();
    }

    synchronized public static Integer getLikesQuantity(String post) throws SQLException
    {
        int quantity = 0;
        String query = "SELECT post FROM likes " +
                "WHERE post = ?";
        PreparedStatement statement = CONNECTION.prepareStatement(query);
        statement.setString(1,post);
        ResultSet postsSet = statement.executeQuery();
        while (postsSet.next())
        {
            quantity++;
        }
        statement.close();
        return quantity;
    }

    synchronized public static String getLikes(String post) throws SQLException
    {
        String query = "SELECT * FROM likes WHERE post = ?";
        PreparedStatement statement = CONNECTION.prepareStatement(query);
        statement.setString(1,post);
        ResultSet resultSet = statement.executeQuery();
        String likesJson = "";
        Like like;
        String liker;

        while (resultSet.next())
        {
            liker = resultSet.getString("liker");
            like = new Like(liker,post);
            likesJson += gson.toJson(like) + "@";
        }
        statement.close();
        return likesJson;
    }

    synchronized public static boolean isLikeNew(Like like) throws SQLException
    {
        String searchQuery = "SELECT * FROM likes " +
                "WHERE liker = ? AND post = ?";
        PreparedStatement statement = CONNECTION.prepareStatement(searchQuery);
        statement.setString(1,like.getLiker());
        statement.setString(2,like.getPost());
        ResultSet resultSet = statement.executeQuery();
        boolean result;
        if (!resultSet.next())
        {
            result = false;
        }
        else
        {
            result = true;
        }

        statement.close();

        return result;
    }

    synchronized public static void addDislike(Like like) throws SQLException
    {
        String query = "DELETE FROM likes " +
                "WHERE liker = ? AND post = ?";
        PreparedStatement statement = CONNECTION.prepareStatement(query);
        statement.setString(1,like.getLiker());
        statement.setString(2,like.getPost());
        statement.execute();
        statement.close();
    }

    synchronized public static String getFollowings(String username) throws SQLException
    {
        String followings = "";
        String query = "SELECT following FROM followings WHERE username = ?";
        PreparedStatement statement = CONNECTION.prepareStatement(query);
        statement.setString(1,username);
        ResultSet followingsSet = statement.executeQuery();
        while (followingsSet.next())
        {
            followings += followingsSet.getString("following") + "/";
        }
        statement.close();
        return followings;
    }

    synchronized public static void setProfilePic(String username,String savePath) throws SQLException
    {
        String insertQuery = "UPDATE users SET profile_pic_path = ? WHERE username = ?";
        PreparedStatement statement = CONNECTION.prepareStatement(insertQuery);
        statement.setString(1,savePath);
        statement.setString(2,username);
        statement.execute();
        statement.close();
    }

    synchronized public static String getIsProfileSet(String username) throws SQLException
    {
        String query = "SELECT profile_pic_path FROM users WHERE username = ?";
        PreparedStatement statement = CONNECTION.prepareStatement(query);
        statement.setString(1,username);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next())
        {
            if (resultSet.getString("profile_pic_path") == null)
            {
                return "false";
            }
            else
            {
                return "true";
            }
        }
        statement.close();
        return "true";
    }

    synchronized public static String getProfilePicSavedPath(String username) throws SQLException
    {
        String path = null;
        String query = "SELECT profile_pic_path FROM users " +
                "WHERE username = ?";
        PreparedStatement statement = CONNECTION.prepareStatement(query);
        statement.setString(1,username);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next())
        {
            path = resultSet.getString("profile_pic_path");
        }
        statement.close();
        return path;
    }

    synchronized public static String getFollowers(String username) throws SQLException
    {
        String followers = "";
        String query = "SELECT follower FROM followers WHERE username = ?";
        PreparedStatement statement = CONNECTION.prepareStatement(query);
        statement.setString(1,username);
        ResultSet followersSet = statement.executeQuery();
        while (followersSet.next())
        {
            followers += followersSet.getString("follower") + "/";
        }
        statement.close();
        return followers;
    }

    synchronized public static String getBio(String username) throws SQLException
    {
        String query = "SELECT bio FROM users " +
                "WHERE username = ?";
        PreparedStatement statement = CONNECTION.prepareStatement(query);
        statement.setString(1,username);
        ResultSet resultSet = statement.executeQuery();
        String bio = null;
        while (resultSet.next())
        {
            bio = resultSet.getString("bio");
        }
        statement.close();
        return bio;
    }

    synchronized public static String isFollowed(String username, String follower) throws SQLException
    {
        String query = "SELECT * FROM followers WHERE username = ?";
        PreparedStatement statement = CONNECTION.prepareStatement(query);
        statement.setString(1,username);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next())
        {
            if (resultSet.getString("follower").equals(follower))
            {
                return "true";
            }
        }
        statement.close();
        return "false";
    }

    public static String getIsLiked(String post, String liker) throws SQLException
    {
        String query = "SELECT * FROM likes WHERE liker = ?";
        PreparedStatement statement = CONNECTION.prepareStatement(query);
        statement.setString(1,liker);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next())
        {
            if (resultSet.getString("post").equals(post))
            {
                return "true";
            }
        }
        statement.close();
        return "false";
    }

    synchronized public static void addBio(User user) throws SQLException
    {
        String insertQuery = "UPDATE users SET bio = ? WHERE username = ?";
        PreparedStatement statement = CONNECTION.prepareStatement(insertQuery);
        statement.setString(1,user.getBio());
        statement.setString(2,user.getUserName());
        statement.execute();
        statement.close();
    }
}