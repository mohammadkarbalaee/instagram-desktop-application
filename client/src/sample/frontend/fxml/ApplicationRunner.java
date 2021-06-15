package sample.frontend.fxml;

import sample.api.RequestPipeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ApplicationRunner extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        RequestPipeline.build();
        Parent root = FXMLLoader.load(getClass().getResource("signup.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 520, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
