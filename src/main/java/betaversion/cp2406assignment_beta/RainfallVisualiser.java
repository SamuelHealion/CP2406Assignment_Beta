package betaversion.cp2406assignment_beta;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.*;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * This file can be used to draw a chart that effectively represents rainfall data.  Just fill in
 * the definition of drawPicture with the code that draws your picture.
 */
public class RainfallVisualiser {

    public static StackedBarChart<String, Number> getRainfallChart(RainfallData rainfallData) {

        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Dates");
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Rainfall (millimeters)");

        StackedBarChart<String, Number> rainfallChart = new StackedBarChart<>(xAxis, yAxis);
        XYChart.Series<String, Number> totals = new XYChart.Series<>();
        XYChart.Series<String, Number> mins = new XYChart.Series<>();
        XYChart.Series<String, Number> maxs = new XYChart.Series<>();

        rainfallChart.setTitle(rainfallData.getDateRange());
        rainfallChart.setCategoryGap(0.0);
        rainfallChart.setLegendVisible(false);
        rainfallChart.setOnMouseDragOver((MouseEvent e) -> System.out.println("Dragged over"));

        for (MonthRainfallData monthRainfallData: rainfallData.getRainfallData()) {

            double minMaxDiff = monthRainfallData.getMax() - monthRainfallData.getMin();
            double totalDiff = monthRainfallData.getTotal() - minMaxDiff;

            mins.getData().add(new XYChart.Data<>(monthRainfallData.getDate(), monthRainfallData.getMin()));
            maxs.getData().add(new XYChart.Data<>(monthRainfallData.getDate(), minMaxDiff));
            totals.getData().add(new XYChart.Data<>(monthRainfallData.getDate(), totalDiff));
        }

        rainfallChart.getData().addAll(mins, maxs, totals);

        double[] minValues = new double[rainfallData.getNumberOfMonths()];
        int i;
        for (XYChart.Series<String, Number> newSeries: rainfallChart.getData()) {
            i = 0;
            for (XYChart.Data<String, Number> item : newSeries.getData()) {
                Tooltip.install(item.getNode(), new Tooltip((item.getYValue().doubleValue() + minValues[i])+""));
                minValues[i] += item.getYValue().doubleValue();
                i++;
            }
        }

        return rainfallChart;
    }

} // end RainfallVisualiser
