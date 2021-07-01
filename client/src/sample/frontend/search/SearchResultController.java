package sample.frontend.search;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class SearchResultController
{
    @FXML
    public Button showprofileButton;

    public void onClick() throws IOException
    {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("userProfile/userProfile.fxml")));
        Scene scene = new Scene(root,1290,900);
        Stage notFoundStage = new Stage();
        notFoundStage.initStyle(StageStyle.DECORATED);
        notFoundStage.setScene(scene);
        notFoundStage.setTitle("alert");
        notFoundStage.show();
    }
}
