package betaversion.cp2406assignment_beta;

import java.util.ArrayList;

/**
 * CP2406 Assignment - Samuel Healion
 * RainfallDataSet is used as an array of MonthDataSet objects.
 * Includes a number of methods to analyse the full data set.
 */
public class RainfallData {

    private final ArrayList<MonthRainfallData> totalDataSet = new ArrayList<>();

    public void addDataSet(double newTotal, double newMin, double newMax, int newMonth, int newYear) {
        MonthRainfallData newDataSet = new MonthRainfallData(newTotal, newMin, newMax, newMonth, newYear);
        totalDataSet.add(newDataSet);
    }

    public ArrayList<MonthRainfallData> getRainfallData() {
        return totalDataSet;
    }

    public int getNumberOfMonths() {
        return totalDataSet.size();
    }

    public String getDateRange() {
        int firstMonth = 13;
        int lastMonth = 0;
        int firstYear = 9999;
        int lastYear = 0;

        for (MonthRainfallData monthRainfallData : totalDataSet) {
            if (monthRainfallData.getYear() < firstYear) {
                firstYear = monthRainfallData.getYear();
                if (monthRainfallData.getMonth() < firstMonth)
                    firstMonth = monthRainfallData.getMonth();
            }
            else if (monthRainfallData.getYear() > lastYear) {
                lastYear = monthRainfallData.getYear();
                if (monthRainfallData.getMonth() > lastMonth)
                    lastMonth = monthRainfallData.getMonth();
            }
        }
        return ("There is rainfall data from " + firstMonth + "/" + firstYear + " to " + lastMonth + "/" + lastYear);
    }

    public double getMaxTotalRainfall() {
        double maxTotalRainfall = Double.NEGATIVE_INFINITY;

        for (MonthRainfallData monthRainfallData : totalDataSet) {
            if (monthRainfallData.getTotal() > maxTotalRainfall)
                maxTotalRainfall = monthRainfallData.getTotal();
        }
        return maxTotalRainfall;
    }

    public double getMinRainfall() {
        double minRainfall = Double.POSITIVE_INFINITY;

        for (MonthRainfallData monthRainfallData : totalDataSet) {
            if (monthRainfallData.getMin() < minRainfall)
                minRainfall = monthRainfallData.getMin();
        }
        return minRainfall;
    }

    public double getMaxRainfall() {
        double maxRainfall = Double.NEGATIVE_INFINITY;

        for (MonthRainfallData monthRainfallData : totalDataSet) {
            if (monthRainfallData.getMax() > maxRainfall)
                maxRainfall = monthRainfallData.getMax();
        }
        return maxRainfall;
    }
}
