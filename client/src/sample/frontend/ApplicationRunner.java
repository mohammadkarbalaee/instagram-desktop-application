package sample.frontend;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.api.RequestPipeline;

public class ApplicationRunner extends Application
{
    private static String loggedInUsername;
    private static Gson gson = new Gson();

    public static String getLoggedInUsername()
    {
        return loggedInUsername;
    }

    public static Gson getGson()
    {
        return gson;
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        RequestPipeline.build();
        Parent root = FXMLLoader.load(getClass().getResource("post/savepost.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
