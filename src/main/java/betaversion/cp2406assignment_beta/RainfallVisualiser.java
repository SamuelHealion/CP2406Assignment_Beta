package betaversion.cp2406assignment_beta;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This file can be used to draw a chart that effectively represents rainfall data.  Just fill in
 * the definition of drawPicture with the code that draws your picture.
 */
public class RainfallVisualiser extends Application {

    /**
     * Draws a picture.  The parameters width and height give the size
     * of the drawing area, in pixels.
     */
    public void drawPicture(GraphicsContext g, int width, int height) {

        // Create the x-axis and y-axis
        int border_width = 20;
        g.setStroke(Color.BLACK);
        g.setLineWidth(2);
        g.strokeLine(border_width, border_width, border_width, height - border_width);
        g.strokeLine(border_width, height - border_width, width - border_width, height - border_width);


        RainfallAnalyser rainfallAnalyser = new RainfallAnalyser();
        RainfallData rainfallData = new RainfallData();
        try {
            rainfallData = rainfallAnalyser.analyseDataSet("src/main/resources/betaversion/cp2406assignment_beta/IDCJAC0009_031205_1800_Data.csv");
            System.out.println("Successfully loaded the data");
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

        double xAxisLength = width - 2 * border_width;
        double yAxisLength = height - 2 * border_width;
        double currentXPos = border_width;
        double barWidth = xAxisLength / rainfallData.getNumberOfMonths();
        double maxMonthlyTotal = rainfallData.getMaxTotalRainfall();

        g.setFill(Color.BLUE);
        g.setLineWidth(0.5);

        for (MonthRainfallData monthData : rainfallData.getRainfallData()) {
            // Get the height of the column relative to the maximum height
            double columnHeight = (monthData.getTotal() / maxMonthlyTotal) * yAxisLength;

            // Draw the rectangle representing the rainfall and draw a black outline
            g.fillRect(currentXPos, height - border_width - columnHeight, barWidth, columnHeight);
            g.strokeRect(currentXPos, height - border_width - columnHeight, barWidth, columnHeight);

            currentXPos += barWidth;
        }

        // Add a title and axis names
        g.setFill(Color.BLACK);
        g.setFont(Font.font(24));
        g.fillText("Analysed Rainfall", width/2.5, border_width);

        g.setFont(Font.font(15));
        g.fillText("Months", width/2.0, height-5);

        g.rotate(-90);
        g.fillText("Rainfall (millimeters)",-height/1.6, border_width-5);
    } // end drawPicture()


    //------ Implementation details: DO NOT EDIT THIS ------
    public void start(Stage stage) {
        int width = 200 * 6 + 40;
        int height = 500;
        Canvas canvas = new Canvas(width, height);
        drawPicture(canvas.getGraphicsContext2D(), width, height);
        BorderPane root = new BorderPane(canvas);
        root.setStyle("-fx-border-width: 4px; -fx-border-color: #444");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Rainfall Visualiser");
        stage.show();
        stage.setResizable(false);
    }
    //------ End of Implementation details ------


    public static void main(String[] args) {
        // Used for testing
//        var path = "src/main/resources/betaversion/cp2406assignment_beta/MountSheridanStationCNS.csv";
        var path = "src/main/resources/betaversion/cp2406assignment_beta/IDCJAC0009_031205_1800_Data.csv";
        launch();
    }

} // end SimpleGraphicsStarter
