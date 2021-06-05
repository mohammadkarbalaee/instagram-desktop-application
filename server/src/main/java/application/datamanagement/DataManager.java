package application.datamanagement;

import application.datamanagement.database.DatabaseManager;
import application.datamanagement.file.ProfilePhotoSaver;
import application.util.User;

import java.io.IOException;
import java.sql.SQLException;

public class DataManager
{
    DatabaseManager databaseManager = new DatabaseManager();
    ProfilePhotoSaver profilePhotoSaver = new ProfilePhotoSaver();

    public DataManager() throws SQLException {}

    public void saveUser(User user) throws SQLException, IOException
    {
        databaseManager.addUserRecord(user);
    }
}
