package application.DataManagement;

import application.DataManagement.Database.DatabaseManager;
import application.DataManagement.File.ProfilePhotoSaver;
import application.User;

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
        if (user.getProfilePhoto() != null)
        {
            profilePhotoSaver.writeProfilePhoto(user.getUserName(),user.getProfilePhoto());
        }
    }
}
