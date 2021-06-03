import application.Database.DatabaseManager;
import application.User;

import java.sql.SQLException;

public class DatabaseTester
{
    public static void main(String[] args) throws SQLException
    {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.addUserRecord(new User("muhammad.ksht","muhammad.ksht@gmail.com","123456"));
    }
}
