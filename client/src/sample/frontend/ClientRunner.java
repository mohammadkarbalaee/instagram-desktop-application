package sample.frontend;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.backend.api.ApiHandler;
import sample.backend.api.RequestPipeline;

public class ClientRunner extends Application
{
    private static String loggedInUsername;
    private static String searchedUsername;
    private static String inChatUsername;
    private static Gson gson = new Gson();
    private static ApiHandler apiHandler = new ApiHandler();

    public static ApiHandler getApiHandler()
    {
        return apiHandler;
    }

    public static void setApiHandler(ApiHandler apiHandler)
    {
        ClientRunner.apiHandler = apiHandler;
    }

    public static String getLoggedInUsername()
    {
        return loggedInUsername;
    }

    public static Gson getGson()
    {
        return gson;
    }

    public static void setLoggedInUsername(String loggedInUsername)
    {
        ClientRunner.loggedInUsername = loggedInUsername;
    }

    public static String getSearchedUsername()
    {
        return searchedUsername;
    }

    public static void setSearchedUsername(String searchedUsername)
    {
        ClientRunner.searchedUsername = searchedUsername;
    }

    public static void setInChatUsername(String inChatUsername)
    {
        ClientRunner.inChatUsername = inChatUsername;
    }

    public static void setGson(Gson gson)
    {
        ClientRunner.gson = gson;
    }

    public static String getInChatUsername()
    {
        return inChatUsername;
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        RequestPipeline.build();
        Parent root = FXMLLoader.load(getClass().getResource("register/signup.fxml"));
        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
