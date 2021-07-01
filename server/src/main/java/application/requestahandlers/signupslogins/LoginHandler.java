package application.requestahandlers.signupslogins;

import api.ApiHandler;
import api.Response;
import application.datacomponents.signuplogin.User;
import application.datamanagement.database.DatabaseManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.SQLException;

public class LoginHandler
{
    private ApiHandler apiHandler;
    private Gson gson = new Gson();

    public LoginHandler(ApiHandler apiHandler)
    {
        this.apiHandler = apiHandler;
    }

    public void deliverPassword(String username) throws SQLException, IOException
    {
        Response response = new Response(DatabaseManager.getPassword(username));
        apiHandler.answerToClient(response);
    }

    public void deliverBio(String username) throws IOException, SQLException
    {
        Response response = new Response(DatabaseManager.getBio(username));
        apiHandler.answerToClient(response);
    }

    public void setBio(String body) throws SQLException
    {
        User user = gson.fromJson(body, User.class);
        DatabaseManager.addBio(user);
    }
}
