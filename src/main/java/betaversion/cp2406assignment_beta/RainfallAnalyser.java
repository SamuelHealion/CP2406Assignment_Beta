package betaversion.cp2406assignment_beta;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Objects;

/**
 * CP2406 Assignment - Samuel Healion
 * Beta version of the Rainfall Analyser
 * Makes use of the RainfallDataSet class and the CSV package from Apache
 */
public class RainfallAnalyser {

    public RainfallDataSet analyseDataSet(String path) throws IOException {
        RainfallDataSet newDataSet = new RainfallDataSet();

        Reader reader = new FileReader(path);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(reader);
        int year, month, day;
        double rainfall;
        int currentYear = 0;
        int currentMonth = 1;
        double monthlyTotal = 0.0;
        double minRainfall = Double.POSITIVE_INFINITY;
        double maxRainfall = 0.0;

        for (CSVRecord record : records) {
            // Get the data from specific rows of the Rainfall Data CSV file
            String yearText = record.get("Year");
            String monthText = record.get("Month");
            String dayText = record.get("Day");
            String rainfallText = record.get("Rainfall amount (millimetres)");

            // Analyse the data and convert it into the expected output format
            year = Integer.parseInt(yearText);
            month = Integer.parseInt(monthText);
            day = Integer.parseInt(dayText);

            // Check if the recorded date is valid
            if ((month < 1 || month > 12) || (day < 1 || day > 31)) {
                System.out.println("Error: Invalid format for dates");
                break;
            }

            // Check if there is data for rainfall, otherwise assume zero
            rainfall = Objects.equals(rainfallText, "") ? 0 : Double.parseDouble(rainfallText);

            // Check to see if it's the next month
            if (month != currentMonth) {
                newDataSet.addDataSet(monthlyTotal, minRainfall, maxRainfall, currentMonth, currentYear == 0? year : currentYear);
                currentYear = year;
                currentMonth = month;
                monthlyTotal = 0;
                maxRainfall = 0.0;
                minRainfall = Double.POSITIVE_INFINITY;
            }

            // Update the total for the month
            monthlyTotal += rainfall;
            if (rainfall > maxRainfall) maxRainfall = rainfall;
            if (rainfall < minRainfall) minRainfall = rainfall;
        }
        // Catch an incomplete month when exiting the for loop
        newDataSet.addDataSet(monthlyTotal, minRainfall, maxRainfall, currentMonth, currentYear);

        return newDataSet;
    }
}
