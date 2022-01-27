package app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main extends Application {
    static Stage primaryStage;
    static Scene mainScene;
    static String currentMap;
    static List<String> maps = Arrays.asList(new String[]{"Mapas.png", "Mapas1.jpg", "Mapas2.png", "Mapas3.jpg", "Mapas4.png"});

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        int randomNum = ThreadLocalRandom.current().nextInt(0, 4);
        currentMap = maps.get(randomNum);
        Platform.setImplicitExit(false);
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Inicio.fxml"));

        Scene scene = new Scene(root, 973, 552);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("styles.css");
        mainScene = scene;
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.getIcons().add(new Image("icon.png"));

        primaryStage.setTitle("Caminho + curto");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void setCurrentMap(String currentMap) {
        Main.currentMap = currentMap;
    }

    public static int getCurrentMapIndex() {
        int count = 0;
        for (String map: maps){
            if (currentMap.equals(map)){
                return count;
            }
            count++;
        }
        return 0;
    }
}
