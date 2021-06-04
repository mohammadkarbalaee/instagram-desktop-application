package application.DataManagement.Database;

import application.DataManagement.DataManager;
import application.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager
{
    private final String URL_CONNECTION = "jdbc:mysql://localhost:3306/jdbc?user=root";
    private final Connection CONNECTION = DriverManager.getConnection(URL_CONNECTION);

    public DatabaseManager() throws SQLException{}

    public void addUserRecord(User user) throws SQLException
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
    }
}
