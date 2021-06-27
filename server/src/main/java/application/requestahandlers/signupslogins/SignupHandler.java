package application.requestahandlers.signupslogins;

import api.ApiHandler;
import api.Response;
import application.datacomponents.signuplogin.User;
import application.datamanagement.database.DatabaseManager;
import application.datamanagement.file.PhotoManager;

import java.io.IOException;
import java.sql.SQLException;

public class SignupHandler
{
    private ApiHandler apiHandler;

    public SignupHandler(ApiHandler apiHandler)
    {
        this.apiHandler = apiHandler;
    }

    public void deliverIsNew(String username) throws IOException, SQLException
    {
        if (DatabaseManager.isUserNew(username))
        {
            Response isNew = new Response("true");
            apiHandler.answerToClient(isNew);
        }
        else
        {
            Response isNew = new Response("false");
            apiHandler.answerToClient(isNew);
        }
    }

    public void addUser(User newUser) throws SQLException
    {
        DatabaseManager.addUserRecord(newUser);
    }

    public void setProfilePic(String username) throws IOException, SQLException
    {
        DatabaseManager.setProfilePic(username,PhotoManager.saveProfilePhoto(username,apiHandler.getImage()));
    }

    public void deliverIsPicSet(String username) throws SQLException, IOException
    {
        Response response = new Response(DatabaseManager.getIsProfileSet(username));
        apiHandler.answerToClient(response);
    }

    public void deliverProfilePic(String username) throws IOException, SQLException
    {
        apiHandler.sendPhoto(PhotoManager.getPhoto(DatabaseManager.getProfilePicSavedPath(username)));
    }
}