package sample.frontend;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.backend.api.RequestPipeline;

public class ApplicationRunner extends Application
{
    private static String loggedInUsername;
    private static String searchedUsername;
    private static Gson gson = new Gson();

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
        ApplicationRunner.loggedInUsername = loggedInUsername;
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        RequestPipeline.build();
        Parent root = FXMLLoader.load(getClass().getResource("post/savepost.fxml"));
        String css = ApplicationRunner.class.getResource("post/css/buttonStyle.css").toExternalForm();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
