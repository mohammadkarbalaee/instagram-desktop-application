package application.requestahandlers.signupslogins;

import api.ApiHandler;
import api.Response;
import application.datacomponents.signuplogin.User;
import application.datamanagement.database.DatabaseManager;

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
}
