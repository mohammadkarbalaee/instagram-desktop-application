package sample.frontend.mainmenu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.backend.api.RequestPipeline;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        RequestPipeline.build();

        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Instagram");
        primaryStage.setScene(new Scene(root,1510, 850));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
