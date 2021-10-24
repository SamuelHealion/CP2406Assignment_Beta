package betaversion.cp2406assignment_beta;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    private static RainfallData rainfallData = new RainfallData();

    private final Stage homeStage = new Stage();

    public static void main(String[] args) {
        RainfallAnalyser rainfallAnalyser = new RainfallAnalyser();
        try {
            rainfallData = rainfallAnalyser.analyseDataSet("src/main/resources/betaversion/cp2406assignment_beta/MountSheridanStationCNS.csv");

            for (MonthRainfallData monthRainfallData: rainfallData.getRainfallData()) {
                System.out.println(monthRainfallData.toString());
                System.out.println(monthRainfallData.getMin());
                System.out.println(monthRainfallData.getMax());
            }
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
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Label message = new Label("Welcome to the Rainfall Analyser/Visualiser");
        message.setFont(new Font(20));

        // Set up all the buttons and put them in a Hbox
        Button startButton = new Button("Start Visualiser");
        Button loadButton = new Button("Load Rainfall Data");
        Button quitButton = new Button("Quit");

        HBox buttonBar = new HBox(50, startButton, loadButton, quitButton);
        buttonBar.setAlignment(Pos.CENTER);

        Tooltip startTooltip = new Tooltip(), loadTooltip = new Tooltip(), quitTooltip = new Tooltip();
        startTooltip.setText("Starts the Rainfall Visualiser");
        Tooltip.install(startButton, startTooltip);
        loadTooltip.setText("Load the Rainfall Data from a file on your computer");
        Tooltip.install(loadButton, loadTooltip);
        quitTooltip.setText("Exits the Rainfall Visualiser");
        Tooltip.install(quitButton, quitTooltip);

        // Set up the first root and scene
        BorderPane root = new BorderPane();
        root.setCenter(message);
        root.setBottom(buttonBar);

        Scene homeScene = new Scene(root, 600, 600);
        stage.setScene(homeScene);
        stage.setTitle("CP2406 Assignment - Samuel Healion");


        startButton.setOnAction(e -> {
//            BorderPane visualiserRoot = new BorderPane(RainfallVisualiser.getCanvas(rainfallData));
            BorderPane visualiserRoot = new BorderPane(RainfallVisualiser.getRainfallChart(rainfallData));
            Button returnButton = new Button("Close Visualiser");

            HBox visualiserHBox = new HBox(returnButton);
            visualiserHBox.setAlignment(Pos.CENTER);

            visualiserRoot.setBottom(visualiserHBox);
            visualiserRoot.setStyle("-fx-border-width: 4px; -fx-border-color: #444");
            Scene visualiserScene = new Scene(visualiserRoot, 1200, 600);
            stage.setScene(visualiserScene);
            stage.setTitle("Rainfall Visualiser");

            returnButton.setOnAction(actionEvent -> stage.setScene(homeScene));
        } );
        loadButton.setOnAction(e -> rainfallData = loadRainfallData());
        quitButton.setOnAction(e -> Platform.exit());

        stage.show();
    }

    private RainfallData loadRainfallData() {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(homeStage);
        String path = file.getAbsolutePath();

        RainfallAnalyser rainfallAnalyser = new RainfallAnalyser();
        try {
            rainfallData = rainfallAnalyser.analyseDataSet(path);
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
