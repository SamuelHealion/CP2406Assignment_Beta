package alphaversion.cp2406assignment_alpha;

import org.apache.commons.csv.*;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Objects;

/**
 * CP2406 Assignment - Samuel Healion
 * Alpha Version
 * This program will get rainfall data from a file that the user specifies
 * and return the analysed data in a format for a visualiser.
 * This version can only analyse files stored in the rainfalldata directory of the project.
 */
public class RainfallAnalyser {

    public static void main(String[] args) {

        System.out.println("Welcome to the Rainfall Analyser");
        System.out.println("This program analyses the rainfall data given from various sources like BOM");
        System.out.println("It will then return the extracted Total Monthly Rainfall for the data set");
        System.out.println("as well as the minimum and maximum rainfall that occurred that month.");

        try {
            String filename = getFilename();
            analyseRainfallData(filename);
            System.out.println(filename + " successfully analysed!");
        } catch (Exception e) {
            System.out.println("Error: there was an issue");
            System.out.println(e.getMessage());
        }
    }

    private static String getSavePath(String filename) {
        String[] filenameElements = filename.trim().split("\\.");
        return "./rainfalldata_analysed/" + filenameElements[0] + "_analysed.csv";
    }

    private static void analyseRainfallData(String fileName) throws Exception {

        Reader reader = new FileReader("./rainfalldata/" + fileName);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(reader);

        // Set the output file based on the file being analysed
        alphaversion.cp2406assignment_alpha.TextIO.writeFile(getSavePath(fileName));
        alphaversion.cp2406assignment_alpha.TextIO.putln("year,month,total,minimum,maximum");

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
                return;
            }

            // Check if there is data for rainfall, otherwise assume zero
            rainfall = Objects.equals(rainfallText, "") ? 0 : Double.parseDouble(rainfallText);

            // Check to see if it's the next month
            if (month != currentMonth) {
                writeCurrentData(monthlyTotal, minRainfall, maxRainfall, currentMonth, currentYear == 0? year : currentYear);
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
        writeCurrentData(monthlyTotal, minRainfall, maxRainfall, currentMonth, currentYear);
    }

    private static void writeCurrentData(double monthlyTotal, double minRainfall, double maxRainfall, int month, int year) {
        alphaversion.cp2406assignment_alpha.TextIO.putf("%d,%d,%1.2f,%1.2f,%1.2f\n", year, month, monthlyTotal, minRainfall, maxRainfall);
    }

    /**
     * Get a list of available rainfall data sets to be analysed.
     * Allows the user to pick which data set to analyse from this list.
     * @return Name of the file to be analysed
     */
    private static String getFilename() {
        System.out.println("The files available are:\n");
        File f = new File("./rainfalldata");
        String[] pathNames = f.list();

        assert pathNames != null;
        for (int i = 0; i < pathNames.length; i++) {
            System.out.println((i+1) + ": " + pathNames[i]);
        }

        System.out.println("\nWhich file would you like to analyse? Enter the corresponding number");

        int fileNumber;
        String filename;
        while (true) {
            // Check that the selected file is valid. TextIO handles a non-Int input
            try {
                fileNumber = alphaversion.cp2406assignment_alpha.TextIO.getInt();
                filename = pathNames[fileNumber - 1];
                break;
            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("That is outside of the range of available data files to analyse.");
                System.out.println("Please choose another one");
            }
        }
        return filename;
    } // End getFilename
}

