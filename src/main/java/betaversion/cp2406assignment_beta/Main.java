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
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    private static RainfallData rainfallData = new RainfallData();
    private static final RainfallAnalyser rainfallAnalyser = new RainfallAnalyser();

    private final Stage primaryStage = new Stage();
    private final Stage helpStage = new Stage();
    private Scene homeScene;
    private Scene visualiserScene;
    private final Label statusBar = new Label();

    /**
     * Main method that starts the application
     */
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {

        // Build the homeScene. Don't need to build the visualiserScene until it is called.
        buildHomeScene();
        buildHelpStage();
        statusBar.setText("Please load a rainfall csv to be analysed");

        // Set the stage to the Home Scene and show it
        setPrimaryStage(homeScene);
        primaryStage.show();
    }

    /**
     * Set which scene is being displayed on the PrimaryStage
     * Adjusts Resizability and centers the window each time.
     */
    private void setPrimaryStage(Scene newScene) {
        if (newScene == homeScene) {
            primaryStage.setScene(homeScene);
            primaryStage.setTitle("CP2406 Assignment - Samuel Healion");
            primaryStage.centerOnScreen();
            primaryStage.setResizable(false);
        } else if (newScene == visualiserScene)
            primaryStage.setScene(visualiserScene);
            primaryStage.setTitle("Rainfall Visualiser");
            primaryStage.centerOnScreen();
            primaryStage.setResizable(true);
    } // end setPrimaryStage

    /**
     * Builds the Scene used for the Home Scene
     */
    private void buildHomeScene() {
        // Set up all the labels and put them in a VBox
        Label message = new Label("Welcome to the Rainfall Visualiser");
        message.setFont(new Font(20));
        statusBar.setAlignment(Pos.CENTER);
        VBox labelBar = new VBox(message, statusBar);
        labelBar.setAlignment(Pos.CENTER);

        // Set up all the buttons and put them in a HBox
        Button startButton = new Button("Start Visualiser");
        Button loadButton = new Button("Load Rainfall Data");
        Button quitButton = new Button("Quit");

        Tooltip startTooltip = new Tooltip(), loadTooltip = new Tooltip(), quitTooltip = new Tooltip();
        startTooltip.setText("Starts the Rainfall Visualiser");
        Tooltip.install(startButton, startTooltip);
        loadTooltip.setText("Load the Rainfall Data from a file on your computer");
        Tooltip.install(loadButton, loadTooltip);
        quitTooltip.setText("Exits the Rainfall Visualiser");
        Tooltip.install(quitButton, quitTooltip);

        HBox buttonBar = new HBox(50, startButton, loadButton, quitButton);
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.setPrefHeight(50);

        MenuButton menuButton = buildMenuButton();
        MenuBar menuBar = buildMenuBar();

        // Set up the home root and scene
        BorderPane homeRoot = new BorderPane();
        homeRoot.setCenter(labelBar);
        homeRoot.setBottom(buttonBar);
        homeRoot.setRight(menuButton);
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

        MenuBar menuBar = buildMenuBar();

        visualiserRoot.setTop(menuBar);
        visualiserRoot.setBottom(visualiserHBox);
        visualiserRoot.setStyle("-fx-border-width: 2px; -fx-border-color: GRAY");
        visualiserScene = new Scene(visualiserRoot, 1200, 600);

        returnButton.setOnAction(actionEvent -> setPrimaryStage(homeScene));
    }

    private void buildHelpStage() {
        TabPane helpPane = new TabPane();
        helpPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        helpPane.setTabDragPolicy(TabPane.TabDragPolicy.FIXED);
        helpPane.setStyle("-fx-border-width: 2px; -fx-border-color: GRAY");

        Label generalHelp = new Label("""
                Welcome to the Rainfall visualiser app.
                
                This app will analyse a Comma Separated Value (CSV) file and
                give the recorded rainfall data as a bar chart on the
                visualiser screen.
                
                This app was designed for the subject CP2406 at JCU by
                Samuel Healion.
                
                It makes use of the Commons CSV package from Apache.
                
                Please select a specific tab if you need a more detailed
                explanation.""");
        Label loadingHelp = new Label("""
                There are three ways to load a file into the Rainfall Visualiser.

                The first is to use the load option from the top of the app.
                This will open up a file explorer window and allows the user
                to enter a new file to be analysed and then represented with
                a graph on the visualiser. This option is available from both
                the home screen and the visualiser screen.

                The second is by clicking the button on the home screen. This
                also opens up the file explorer to load in a new file.

                The third is from the drop down list on the left of the home
                screen. This is a list of previously loaded and saved files
                and are stored in the project itself. Files can only be added
                to this list by choosing the save option in the app itself.""");
        Label savingHelp = new Label("""
                To save a file, the user needs to select the save file at the
                top of the app screen. The app needs to have a file loaded into
                the app before it can save any rainfall data. This saved data is
                stored in the project and can only be loaded in through the drop
                down list on the right on the home screen.

                This feature was included to prevent the user from having to
                manually find a file each time they wanted to view the graph
                in the visualiser. It is important to note that no saved data
                will appear in the list until the app is closed and then
                launched again.""");
        Label visualiserHelp = new Label("""
                The visualiser represents the currently loaded rainfall data
                as a bar chart. The user can hover their cursor over the bar
                of an entry and get its current value. This value includes the
                date, whether it is a minimum, maximum or total value in
                millimeters. A new file can be loaded into the chart from the
                visualiser screen by making use of the menu bar at the top
                of the screen. The bar chart will update itself on this screen.""");

        Tab general = new Tab("General", generalHelp);
        Tab load = new Tab("Loading", loadingHelp);
        Tab save = new Tab("Saving", savingHelp);
        Tab visualiser = new Tab("Visualiser", visualiserHelp);

        helpPane.getTabs().addAll(general, load, save, visualiser);
        Button close = new Button("Close Help");
        close.setOnAction(e -> helpStage.hide());
        VBox helpBox = new VBox(helpPane, close);
        helpBox.setAlignment(Pos.CENTER);

        Scene helpScene = new Scene(helpBox, 400, 300);
        helpStage.setScene(helpScene);
        helpStage.setTitle("Help for Rainfall Visualiser");
        helpStage.setResizable(false);
        helpStage.initStyle(StageStyle.UTILITY);

        helpStage.focusedProperty().addListener( (obj,oldVal,newVal) -> {
            if (!newVal) {
                helpStage.hide();
            }
        });

    } // end buildHelpStage()

    private MenuButton buildMenuButton() {
        MenuButton analysedList = new MenuButton();
        analysedList.setText("Saved Rainfall Data");
        analysedList.setAlignment(Pos.CENTER);

        File f = new File("src/main/resources/betaversion/cp2406assignment_beta/analysedrainfalldata");
        if (Objects.requireNonNull(f.list()).length == 0) {
            MenuItem noData = new MenuItem("No saved analysed rainfall data");
            analysedList.getItems().add(noData);
        } else {
            for (String filename : Objects.requireNonNull(f.list())) {
                MenuItem choice = new MenuItem(filename);
                choice.setOnAction(e -> {
                    String path = f.getAbsolutePath() + "\\" + filename;
                    try {
                        rainfallData = rainfallAnalyser.getAnalysedRainfallData(path);
                        buildRainfallVisualiserScene();
                        statusBar.setText(filename + " successfully loaded");
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                        ex.printStackTrace();
                    }
                });
                analysedList.getItems().add(choice);
            }
        }
        return analysedList;
    } // end buildMenuButton

    private MenuBar buildMenuBar() {
        Menu fileMenu = new Menu("File");
        Menu helpMenu = new Menu("Help");
        MenuBar menuBar = new MenuBar();
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
        save.setOnAction(e -> {
            String filename = rainfallAnalyser.saveRainfallData(rainfallData);
            if (filename == null)
                statusBar.setText("No file loaded to save");
            else
                statusBar.setText(filename + " successfully saved");
        });

        MenuItem close = new MenuItem("Close");
        close.setOnAction(e -> Platform.exit());

        fileMenu.getItems().addAll(open, save, close);

        MenuItem help = new MenuItem("Help");
        help.setOnAction(e -> helpStage.show());
        helpMenu.getItems().add(help);

        return menuBar;
    }

    private RainfallData loadRainfallData() {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(primaryStage);
        String path = file.getAbsolutePath();

        try {
            rainfallData = rainfallAnalyser.analyseRainfallData(path);
            statusBar.setText(rainfallData.getFilename() + " successfully loaded");
        } catch (Exception err) {
            statusBar.setText("Failed to load " + file.getName() + "\n" +
                    err.getMessage());
        }

        return rainfallData;
    } // end loadRainfallData()

}
