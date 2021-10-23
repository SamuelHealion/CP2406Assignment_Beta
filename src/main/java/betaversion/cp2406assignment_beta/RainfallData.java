package betaversion.cp2406assignment_beta;

import java.util.ArrayList;

/**
 * CP2406 Assignment - Samuel Healion
 * RainfallData is used as an array of MonthRainfallData objects.
 * Includes a number of methods to analyse the full data set.
 */
public class RainfallData {

    private final ArrayList<MonthRainfallData> totalRainfallData = new ArrayList<>();

    public void addDataSet(double newTotal, double newMin, double newMax, int newMonth, int newYear) {
        MonthRainfallData newData = new MonthRainfallData(newTotal, newMin, newMax, newMonth, newYear);
        totalRainfallData.add(newData);
    }

    /**
     * Used to iterate through the array
     */
    public ArrayList<MonthRainfallData> getRainfallData() {
        return totalRainfallData;
    }

    public int getNumberOfMonths() {
        return totalRainfallData.size();
    }

    /**
     * Set the earliest month and last month to an arbitrary value and calculate the starting month/year
     * and ending month/year
     * @return String containing the date range
     */
    public String getDateRange() {
        int firstMonth = 13;
        int lastMonth = 0;
        int firstYear = 9999;
        int lastYear = 0;

        for (MonthRainfallData monthRainfallData : totalRainfallData) {
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

        for (MonthRainfallData monthRainfallData : totalRainfallData) {
            if (monthRainfallData.getTotal() > maxTotalRainfall)
                maxTotalRainfall = monthRainfallData.getTotal();
        }
        return maxTotalRainfall;
    }

    public double getMinRainfall() {
        double minRainfall = Double.POSITIVE_INFINITY;

        for (MonthRainfallData monthRainfallData : totalRainfallData) {
            if (monthRainfallData.getMin() < minRainfall)
                minRainfall = monthRainfallData.getMin();
        }
        return minRainfall;
    }

    public double getMaxRainfall() {
        double maxRainfall = Double.NEGATIVE_INFINITY;

        for (MonthRainfallData monthRainfallData : totalRainfallData) {
            if (monthRainfallData.getMax() > maxRainfall)
                maxRainfall = monthRainfallData.getMax();
        }
        return maxRainfall;
    }
}
