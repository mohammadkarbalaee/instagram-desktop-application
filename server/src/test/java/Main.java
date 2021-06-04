import api.ApiHandler;
import api.Request;
import api.RequestPipeline;
import api.Response;
import application.datamanagement.database.DatabaseManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;

public class Main
{
    public static void main(String[] args) throws IOException, SQLException
    {
        ServerSocket serverSocket = new ServerSocket(8080);
        RequestPipeline requestPipeline = new RequestPipeline(serverSocket);
        ApiHandler handler = new ApiHandler(requestPipeline);
        DatabaseManager databaseManager = new DatabaseManager();
        Request request = handler.listen();
        if (request.getLabel().equals("IS_NEW"))
        {
            System.out.println("i am in if");
            if (!databaseManager.isNew(request.getUsername()))
            {
                ApiHandler apiHandler = new ApiHandler(requestPipeline);
                Response itsNew = new Response("true");
                apiHandler.sendResponse(itsNew);
            }
        }
    }
}