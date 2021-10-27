package betaversion.cp2406assignment_beta;

import java.io.File;
import java.util.ArrayList;

/**
 * CP2406 Assignment - Samuel Healion
 * RainfallData is used as an array of MonthRainfallData objects.
 * Includes methods to analyse the full rainfall data.
 */
public class RainfallData {

    private final ArrayList<MonthRainfallData> totalRainfallData = new ArrayList<>();
    private String filename = null;

    /**
     * Add a single months' rainfall data to the array created by the RainfallData Object.
     * This class has no other setter methods, so any rainfall data added to a RainfallData
     * object must contain all constructor values.
     */
    public void addRainfallData(double newTotal, double newMin, double newMax, int newMonth, int newYear) {
        MonthRainfallData newData = new MonthRainfallData(newTotal, newMin, newMax, newMonth, newYear);
        totalRainfallData.add(newData);
    }

    /**
     * Return the private variable totalRainfallData.
     * Used to iterate through the data of a RainfallData object.
     */
    public ArrayList<MonthRainfallData> getRainfallData() {
        return totalRainfallData;
    }

    /**
     * Get the number of entries recorded in totalRainfallData.
     */
    public int getNumberOfMonths() {
        return totalRainfallData.size();
    }

    /**
     * Get the date range of a RainfallData object.
     * Sets the earliest month and last month to an arbitrary value then calculates the starting month/year
     * and ending month/year
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
        return ("Rainfall data from " + firstMonth + "/" + firstYear + " to " + lastMonth + "/" + lastYear);

    } // end getDateRange()

    /**
     * Get the maximum total recorded rainfall over the entire recorded data.
     */
    public double getMaxTotalRainfall() {
        double maxTotalRainfall = Double.NEGATIVE_INFINITY;

        for (MonthRainfallData monthRainfallData : totalRainfallData) {
            if (monthRainfallData.getTotal() > maxTotalRainfall)
                maxTotalRainfall = monthRainfallData.getTotal();
        }
        return maxTotalRainfall;
    }

    /**
     * Get the lowest recorded rainfall for a single month.
     */
    public double getMinRainfall() {
        double minRainfall = Double.POSITIVE_INFINITY;

        for (MonthRainfallData monthRainfallData : totalRainfallData) {
            if (monthRainfallData.getMin() < minRainfall)
                minRainfall = monthRainfallData.getMin();
        }
        return minRainfall;
    }

    /**
     * Get the highest recorded rainfall for a single month.
     */
    public double getMaxRainfall() {
        double maxRainfall = Double.NEGATIVE_INFINITY;

        for (MonthRainfallData monthRainfallData : totalRainfallData) {
            if (monthRainfallData.getMax() > maxRainfall)
                maxRainfall = monthRainfallData.getMax();
        }
        return maxRainfall;
    }

    /**
     * Assigns a filename to the RainfallData object.
     */
    public void setFilename(String path) {
        File file = new File(path);
        String[] filenameElements = file.getName().trim().split("\\.");
        filename = filenameElements[0];
    }

    /**
     * Get the assigned filename for the RainfallData object.
     */
    public String getFilename() { return filename; }

} // End RainfallData
