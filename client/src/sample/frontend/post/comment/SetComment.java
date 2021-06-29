package sample.frontend.post.comment;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sample.backend.application.comment.Comment;

public class SetComment {

    @FXML
    private Label commentLabel;

    public void setCommentLabel(Comment comment)
    {
        commentLabel.setText(comment.getAuthor() + ":     " + comment.getText());
    }
}
