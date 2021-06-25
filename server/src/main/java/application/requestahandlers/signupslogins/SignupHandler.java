package application.requestahandlers.signupslogins;

import api.ApiHandler;
import api.Response;
import application.datacomponents.signuplogin.User;
import application.datamanagement.database.DatabaseManager;

import java.io.IOException;
import java.sql.SQLException;

public class SignupHandler
{
    private static ApiHandler apiHandler;

    public static void build(ApiHandler newApiHandler)
    {
        apiHandler = newApiHandler;
    }

    public static void deliverIsNew(String username) throws IOException, SQLException
    {
        if (!DatabaseManager.checkExistence(username))
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

    public static void addUser(User newUser) throws SQLException
    {
        DatabaseManager.addUserRecord(newUser);
    }
}
