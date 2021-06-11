package application.datamanagement;

import application.datamanagement.database.DatabaseManager;
import application.datamanagement.file.ProfilePhotoSaver;
import application.util.User;

import java.io.IOException;
import java.sql.SQLException;

public class DataManager
{
    synchronized public static void saveUser(User user) throws SQLException, IOException
    {
        DatabaseManager.addUserRecord(user);
    }
}
