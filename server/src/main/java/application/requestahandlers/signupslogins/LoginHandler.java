package application.requestahandlers.signupslogins;

import api.ApiHandler;
import api.Response;
import application.datamanagement.database.DatabaseManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.SQLException;

public class LoginHandler
{
    private ApiHandler apiHandler;

    public LoginHandler(ApiHandler apiHandler)
    {
        this.apiHandler = apiHandler;
    }

    public void deliverPassword(String username) throws SQLException, IOException
    {
        Response response = new Response(DatabaseManager.getPassword(username));
        apiHandler.answerToClient(response);
    }
}
