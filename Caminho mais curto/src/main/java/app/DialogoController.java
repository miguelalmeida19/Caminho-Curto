package app;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DialogoController {

    @FXML
    private AnchorPane root;

    @FXML
    private AnchorPane dialog;

    @FXML
    private Label title;

    @FXML
    private Label details;

    @FXML
    private ImageView fire;

    @FXML
    public void initialize() {
        fire.setVisible(true);
    }

    public void setTitle(String t) {
        title.setText(t);
    }

    public void setDetails(String d) {
        details.setText(d);
    }

    @FXML
    void okClicked(MouseEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
