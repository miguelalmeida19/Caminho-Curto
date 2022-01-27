package app;

import javafx.animation.*;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.NodeOrientation;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MapaController {

    @FXML
    private AnchorPane root;

    static final double AVERAGE_SPEED = 50.0;

    @FXML
    private AnchorPane costDialog;

    @FXML
    private Label pathField;

    @FXML
    private Label distanceField;

    @FXML
    private Label timeField;

    @FXML
    private Circle currentMap;

    private List<Line> linesList;

    @FXML
    private AnchorPane dialog;

    private List<String> pointsCoordinates;

    private Map map;

    @FXML
    private AnchorPane defDialog;

    @FXML
    private Label cost;

    @FXML
    private ImageView def;

    @FXML
    private AnchorPane mapas;

    @FXML
    private ImageView next;

    @FXML
    private ImageView before;

    @FXML
    private AnchorPane findPathDialog;

    @FXML
    private TextField start;

    @FXML
    private TextField end;

    @FXML
    private ImageView fire;

    @FXML
    private AnchorPane invalidPathDialog;

    @FXML
    private ImageView partida;

    @FXML
    private ImageView chegada;

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
        partida.setVisible(false);
        chegada.setVisible(false);
        //System.out.println(partida.getTranslateX());
        //System.out.println(partida.getTranslateY());
        partida.setTranslateX(-6.0);
        partida.setTranslateY(-15.0);
        chegada.setTranslateX(-6.0);
        chegada.setTranslateY(-15.0);
        invalidPathDialog.setVisible(false);
        fire.setVisible(false);
        costDialog.setVisible(false);
        mapas.setStyle("-fx-background-image: url('" + Main.currentMap + "'); ");
        RotateTransition rt = new RotateTransition(Duration.millis(3000), def);
        rt.setByAngle(180);
        rt.setCycleCount(4);
        rt.setAutoReverse(true);
        rt.setOnFinished((ActionEvent event) -> rt.play());
        rt.play();


        findPathDialog.setVisible(false);
        defDialog.setVisible(false);
        dialog.setVisible(false);
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
                    RotateTransition d = new RotateTransition(Duration.millis(500), root);
                    d.setByAngle(90);
                    d.setCycleCount(1);
                    d.setAutoReverse(false);
                    d.play();
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
        Parent parent = FXMLLoader.load(MapaController.class.getClassLoader().getResource(fxml));
        Main.mainScene.setRoot(parent);
    }

    @FXML
    void findPath(MouseEvent event) {
        FadeTransition g = new FadeTransition(Duration.millis(200), costDialog);
        g.setFromValue(1.0);
        g.setToValue(0.0);
        g.setAutoReverse(true);
        g.play();
        for (Node line : mapas.getChildren()) {
            if (line instanceof Line) {
                if (((Line) line).getStroke().equals(Color.GREEN)) {
                    ((Line) line).setStroke(Color.BLACK);
                }
            }
        }
        findPathDialog.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(500), findPathDialog);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setAutoReverse(true);

        ft.play();
    }

    @FXML
    void go(MouseEvent event) throws IOException {
        try {

            int startPoint = Integer.parseInt(start.getText());
            int endPoint = Integer.parseInt(end.getText());

            partida.setVisible(true);
            partida.setX(Double.parseDouble(pointsCoordinates.get(startPoint).split(",")[0]));
            partida.setY(Double.parseDouble(pointsCoordinates.get(startPoint).split(",")[1]));

            chegada.setVisible(true);
            chegada.setX(Double.parseDouble(pointsCoordinates.get(endPoint).split(",")[0]));
            chegada.setY(Double.parseDouble(pointsCoordinates.get(endPoint).split(",")[1]));


            String solution = ShortestPath.dijkstra(map.getArr(), startPoint, endPoint);
            //System.out.println(Arrays.deepToString(map.getArr()));
            //System.out.println(solution);
            String custo = solution.split("\t\t")[1];
            String percurso = solution.split("\t\t")[2];
            //System.out.println("Percurso: " + percurso);
            pathField.setText(percurso);
            distanceField.setText(custo + "km");


            timeField.setText(estimatedTime(Integer.parseInt(custo)));

            int count = 0;
            int count1 = 0;


            for (Node line : mapas.getChildren()) {
                if (line instanceof Line) {
                    for (int i = 0; i < percurso.split(" ").length - 1; i++) {
                        count = Integer.parseInt(percurso.split(" ")[i]);
                        count1 = Integer.parseInt(percurso.split(" ")[i + 1]);
                        //System.out.println("Count: " + count);
                        //System.out.println("Count1: " + count1);
                        double startX = Double.parseDouble(pointsCoordinates.get(count).split(",")[0]);
                        double startY = Double.parseDouble(pointsCoordinates.get(count).split(",")[1]);
                        double endX = Double.parseDouble(pointsCoordinates.get(count1).split(",")[0]);
                        double endY = Double.parseDouble(pointsCoordinates.get(count1).split(",")[1]);
                        Line l = (Line) line;
                        if (l.getStartX() == startX && l.getStartY() == startY && l.getEndX() == endX && l.getEndY() == endY ||
                                l.getEndX() == startX && l.getEndY() == startY && l.getStartX() == endX && l.getStartY() == endY) {
                            //System.out.println("Entrei");
                            ((Line) line).setFill(Color.GREEN);
                            ((Line) line).setStroke(Color.GREEN);
                        }
                    }
                }
            }
            costDialog.setVisible(true);
            FadeTransition g = new FadeTransition(Duration.millis(200), costDialog);
            g.setFromValue(0.0);
            g.setToValue(1.0);
            g.setAutoReverse(true);
            g.play();
            //System.out.println(pointsCoordinates);
            FadeTransition ft = new FadeTransition(Duration.millis(200), findPathDialog);
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            ft.setAutoReverse(true);

            ft.play();
            invalidPathDialog.setVisible(false);
        } catch (Exception e) {
            partida.setVisible(false);
            chegada.setVisible(false);
            System.out.println("Caminho não existe");
            FadeTransition ft = new FadeTransition(Duration.millis(50), findPathDialog);
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            ft.setAutoReverse(true);

            ft.play();
            invalidPathDialog.setVisible(true);
            //Dialogo dialogo = new Dialogo();
            //setWarningMsg("Colocaste um ou mais pontos que não existem, ou esse caminho não é possível.");
            //showDialog();
        }
    }

    @FXML
    void back(MouseEvent event) {
        try {
            InicioController.changeScene("Inicio.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void close(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void generateMap(MouseEvent event) {
        partida.setVisible(false);
        chegada.setVisible(false);
        linesList = new ArrayList<>();
        mapas.getChildren().clear();
        map = new Map();
        List<String> pointsCoordinates = generatePoints(map);

        //Pode ser útil para alguma funcionalidade depois
        //List<String> allPaths = getAllPaths(map);

        //System.out.println(Arrays.deepToString(map.getArr()));

        for (int i = 0; i < map.getSize(); i++) {
            for (int f = 0; f < map.getSize(); f++) {
                if (map.getArr()[i][f] != 0) {
                    int startX = Integer.parseInt(pointsCoordinates.get(i).split(",")[0]);
                    int startY = Integer.parseInt(pointsCoordinates.get(i).split(",")[1]);
                    int endX = Integer.parseInt(pointsCoordinates.get(f).split(",")[0]);
                    int endY = Integer.parseInt(pointsCoordinates.get(f).split(",")[1]);

                    Line line = new Line(startX, startY, endX, endY);
                    line.setStroke(Color.BLACK);
                    line.setStrokeWidth(3.0);

                    int finalI = i;
                    int finalF = f;
                    line.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
                        if (newValue) {
                            dialog.setVisible(true);
                            TranslateTransition ft = new TranslateTransition(Duration.millis(500), dialog);
                            ft.setFromY(-120);
                            ft.setByY(130);
                            ft.play();
                            cost.setText("Distância: " + map.getArr()[finalI][finalF] + " km");
                            line.setCursor(Cursor.HAND);
                            if (line.getStroke() != Color.GREEN) {
                                line.setStroke(Color.YELLOW);
                            } else {
                                line.setStroke(Color.ORANGE);
                            }
                        } else {
                            TranslateTransition ft = new TranslateTransition(Duration.millis(500), dialog);
                            ft.setFromY(0);
                            ft.setByY(-250);
                            ft.play();
                            if (line.getStroke() != Color.ORANGE) {
                                line.setStroke(Color.BLACK);
                            } else {
                                line.setStroke(Color.GREEN);
                            }
                            line.setCursor(Cursor.DEFAULT);
                        }
                    });

                    Circle circleStart = new Circle();
                    circleStart.setCenterX(startX);
                    circleStart.setCenterY(startY);
                    circleStart.setRadius(3);
                    circleStart.setFill(Color.ORANGE);
                    circleStart.setCursor(Cursor.HAND);
                    int finalI1 = i;
                    circleStart.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
                        if (newValue) {
                            dialog.setVisible(true);
                            TranslateTransition ft = new TranslateTransition(Duration.millis(500), dialog);
                            ft.setFromY(-120);
                            ft.setByY(130);
                            ft.play();
                            cost.setText("Ponto: " + (finalI1));
                            circleStart.setStroke(Color.WHITE);
                        } else {
                            TranslateTransition ft = new TranslateTransition(Duration.millis(500), dialog);
                            ft.setFromY(0);
                            ft.setByY(-250);
                            ft.play();
                            //circleStart.setStroke(Color.BLACK);
                        }
                    });


                    Circle circleEnd = new Circle();
                    circleEnd.setCenterX(endX);
                    circleEnd.setCenterY(endY);
                    circleEnd.setRadius(3);
                    circleEnd.setFill(Color.ORANGE);
                    circleEnd.setCursor(Cursor.HAND);
                    int finalF1 = f;
                    circleEnd.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
                        if (newValue) {
                            dialog.setVisible(true);
                            TranslateTransition ft = new TranslateTransition(Duration.millis(500), dialog);
                            ft.setFromY(-120);
                            ft.setByY(130);
                            ft.play();
                            cost.setText("Ponto: " + (finalF1));
                            circleStart.setStroke(Color.WHITE);
                        } else {
                            TranslateTransition ft = new TranslateTransition(Duration.millis(500), dialog);
                            ft.setFromY(0);
                            ft.setByY(-250);
                            ft.play();
                            //circleStart.setStroke(Color.BLACK);
                        }
                    });

                    if (containsLine(line) == true) {
                        linesList.add(line);
                        mapas.getChildren().add(line);
                        ScaleTransition st = new ScaleTransition(Duration.millis(200), line);
                        st.setFromX(0.0f);
                        st.setFromY(0.0f);
                        st.setByX(1.0);
                        st.setByY(1.0);
                        st.setCycleCount(1);
                        st.setAutoReverse(false);

                        st.play();
                    }
                    if (!mapas.getChildren().contains(circleStart)) {
                        mapas.getChildren().add(circleStart);
                    }
                    if (!mapas.getChildren().contains(circleEnd)) {
                        mapas.getChildren().add(circleEnd);
                    }
                }
            }
        }

        int cnt = 0;
        for (Node f : mapas.getChildren()) {
            if (f instanceof Line) {
                cnt++;
            }
        }

        //System.out.println("Nº de Linhas: " + cnt);
    }

    private List<String> generatePoints(Map map) {
        pointsCoordinates = new ArrayList<>();
        for (int i = 0; i < map.getSize(); i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(1, 7);
            String coordinate = "";
            coordinate = ((i + 1) * 3 * 10) + "," + (randomNum * 15);
            pointsCoordinates.add(coordinate);
        }
        return pointsCoordinates;
    }

    private List<String> getAllPaths(Map map) {
        Graph g = new Graph(map.getSize());
        for (int i = 0; i < map.getArr().length; i++) {
            for (int f = 0; f < map.getArr().length; f++) {
                if (map.getArr()[i][f] != 0) {
                    g.addEdge(i, f);
                }
            }
        }
        List<String> allPaths = new ArrayList<>();
        for (int i = 0; i < map.getArr().length; i++) {
            int counter = 1;
            while (counter <= map.getArr().length - 1) {
                List<String> allPossiblePaths = g.printAllPaths(i, counter);
                for (String path : allPossiblePaths) {
                    if (!allPaths.contains(path)) {
                        allPaths.add(path);
                    }
                }
                counter++;
            }
        }
        return allPaths;
    }

    @FXML
    void closeDialog(MouseEvent event) {

    }

    private boolean containsLine(Line line) {
        for (Line l : linesList) {
            Line lCopy = l;
            //lCopy.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            String lToString = lCopy.toString();
            String lineToString = line.toString();

            if (l.getStartX() == line.getEndX()) {
                return false;
            }
        }
        return true;
    }

    @FXML
    void defClicked(MouseEvent event) {
        FadeTransition g = new FadeTransition(Duration.millis(200), costDialog);
        g.setFromValue(1.0);
        g.setToValue(0.0);
        g.setAutoReverse(true);
        g.play();
        costDialog.setVisible(false);
        if (defDialog.getOpacity() == 1.0 && defDialog.isVisible()) {
            //System.out.println("Estava ativada");
            FadeTransition ft = new FadeTransition(Duration.millis(200), defDialog);
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            ft.setAutoReverse(true);

            ft.play();
            defDialog.setOpacity(0.0);
        } else {
            //System.out.println("Estava desativada");
            defDialog.setVisible(true);
            defDialog.setOpacity(1.0);
            FadeTransition ft = new FadeTransition(Duration.millis(200), defDialog);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.setAutoReverse(true);

            ft.play();

        }

        currentMap.setFill(new ImagePattern(new Image(Main.currentMap)));
        next.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (Main.getCurrentMapIndex() + 1 == Main.maps.size()) {
                    Main.setCurrentMap(Main.maps.get(0));
                } else {
                    Main.setCurrentMap(Main.maps.get(Main.getCurrentMapIndex() + 1));
                }
                currentMap.setFill(new ImagePattern(new Image(Main.currentMap)));
            }
        });
        before.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (Main.getCurrentMapIndex() - 1 < 0) {
                    Main.setCurrentMap(Main.maps.get(Main.maps.size() - 1));
                } else {
                    Main.setCurrentMap(Main.maps.get(Main.getCurrentMapIndex() - 1));
                }
                currentMap.setFill(new ImagePattern(new Image(Main.currentMap)));
            }
        });
    }

    @FXML
    void apply(MouseEvent event) {
        mapas.setStyle("-fx-background-image: url('" + Main.currentMap + "'); ");
    }

    static String estimatedTime(double dist) {
        //System.out.print("\n Distance(km) : "+ dist) ;
        //System.out.print("\n Speed(km / hr) : " + speed) ;

        double time = dist / AVERAGE_SPEED;
        double minutes = Integer.parseInt(Double.valueOf(time).toString().split("\\.")[1]);
        minutes = minutes * 0.01 * 60;
        //System.out.println("minutes: " + minutes);
        //System.out.println("0" + Double.valueOf(time).toString().split("\\.")[0] + ":" + Double.valueOf(minutes).toString().split("\\.")[0]);
        String hourString = "0" + Double.valueOf(time).toString().split("\\.")[0] + "h";
        String minutesString = Double.valueOf(minutes).toString().split("\\.")[0];
        if (Integer.parseInt(minutesString) < 10) {
            minutesString = "0" + minutesString;
        }

        return String.valueOf(hourString + ":" + minutesString + "min");
    }

    @FXML
    void backClicked(MouseEvent event) {
        FadeTransition g = new FadeTransition(Duration.millis(200), costDialog);
        g.setFromValue(1.0);
        g.setToValue(0.0);
        g.setAutoReverse(true);
        g.play();
        costDialog.setVisible(false);
    }

    public static void setWarningMsg(String text) {
        Toolkit.getDefaultToolkit().beep();
        JOptionPane optionPane = new JOptionPane(text, JOptionPane.WARNING_MESSAGE);
        JDialog dialog = optionPane.createDialog("Warning!");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    void showDialog() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./Dialogo.fxml"));
        Parent parent = fxmlLoader.load();
        DialogoController dialogController = fxmlLoader.<DialogoController>getController();
        //dialogController.setAppMainObservableList(tvObservableList);

        Scene scene = new Scene(parent, 800, 800);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void okClicked(MouseEvent event) {
        invalidPathDialog.setVisible(false);
    }
}
