package sample.frontend.directmessage;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sample.backend.application.directmessage.Message;

/**
 * @author Reyhaneh Saffar
 * representing every message which appears on the screen
 */
public class MessageController
{

    @FXML
    private Label messageLabel;

    public void setMessageLabel(Message message)
    {
        messageLabel.setText("==>>" + message.getSender() + "\n" + message.getText());
    }
}
