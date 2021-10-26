package betaversion.cp2406assignment_beta;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * CP2406 Assignment - Samuel Healion
 * Beta version of the Rainfall Analyser
 * Makes use of the RainfallDataSet class and the CSV package from Apache
 */
public class RainfallAnalyser {

    public RainfallData analyseRainfallData(String path) throws IOException, NumberFormatException {
        RainfallData newRainfallData = new RainfallData();
        newRainfallData.setFilename(path);
        Reader reader = new FileReader(path);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(reader);
        int year, month, day;
        double rainfall;
        int currentYear = 0;
        int currentMonth = 1;
        double monthlyTotal = 0.0;
        double minRainfall = Double.POSITIVE_INFINITY;
        double maxRainfall = 0.0;
        boolean startReadingData = false;


        for (CSVRecord record : records) {
            // Get the data from specific rows of the Rainfall Data CSV file
            String yearText = record.get("Year");
            String monthText = record.get("Month");
            String dayText = record.get("Day");
            String rainfallText = record.get("Rainfall amount (millimetres)");

            // Check if there is data for rainfall
            if (rainfallText.isEmpty()) {
                continue; // If the entry is empty, ignore it and move to the next entry
            }
            else {
                rainfall = Double.parseDouble(rainfallText);

                // Analyse the data and convert it into the expected output format
                // then check if the recorded date is valid
                year = Integer.parseInt(yearText);
                month = Integer.parseInt(monthText);
                day = Integer.parseInt(dayText);
                if ((month < 1 || month > 12) || (day < 1 || day > 31)) {
                    throw new NumberFormatException("Date outside of expected range");
                }
            }

            // Check to see if it's the next month
            if (month != currentMonth) {
                // Ignore any initial null values for rainfall
                if (!startReadingData) {
                    startReadingData = true;
                } else {
                    newRainfallData.addRainfallData(monthlyTotal, minRainfall, maxRainfall, currentMonth, currentYear == 0 ? year : currentYear);
                }
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
        newRainfallData.addRainfallData(monthlyTotal, minRainfall, maxRainfall, currentMonth, currentYear);

        return newRainfallData;

    } // end analyseRainfallData()


    /**
     * Creates a RainfallData object from a previously analysed file
     */
    public RainfallData getAnalysedRainfallData(String path) throws IOException {
        RainfallData rainfallData = new RainfallData();
        rainfallData.setFilename(path);
        Reader reader = new FileReader(path);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(reader);

        for (CSVRecord record: records) {

            int year = Integer.parseInt(record.get("year"));
            int month = Integer.parseInt(record.get("month"));
            double total = Double.parseDouble(record.get("total"));
            double min = Double.parseDouble(record.get("minimum"));
            double max = Double.parseDouble(record.get("maximum"));

            rainfallData.addRainfallData(total, min, max, month, year);
        }
        return rainfallData;
    }

    public void saveRainfallData(RainfallData rainfallData) {
        if (rainfallData.getFilename() == null) {
            System.out.println("No file loaded to save");
            return;
        }
        String savePath = "src/main/resources/betaversion/cp2406assignment_beta/analysedrainfalldata/" + rainfallData.getFilename() + "_Analysed";
        TextIO.writeFile(savePath);
        TextIO.putln("year,month,total,minimum,maximum");
        for (MonthRainfallData monthRainfallData : rainfallData.getRainfallData()) {
            TextIO.putf("%d,%d,%1.2f,%1.2f,%1.2f\n",
                    monthRainfallData.getYear(), monthRainfallData.getMonth(), monthRainfallData.getTotal(),
                    monthRainfallData.getMin(), monthRainfallData.getMax());
        }
        System.out.println("Successfully saved rainfall data.");
    }



} // end RainfallAnalyser
