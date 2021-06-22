package sample.frontend;

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
//        RequestPipeline.build();
        Parent root = FXMLLoader.load(getClass().getResource("post/savepost.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
