package betaversion.cp2406assignment_beta;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    private static RainfallData rainfallData = new RainfallData();

    private Stage homeStage;

    public static void main(String[] args) {
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

        // Set up the first root and scene
        BorderPane root = new BorderPane();
        root.setCenter(message);
        root.setBottom(buttonBar);

        Scene homeScene = new Scene(root, 500, 500);
        stage.setScene(homeScene);
        stage.setTitle("CP2406 Assignment - Samuel Healion");


        startButton.setOnAction(e -> {
            Scene visualiserScene = RainfallVisualiser.getScene(rainfallData);
            stage.setScene(visualiserScene);
            stage.setTitle("Rainfall Visualiser");
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
