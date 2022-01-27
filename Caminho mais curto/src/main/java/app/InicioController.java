package app;

import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class InicioController {
    @FXML
    private AnchorPane root;

    @FXML
    private ImageView start;

    @FXML
    private ImageView fire;

    private static double xOffset = 0;
    private static double yOffset = 0;

    private BooleanProperty wPressed = new SimpleBooleanProperty();
    private BooleanProperty aPressed = new SimpleBooleanProperty();
    private BooleanProperty sPressed = new SimpleBooleanProperty();
    private BooleanProperty dPressed = new SimpleBooleanProperty();

    private BooleanBinding keyPressed = wPressed.or(aPressed).or(sPressed).or(dPressed);

    private int movementVariable = 6;

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            if (wPressed.get()) {
                Main.primaryStage.setY(Main.primaryStage.getY() - movementVariable);
            }
            if (sPressed.get()) {
                Main.primaryStage.setY(Main.primaryStage.getY() + movementVariable);
            }
            if (aPressed.get()) {
                Main.primaryStage.setX(Main.primaryStage.getX() - movementVariable);
            }
            if (dPressed.get()) {
                Main.primaryStage.setX(Main.primaryStage.getX() + movementVariable);
            }
        }
    };

    @FXML
    public void initialize() {
        movementSetup();
        root.setFocusTraversable(true);
        keyPressed.addListener(((observableValue, aBoolean, t1) -> {
            if (!aBoolean) {
                timer.start();
            } else {
                timer.stop();
            }
        }));
        fire.setVisible(false);
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = Main.primaryStage.getX() - event.getScreenX();
                yOffset = Main.primaryStage.getY() - event.getScreenY();
                fire.setVisible(false);
            }
        });

        root.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                fire.setVisible(false);
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Main.primaryStage.setX(event.getScreenX() + xOffset);
                Main.primaryStage.setY(event.getScreenY() + yOffset);
                fire.setVisible(true);
            }
        });
    }


    public void movementSetup() {
        URL file = this.getClass().getClassLoader().getResource("Rocket Launch.mp3");
        final Media media = new Media(file.toString());
        final MediaPlayer mediaPlayer = new MediaPlayer(media);

        root.setOnKeyPressed(e -> {
            fire.setVisible(true);
            if(e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP){
                wPressed.set(true);
                mediaPlayer.play();
            }

            if(e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT){
                aPressed.set(true);
                mediaPlayer.play();
            }

            if(e.getCode() == KeyCode.S || e.getCode() == KeyCode.DOWN){
                sPressed.set(true);
                mediaPlayer.play();
            }

            if(e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT){
                dPressed.set(true);
                mediaPlayer.play();
            }

            if(e.getCode() == KeyCode.HOME){
                try {
                    changeScene("Carro.fxml");
                    mediaPlayer.stop();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        root.setOnKeyReleased(e -> {
            fire.setVisible(false);
            if(e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP){
                wPressed.set(false);
                mediaPlayer.stop();
            }

            if(e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT){
                aPressed.set(false);
                mediaPlayer.stop();
            }

            if(e.getCode() == KeyCode.S || e.getCode() == KeyCode.DOWN){
                sPressed.set(false);
                mediaPlayer.stop();
            }

            if(e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT){
                dPressed.set(false);
                mediaPlayer.stop();
            }
        });
    }

    public static void changeScene(String fxml) throws IOException {
        Parent parent = FXMLLoader.load(InicioController.class.getClassLoader().getResource(fxml));
        Main.mainScene.setRoot(parent);
    }

    @FXML
    void start(MouseEvent event) {
        try {
            changeScene("Mapas.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void close(MouseEvent event) {
        System.exit(0);
    }
}
