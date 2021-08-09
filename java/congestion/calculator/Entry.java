package congestion.calculator;

import congestion.calculator.vo.Emergency;
import congestion.calculator.vo.Car;
import congestion.calculator.vo.Vehicle;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class Entry {
    public static void main(String[] args) {
        Vehicle v = new Emergency();
        Vehicle v2 = new Car();

        CongestionTaxCalculator calculator = new CongestionTaxCalculator();

        Date[] dates = new Date[5];
        Date date0 = new Date(2013-1900,1,8,8,01,00);
        dates[0] = date0;

        Date date1 = new Date(2013-1900,1,8,7,00,00);
        dates[1] = date1;

        Date date2 = new Date(2013-1900,1,8,7,30,00);
        dates[2] = date2;

        Date date3 = new Date(2013-1900,1,7,6,23,00);
        dates[3] = date3;

        Date date4 = new Date(2013-1900,1,7,15,27,00);
        dates[4] = date4;

        int tax = calculator.getTax(v2, dates);

        System.out.println(tax);

    }
}
