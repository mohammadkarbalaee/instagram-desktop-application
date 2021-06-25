package application.requestahandlers.searches;

import api.ApiHandler;
import api.Response;
import application.datacomponents.search.SearchResult;
import application.datamanagement.database.DatabaseManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.SQLException;

public class SearchHandler
{
    private static ApiHandler apiHandler;
    private static Gson gson = new Gson();

    public static void build(ApiHandler newApiHandler)
    {
        apiHandler = newApiHandler;
    }

    public static void deliverSearchReuslt(String usenanme) throws IOException, SQLException
    {
        SearchResult searchResult = DatabaseManager.getSearchResult(usenanme);
        if (searchResult == null)
        {
            Response response = new Response("null");
            apiHandler.answerToClient(response);
        }
        else
        {
            Response response = new Response(gson.toJson(searchResult));
            apiHandler.answerToClient(response);
        }
    }
}
