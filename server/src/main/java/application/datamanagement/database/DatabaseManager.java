package application.datamanagement.database;

import application.util.User;
import application.util.directmessage.ChatRoom;
import application.util.directmessage.Message;
import application.util.followerfollowing.FollowerFollowingPack;
import application.util.post.Post;
import application.util.search.SearchResult;
import com.google.gson.Gson;

import java.sql.*;


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

    synchronized public static boolean checkExistence(String usernameToCheck) throws SQLException
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

    synchronized public static SearchResult getSearchResult(String usernameToManipulate) throws SQLException
    {
        if (checkExistence(usernameToManipulate))
        {
            SearchResult searchResult = new SearchResult();

            String getFollowersQuery = "SELECT * FROM followers " +
                    "WHERE username = ?";
            PreparedStatement statement1 = CONNECTION.prepareStatement(getFollowersQuery);
            statement1.setString(1,usernameToManipulate);
            ResultSet followersSet = statement1.executeQuery();
            while (followersSet.next())
            {
                searchResult.addFollowers(followersSet.getString("follower"));
            }
            statement1.close();

            String getFollowingsQuery = "SELECT * FROM followings " +
                    "WHERE username = ?";
            PreparedStatement statement2 = CONNECTION.prepareStatement(getFollowingsQuery);
            statement2.setString(1,usernameToManipulate);
            ResultSet followingsSet = statement2.executeQuery();
            while (followingsSet.next())
            {
                searchResult.addFollowings(followingsSet.getString("following"));
            }
            statement2.close();

            String getUser = "SELECT * FROM users " +
                    "WHERE username = ?";
            PreparedStatement statement3 = CONNECTION.prepareStatement(getUser);
            statement3.setString(1,usernameToManipulate);
            ResultSet userSet = statement3.executeQuery();
            searchResult.setUsername(usernameToManipulate);
            while (userSet.next())
            {
                searchResult.setEmail(userSet.getString("email"));
                searchResult.setBio(userSet.getString("bio"));
            }
            statement3.close();

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
        String savePostQuery = "INSERT INTO posts(owner,caption,image_path,nth)" +
                " VALUES (?, ?, ?,?)";
        PreparedStatement statement = CONNECTION.prepareStatement(savePostQuery);
        statement.setString(1,post.getOwner());
        statement.setString(2,post.getCaption());
        statement.setString(3,saveAddress);
        statement.setInt(4,nth);
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
                "WHERE owner = ? & nth = ?";
        PreparedStatement statement = CONNECTION.prepareStatement(getPostQuery);
        statement.setString(1,owner);
        statement.setInt(2,Integer.parseInt(nth));
        ResultSet postSet = statement.executeQuery();
        while (postSet.next())
        {
            wantedPost.setOwner(owner);
            wantedPost.setCaption(postSet.getString("caption"));
        }
        statement.close();
        return wantedPost;
    }

    synchronized public static String getPostSavedAddress(String owner,String nth) throws SQLException
    {
        String address = null;
        String getPostQuery = "SELECT * FROM posts " +
                "WHERE owner = ? & nth = ?";
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
        ResultSet resultSet = statement.executeQuery(getMessagesQuery);
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

}
