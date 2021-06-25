package application.requestahandlers.signupslogins;

import api.ApiHandler;
import api.Response;
import application.datamanagement.database.DatabaseManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.SQLException;

public class LoginHandler
{
    private static ApiHandler apiHandler;

    public static void build(ApiHandler newApiHandler)
    {
        apiHandler = newApiHandler;
    }

    public static void deliverPassword(String username) throws SQLException, IOException
    {
        Response response = new Response(DatabaseManager.getPassword(username));
        apiHandler.answerToClient(response);
    }
}
