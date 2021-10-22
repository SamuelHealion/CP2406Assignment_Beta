package betaversion.cp2406assignment_beta;

import java.util.ArrayList;

/**
 * CP2406 Assignment - Samuel Healion
 * RainfallDataSet is used as an array of MonthDataSet objects.
 * Includes a number of methods to analyse the full data set.
 */
public class RainfallDataSet {

    private final ArrayList<MonthDataSet> totalDataSet = new ArrayList<>();

    public void addDataSet(double newTotal, double newMin, double newMax, int newMonth, int newYear) {
        MonthDataSet newDataSet = new MonthDataSet();
        newDataSet.setRainfallData(newTotal, newMin, newMax, newMonth, newYear);
        totalDataSet.add(newDataSet);
    }

    public int getNumberOfMonths() {
        return totalDataSet.size();
    }

    public String getDateRange() {
        int firstMonth = 13;
        int lastMonth = 0;
        int firstYear = 9999;
        int lastYear = 0;

        for (MonthDataSet monthDataSet : totalDataSet) {
            if (monthDataSet.getYear() < firstYear) {
                firstYear = monthDataSet.getYear();
                if (monthDataSet.getMonth() < firstMonth)
                    firstMonth = monthDataSet.getMonth();
            }
            else if (monthDataSet.getYear() > lastYear) {
                lastYear = monthDataSet.getYear();
                if (monthDataSet.getMonth() > lastMonth)
                    lastMonth = monthDataSet.getMonth();
            }
        }
        return ("There is rainfall data from " + firstMonth + "/" + firstYear + " to " + lastMonth + "/" + lastYear);
    }

    public double getMaxTotalRainfall() {
        double maxTotalRainfall = Double.NEGATIVE_INFINITY;

        for (MonthDataSet monthDataSet : totalDataSet) {
            if (monthDataSet.getTotal() > maxTotalRainfall)
                maxTotalRainfall = monthDataSet.getTotal();
        }
        return maxTotalRainfall;
    }

    public double getMinRainfall() {
        double minRainfall = Double.POSITIVE_INFINITY;

        for (MonthDataSet monthDataSet : totalDataSet) {
            if (monthDataSet.getMin() < minRainfall)
                minRainfall = monthDataSet.getMin();
        }
        return minRainfall;
    }

    public double getMaxRainfall() {
        double maxRainfall = Double.NEGATIVE_INFINITY;

        for (MonthDataSet monthDataSet : totalDataSet) {
            if (monthDataSet.getMax() > maxRainfall)
                maxRainfall = monthDataSet.getMax();
        }
        return maxRainfall;
    }
}
