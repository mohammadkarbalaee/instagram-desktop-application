package sample.frontend.post.comment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * @author Reyhaneh Saffar
 * is the runner class for stages in comment section
 */
public class CommentMain extends Application
{
    private double x = 0;
    private double y = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        setUp("comment.fxml");
    }

    public void setUp(String path) throws IOException {

        Stage stage = new Stage();

        Parent root = FXMLLoader.load(getClass().getResource(path));
        root.setOnMousePressed(event ->
        {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event ->
        {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}