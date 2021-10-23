package betaversion.cp2406assignment_beta;

import java.io.IOException;

public class ClassTester {
    public static void main(String[] args) {

        // Tests for MonthDataSet class
        System.out.println("\nTests for MonthDataSet class");
        MonthRainfallData newMonth = new MonthRainfallData(55.8,0,22,4,2020);
        MonthRainfallData extraMonth = new MonthRainfallData(595, 2, 200,5,2020);

        System.out.println(newMonth);
        System.out.println(extraMonth);


        // Tests for RainfallDataSet class
        System.out.println("\nTests for RainfallDataSet class");
        RainfallData newRainfallData = new RainfallData();
        newRainfallData.addDataSet(55.8, 0, 22,4,2020);
        newRainfallData.addDataSet(595, 2, 200,5,2020);

        System.out.println("There are " + newRainfallData.getNumberOfMonths() + " months of rainfall data");
        System.out.println(newRainfallData.getDateRange());

        System.out.println("The most rainfall in a month was " + newRainfallData.getMaxTotalRainfall() + " millimeters");
        System.out.println("The least rainfall in a single day was " + newRainfallData.getMinRainfall() + " millimeters");
        System.out.println("The most rainfall in a single day was " + newRainfallData.getMaxRainfall() + " millimeters");


        // Tests for RainfallAnalyser class
        System.out.println("\nTests for RainfallAnalyser class");
        RainfallAnalyser analysedDataSet = new RainfallAnalyser();
        RainfallData testDataSet = new RainfallData();
        try {
            testDataSet = analysedDataSet.analyseDataSet("src/main/resources/betaversion/cp2406assignment_beta/MountSheridanStationCNS.csv");
            System.out.println("Successfully loaded the data");
        } catch (IOException err) {
            System.out.println("Something went wrong");
            System.out.println(err.getMessage());
        }

//        for (MonthDataSet monthData: testDataSet.getRainfallData())
//            System.out.println(monthData.printTotal());

        System.out.println(testDataSet.getDateRange());
        System.out.println(testDataSet.getNumberOfMonths());
    }
}
