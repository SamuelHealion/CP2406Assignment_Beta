package betaversion.cp2406assignment_beta;

/**
 * CP2406 Assignment - Samuel Healion
 * Basic class representing a single months' rainfall data.
 * Includes setter/getter methods for each variable
 */
public class MonthDataSet {

        private double total;
        private double min;
        private double max;
        private int month;
        private int year;

        public String printTotal() {
                return (month + "/" + year + " had " + total + " millimeters of rain");
        }

        public void setTotal(double newTotal) {
                total = newTotal;
        }

        public void setMin(double newMin) {
                min = newMin;
        }

        public void setMax(double newMax) {
                max = newMax;
        }

        public void setMonth(int newMonth) {
                month = newMonth;
        }

        public void setYear(int newYear) {
                year = newYear;
        }

        public double getTotal() {
                return total;
        }

        public double getMin() {
                return min;
        }

        public double getMax() {
                return max;
        }

        public int getMonth() {
                return month;
        }

        public int getYear() {
                return year;
        }

}
