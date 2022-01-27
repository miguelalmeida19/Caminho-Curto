package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Dialogo {


    public Dialogo() throws IOException {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("./Diálogo.fxml"));
        Stage primaryStage = new Stage();
        Scene scene = new Scene(root, 973, 552);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("styles.css");
        primaryStage.setAlwaysOnTop(true);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Alerta!");
        primaryStage.setScene(scene);
        //DialogoController dialogoController = new DialogoController();
        //dialogoController.setDialog(title, details);
        primaryStage.show();
    }
}
