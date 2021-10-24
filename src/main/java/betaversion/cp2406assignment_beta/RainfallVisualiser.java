package betaversion.cp2406assignment_beta;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * This file can be used to draw a chart that effectively represents rainfall data.  Just fill in
 * the definition of drawPicture with the code that draws your picture.
 */
public class RainfallVisualiser {

    public static Scene getScene(RainfallData rainfallData) {

        for (MonthRainfallData monthRainfallData : rainfallData.getRainfallData())
            System.out.println(monthRainfallData.getTotal());

        int width = 200 * 6 + 40;
        int height = 500;
        Canvas canvas = new Canvas(width, height);
        drawPicture(canvas.getGraphicsContext2D(), width, height, rainfallData);
        BorderPane root = new BorderPane(canvas);
        root.setStyle("-fx-border-width: 4px; -fx-border-color: #444");

        return new Scene(root);
    }

    /**
     * Draws a picture.  The parameters width and height give the size
     * of the drawing area, in pixels.
     */
    public static void drawPicture(GraphicsContext g, int width, int height, RainfallData rainfallData) {

        // Create the x-axis and y-axis
        int border_width = 20;
        g.setStroke(Color.BLACK);
        g.setLineWidth(2);
        g.strokeLine(border_width, border_width, border_width, height - border_width);
        g.strokeLine(border_width, height - border_width, width - border_width, height - border_width);

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

            System.out.println(monthData.getTotal());
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


} // end RainfallVisualiser
