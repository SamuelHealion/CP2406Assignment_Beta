package betaversion.cp2406assignment_beta;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    private static RainfallData rainfallData = new RainfallData();
    private final RainfallAnalyser rainfallAnalyser = new RainfallAnalyser();

    private final Stage primaryStage = new Stage();
    private Scene homeScene;
    private Scene visualiserScene;
    private final MenuBar menuBar = new MenuBar();
    private final Label statusBar = new Label();

    /**
     * Main method that starts the application
     */
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {

        // Build the menuBar and homeScene. Don't need to build the visualiserScene until it is called.
        buildMenuBar();
        buildHomeScene();

        // Set the stage to the Home Scene and show it
        setPrimaryStage(homeScene);
        primaryStage.show();
    }

    private void setPrimaryStage(Scene newScene) {
        if (newScene == homeScene) {
            primaryStage.setScene(homeScene);
            primaryStage.setTitle("CP2406 Assignment - Samuel Healion");
            primaryStage.centerOnScreen();
            primaryStage.setResizable(false);
        } else if (newScene == visualiserScene)
//            buildRainfallVisualiserScene();
            primaryStage.setScene(visualiserScene);
            primaryStage.setTitle("Rainfall Visualiser");
            primaryStage.centerOnScreen();
            primaryStage.setResizable(true);
    }

    private void buildHomeScene() {

        // Set up all the labels and put them in a VBox
        Label message = new Label("Welcome to the Rainfall Visualiser");
        message.setFont(new Font(20));
        statusBar.setText("Please load a rainfall csv to be analysed");
        VBox labelBar = new VBox(message, statusBar);
        labelBar.setAlignment(Pos.CENTER);

        // Set up all the buttons and put them in a HBox
        Button startButton = new Button("Start Visualiser");
        Button loadButton = new Button("Load Rainfall Data");
        Button quitButton = new Button("Quit");

        HBox buttonBar = new HBox(50, startButton, loadButton, quitButton);
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.setPrefHeight(50);

        Tooltip startTooltip = new Tooltip(), loadTooltip = new Tooltip(), quitTooltip = new Tooltip();
        startTooltip.setText("Starts the Rainfall Visualiser");
        Tooltip.install(startButton, startTooltip);
        loadTooltip.setText("Load the Rainfall Data from a file on your computer");
        Tooltip.install(loadButton, loadTooltip);
        quitTooltip.setText("Exits the Rainfall Visualiser");
        Tooltip.install(quitButton, quitTooltip);

        // Set up the home root and scene
        BorderPane homeRoot = new BorderPane();
        homeRoot.setCenter(labelBar);
        homeRoot.setBottom(buttonBar);
        homeRoot.setTop(menuBar);
        homeRoot.setStyle("-fx-border-width: 2px; -fx-border-color: #444");

        homeScene = new Scene(homeRoot, 600, 600);

        startButton.setOnAction(e -> {
            buildRainfallVisualiserScene();
            setPrimaryStage(visualiserScene);
        });
        loadButton.setOnAction(e -> rainfallData = loadRainfallData());
        quitButton.setOnAction(e -> Platform.exit());

    }

    /**
     * Creates the Stage for the Rainfall Visualiser.
     * Get the StackedBarChart from the Rainfall Visualiser class
     */
    private void buildRainfallVisualiserScene() {
        BorderPane visualiserRoot = new BorderPane(RainfallVisualiser.getRainfallBarChart(rainfallData));
        Button returnButton = new Button("Close Visualiser");

        HBox visualiserHBox = new HBox(returnButton);
        visualiserHBox.setAlignment(Pos.CENTER);

        visualiserRoot.setTop(menuBar);
        visualiserRoot.setBottom(visualiserHBox);
        visualiserRoot.setStyle("-fx-border-width: 2px; -fx-border-color: GRAY");
        visualiserScene = new Scene(visualiserRoot, 1200, 600);

        returnButton.setOnAction(actionEvent -> setPrimaryStage(homeScene));
    }

    private void buildMenuBar() {
        Menu fileMenu = new Menu("File");
        Menu helpMenu = new Menu("Help");
        menuBar.getMenus().addAll(fileMenu, helpMenu);

        MenuItem open = new MenuItem("Open");
        open.setOnAction(e -> {
            loadRainfallData();
            if (primaryStage.getScene().equals(visualiserScene)) {
                buildRainfallVisualiserScene();
                setPrimaryStage(visualiserScene);
            }
        });

        MenuItem save = new MenuItem("Save");
        save.setOnAction(e -> rainfallAnalyser.saveRainfallData(rainfallData));

        MenuItem close = new MenuItem("Close");
        close.setOnAction(e -> Platform.exit());

        fileMenu.getItems().addAll(open, save, close);
    }

    private RainfallData loadRainfallData() {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(primaryStage);
        String path = file.getAbsolutePath();

        try {
            rainfallData = rainfallAnalyser.analyseRainfallData(path);
            statusBar.setText(rainfallData.getFilename() + " successfully loaded");
        } catch (IOException | IllegalArgumentException err) {
            statusBar.setText("Failed to load " + file.getName() + "\n" +
                    "Refer to the console for further details");
            System.out.println(err.getMessage());
        }

        return rainfallData;
    } // end loadRainfallData()

}
