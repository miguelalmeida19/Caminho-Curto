package app;

import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class CarroController {

    @FXML
    private AnchorPane root;

    @FXML
    private ImageView pneuDetras;

    @FXML
    private ImageView pneudaFrente;

    @FXML
    private ImageView fire;

    @FXML
    private ImageView helice;

    @FXML
    private Polygon lights;

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
        lights.setVisible(false);
        RotateTransition d = new RotateTransition(Duration.millis(200), root);
        d.setFromAngle(-90);
        d.setByAngle(90);
        d.setCycleCount(1);
        d.setAutoReverse(true);
        d.play();
        URL file = this.getClass().getClassLoader().getResource("car lock.mp3");
        final Media media = new Media(file.toString());
        final MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        helice.setVisible(false);
        movementSetup();
        root.setFocusTraversable(true);
        keyPressed.addListener(((observableValue, aBoolean, t1) -> {
            if (!aBoolean) {
                timer.start();
            } else {
                timer.stop();
            }
        }));
    }

    public void movementSetup() {
        URL file = this.getClass().getClassLoader().getResource("Rocket Launch.mp3");
        final Media media = new Media(file.toString());
        final MediaPlayer mediaPlayer = new MediaPlayer(media);
        RotateTransition rt = new RotateTransition(Duration.millis(500), pneudaFrente);
        rt.setByAngle(360);
        rt.setCycleCount(4);
        rt.setAutoReverse(false);
        rt.setOnFinished((ActionEvent event) -> rt.play());
        RotateTransition rt1 = new RotateTransition(Duration.millis(500), pneuDetras);
        rt1.setByAngle(360);
        rt1.setCycleCount(4);
        rt1.setAutoReverse(false);
        rt1.setOnFinished((ActionEvent event) -> rt.play());
        root.setOnKeyPressed(e -> {
            rt.play();
            rt1.play();
            fire.setVisible(true);
            if(e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP){
                wPressed.set(true);
                helice.setVisible(true);
                mediaPlayer.play();
            }

            if(e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT){
                aPressed.set(true);
                mediaPlayer.play();
            }

            if(e.getCode() == KeyCode.S || e.getCode() == KeyCode.DOWN){
                helice.setVisible(true);
                sPressed.set(true);
                mediaPlayer.play();
            }

            if(e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT){
                dPressed.set(true);
                mediaPlayer.play();
            }

            if(e.getCode() == KeyCode.HOME){
                try {
                    changeScene("Inicio.fxml");
                    mediaPlayer.stop();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if(e.getCode() == KeyCode.PAGE_UP){
                URL fd = this.getClass().getClassLoader().getResource("lights.mp3");
                final Media md = new Media(fd.toString());
                final MediaPlayer mp = new MediaPlayer(md);
                mp.play();
                if (lights.isVisible()){
                    lights.setVisible(false);
                }
                else {
                    lights.setVisible(true);
                }
            }
        });

        root.setOnKeyReleased(e -> {
            rt.stop();
            rt1.stop();
            fire.setVisible(false);
            if(e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP){
                helice.setVisible(false);
                wPressed.set(false);
                mediaPlayer.stop();
            }

            if(e.getCode() == KeyCode.A || e.getCode() == KeyCode.LEFT){
                aPressed.set(false);
                mediaPlayer.stop();
            }

            if(e.getCode() == KeyCode.S || e.getCode() == KeyCode.DOWN){
                helice.setVisible(false);
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
        Parent parent = FXMLLoader.load(CarroController.class.getClassLoader().getResource(fxml));
        Main.mainScene.setRoot(parent);
    }
}
