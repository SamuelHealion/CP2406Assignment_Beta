package betaversion.cp2406assignment_beta;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    private static RainfallData rainfallData = new RainfallData();

    private Scene homeScene;
    private final MenuBar menuBar = new MenuBar();

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {

        Label message = new Label("Welcome to the Rainfall Analyser/Visualiser");
        message.setFont(new Font(20));

        // Set up all the buttons and put them in a Hbox
        Button startButton = new Button("Start Visualiser");
        Button loadButton = new Button("Load Rainfall Data");
        Button quitButton = new Button("Quit");

        HBox buttonBar = new HBox(100, startButton, loadButton, quitButton);
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.setPrefHeight(50);

        Tooltip startTooltip = new Tooltip(), loadTooltip = new Tooltip(), quitTooltip = new Tooltip();
        startTooltip.setText("Starts the Rainfall Visualiser");
        Tooltip.install(startButton, startTooltip);
        loadTooltip.setText("Load the Rainfall Data from a file on your computer");
        Tooltip.install(loadButton, loadTooltip);
        quitTooltip.setText("Exits the Rainfall Visualiser");
        Tooltip.install(quitButton, quitTooltip);

        createMenuBar(stage);

        // Set up the first root and scene
        BorderPane homeRoot = new BorderPane();
        homeRoot.setCenter(message);
        homeRoot.setBottom(buttonBar);
        homeRoot.setTop(menuBar);
        homeRoot.setStyle("-fx-border-width: 2px; -fx-border-color: #444");


        homeScene = new Scene(homeRoot, 600, 600);
        stage.setScene(homeScene);
        stage.setTitle("CP2406 Assignment - Samuel Healion");
        stage.centerOnScreen();
        stage.setResizable(false);


        startButton.setOnAction(e -> buildRainfallVisualiserStage(stage));
        loadButton.setOnAction(e -> rainfallData = loadRainfallData(stage));
        quitButton.setOnAction(e -> Platform.exit());

        stage.show();
    }

    private void createMenuBar(Stage stage) {
        Menu fileMenu = new Menu("File");
        Menu helpMenu = new Menu("Help");
        menuBar.getMenus().addAll(fileMenu, helpMenu);

        MenuItem open = new MenuItem("Open");
        open.setOnAction(e -> {
            if (stage.getScene().equals(homeScene))
                loadRainfallData(stage);
            else {
                loadRainfallData(stage);
                buildRainfallVisualiserStage(stage);
            }
        });

        MenuItem save = new MenuItem("Save");


        MenuItem close = new MenuItem("Close");
        close.setOnAction(e -> Platform.exit());

        fileMenu.getItems().addAll(open, save, close);
    }

    /**
     * Creates the Stage for the Rainfall Visualiser.
     * Get the StackedBarChart from the Rainfall Visualiser class
     */
    private void buildRainfallVisualiserStage(Stage stage) {
        BorderPane visualiserRoot = new BorderPane(RainfallVisualiser.getRainfallBarChart(rainfallData));
        Button returnButton = new Button("Close Visualiser");

        HBox visualiserHBox = new HBox(returnButton);
        visualiserHBox.setAlignment(Pos.CENTER);

        visualiserRoot.setTop(menuBar);
        visualiserRoot.setBottom(visualiserHBox);
        visualiserRoot.setStyle("-fx-border-width: 2px; -fx-border-color: GRAY");
        Scene visualiserScene = new Scene(visualiserRoot, 1200, 600);
        stage.setScene(visualiserScene);
        stage.setTitle("Rainfall Visualiser");
        stage.centerOnScreen();

        returnButton.setOnAction(actionEvent -> {
            stage.setScene(homeScene);
            stage.centerOnScreen();
        });
    }

    private RainfallData loadRainfallData(Stage stage) {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(stage);
        String path = file.getAbsolutePath();

        RainfallAnalyser rainfallAnalyser = new RainfallAnalyser();
        try {
            rainfallData = rainfallAnalyser.analyseRainfallData(path);
        } catch (IOException err) {
            System.out.println("Something went wrong");
            System.out.println(err.getMessage());
        } catch (NumberFormatException err) {
            System.out.println("There was an issue in the file data");
            System.out.println(err.getMessage());
        } catch (IllegalArgumentException err) {
            System.out.println("There was an issue parsing the rainfall data");
            System.out.println(err.getMessage());
        }

        System.out.println("Successfully loaded rainfall data");
        return rainfallData;
    }

}
