package betaversion.cp2406assignment_beta;

import java.io.IOException;

public class ClassTester {
    public static void main(String[] args) {

        // Tests for MonthDataSet class
        System.out.println("\nTests for MonthDataSet class");
        MonthDataSet newMonth = new MonthDataSet();
        newMonth.setTotal(55.8);
        newMonth.setMin(0);
        newMonth.setMax(22);
        newMonth.setMonth(4);
        newMonth.setYear(2020);

        MonthDataSet extraMonth = new MonthDataSet();
        extraMonth.setRainfallData(595, 2, 200,5,2020);

        System.out.println(newMonth.printTotal());
        System.out.println(extraMonth.printTotal());


        // Tests for RainfallDataSet class
        System.out.println("\nTests for RainfallDataSet class");
        RainfallDataSet newRainfallData = new RainfallDataSet();
        newRainfallData.addDataSet(55.8, 0, 22,4,2020);
        newRainfallData.addDataSet(595, 2, 200,5,2020);

        System.out.println("There are " + newRainfallData.getNumberOfMonths() + " months of rainfall data");
        System.out.println(newRainfallData.getDateRange());

        System.out.println("The most rainfall in a month was " + newRainfallData.getMaxTotalRainfall() + " millimeters");
        System.out.println("The least rainfall in a single day was " + newRainfallData.getMinRainfall() + " millimeters");
        System.out.println("The most rainfall in a single day was " + newRainfallData.getMaxRainfall() + " millimeters");


        // Tests for RainfallAnalyser class
        RainfallAnalyser analysedDataSet = new RainfallAnalyser();
        RainfallDataSet testDataSet = new RainfallDataSet();
        try {
            testDataSet = analysedDataSet.analyseDataSet("src/main/resources/betaversion/cp2406assignment_beta/MountSheridanStationCNS.csv");
            System.out.println("It worked");
        } catch (IOException err) {
            System.out.println("Something went wrong");
            System.out.println(err.getMessage());
        }
        System.out.println(testDataSet);
    }
}
