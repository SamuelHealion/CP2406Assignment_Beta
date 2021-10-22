package betaversion.cp2406assignment_beta;

public class ClassTester {
    public static void main(String[] args) {

        // Tests for MonthDataSet class
        MonthDataSet newMonth = new MonthDataSet();
        newMonth.setTotal(55.8);
        newMonth.setMax(22);
        newMonth.setMin(0);
        newMonth.setMonth(4);
        newMonth.setYear(2020);

        MonthDataSet extraMonth = new MonthDataSet();
        extraMonth.setRainfallData(595, 0, 200,5,2020);

        System.out.println(newMonth.printTotal());
        System.out.println(extraMonth.printTotal());
    }
}
