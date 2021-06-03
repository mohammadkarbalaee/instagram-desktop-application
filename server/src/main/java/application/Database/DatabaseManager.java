package application.Database;

import application.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager
{
    private final String URL_CONNECTION = "jdbc:mysql://localhost:3306/jdbc";
    private final Connection CONNECTION = DriverManager.getConnection(URL_CONNECTION,"root","reyhan1881");

    public DatabaseManager() throws SQLException{}

    public void addUserRecord(User user) throws SQLException
    {
        String insertQuery = "INSERT INTO users(username,password,email,profile_photo,bio)" +
                " VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(insertQuery);

        preparedStatement.setString(1, user.getUserName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, System.getProperty("user.in") + user.getUserName());
        if (user.getBio() != null)
        {
            preparedStatement.setString(5,user.getBio());
        }
        else
        {
            preparedStatement.setString(5,"");
        }
    }
}
