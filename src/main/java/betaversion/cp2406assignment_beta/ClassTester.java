package betaversion.cp2406assignment_beta;

import java.io.IOException;

/**
 * Class used to test the basic classes that are used in the Main program.
 */
public class ClassTester {
    public static void main(String[] args) {

        // Tests for MonthRainfallData class
        System.out.println("\nTests for MonthRainfallData class");
        MonthRainfallData newMonth = new MonthRainfallData(55.8,0,22,4,2020);
        MonthRainfallData extraMonth = new MonthRainfallData(595, 2, 200,5,2020);

        System.out.println(newMonth);
        System.out.println(extraMonth);


        // Tests for RainfallData class
        System.out.println("\nTests for RainfallData class");
        RainfallData newRainfallData = new RainfallData();
        newRainfallData.addRainfallData(55.8, 0, 22,4,2020);
        newRainfallData.addRainfallData(595, 2, 200,5,2020);

        System.out.println("There are " + newRainfallData.getNumberOfMonths() + " months of rainfall data");
        System.out.println(newRainfallData.getDateRange());

        System.out.println("The most rainfall in a month was " + newRainfallData.getMaxTotalRainfall() + " millimeters");
        System.out.println("The least rainfall in a single day was " + newRainfallData.getMinRainfall() + " millimeters");
        System.out.println("The most rainfall in a single day was " + newRainfallData.getMaxRainfall() + " millimeters");


        // Tests for RainfallAnalyser class
        System.out.println("\nTests for RainfallAnalyser class");
        RainfallAnalyser rainfallAnalyser = new RainfallAnalyser();
        RainfallData testRainfallData = new RainfallData();
        try {
            testRainfallData = rainfallAnalyser.analyseRainfallData("src/main/resources/betaversion/cp2406assignment_beta/rainfalldata/IDCJAC0009_031205_1800_Data.csv");
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
        } catch (Exception err) {
            System.out.println("There was an issue");
            System.out.println(err.getMessage());
        }

//        for (MonthRainfallData monthData: testRainfallData.getRainfallData())
//            System.out.println(monthData.getDate() + " had " + monthData.getTotal() + " millimeters of rain");

        System.out.println(testRainfallData.getDateRange());
        System.out.println("There are " + testRainfallData.getNumberOfMonths() + " months of data");
    }
}
