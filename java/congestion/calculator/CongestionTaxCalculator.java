package congestion.calculator;

import congestion.calculator.util.DateUtils;
import congestion.calculator.util.VehicleUtils;
import congestion.calculator.vo.Vehicle;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class CongestionTaxCalculator {

    private static Map<String, Integer> tollFreeVehicles = new HashMap<>();

    static {
        tollFreeVehicles.put("Motorcycle", 0);
        tollFreeVehicles.put("Tractor", 1);
        tollFreeVehicles.put("Emergency", 2);
        tollFreeVehicles.put("Diplomat", 3);
        tollFreeVehicles.put("Foreign", 4);
        tollFreeVehicles.put("Military", 5);
    }
    
    public int getTax(Vehicle vehicle, Date[] dates)
    {
        //先对dates排序
        DateUtils.sort(dates);

        int totalFee = 0;
        Date intervalStart = dates[0];

        for (int i = 0; i < dates.length ; i++) {
            Date date = dates[i];
            int tempFee = GetTollFee(intervalStart, vehicle);
            int nextFee = GetTollFee(date, vehicle);

            long diffInMillies = date.getTime() - intervalStart.getTime();
            long minutes = diffInMillies/1000/60;

            /**
             * 如果间隔小于60，那么先减去tempFee
             * 再加上其中较大的那个
             */
            if (minutes <= 60)
            {
                if (totalFee > 0) totalFee -= tempFee;
                if (nextFee >= tempFee) tempFee = nextFee;
                totalFee += tempFee;
            }
            else
            {
                /**
                 *  主要是看如何理解60分钟只收费一次
                 *  例如12：00，12：30，12：40，13：10过了4次
                 *  目前理解就是12:00、12：30、12：40中收贵的一次，13：10收一次
                 *  不去考虑12：00和12：30，12：40和13：10这种可能收费更多的情况
                 *  解决方案就是重置intervalStart??
                 */
                intervalStart = date;
                totalFee += nextFee;
            }
        }                
      
        if (totalFee > 60) totalFee = 60;
        return totalFee;
    }

    /*private boolean IsTollFreeVehicle(Vehicle vehicle) {
        if (vehicle == null) return false;
        String vehicleType = vehicle.getVehicleType();
        return tollFreeVehicles.containsKey(vehicleType);
    }*/

    public int GetTollFee(Date date, Vehicle vehicle)
    {
        if (DateUtils.isTollFreeDate(date) || VehicleUtils.isTollFreeVehicle(vehicle))
            return 0;

        //int hour = date.getHours();
        //int minute = date.getMinutes();

        /*if (hour == 6 && minute >= 0 && minute <= 29) return 8;
        else if (hour == 6 && minute >= 30 && minute <= 59) return 13;
        else if (hour == 7 && minute >= 0 && minute <= 59) return 18;
        else if (hour == 8 && minute >= 0 && minute <= 29) return 13;
        //这里原来代码有问题
        else if (hour == 8 && minute >= 30 && minute <= 59) return 8;
        else if (hour >= 9 && hour <= 14) return 8;
        else if (hour == 15 && minute >= 0 && minute <= 29) return 13;
        else if (hour == 15 && minute >= 30 || hour == 16 && minute <= 59) return 18;
        else if (hour == 17 && minute >= 0 && minute <= 59) return 13;
        else if (hour == 18 && minute >= 0 && minute <= 29) return 8;
        else return 0;*/

        Set<String> times = Constants.congestionTax.keySet();

        for(String str: times){
            if(this.includes(str,date)){
                String result = Constants.congestionTax.get(str);
                return Integer.valueOf(result);
            }
        }

        return 0;

    }

    private Boolean IsTollFreeDate(Date date)
    {
        int year = date.getYear();
        int month = date.getMonth() + 1;
        int day = date.getDay() + 1;
        int dayOfMonth = date.getDate();

        if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) return true;

        if (year == 2013)
        {
            if ((month == 1 && dayOfMonth == 1) ||
                    (month == 3 && (dayOfMonth == 28 || dayOfMonth == 29)) ||
                    (month == 4 && (dayOfMonth == 1 || dayOfMonth == 30)) ||
                    (month == 5 && (dayOfMonth == 1 || dayOfMonth == 8 || dayOfMonth == 9)) ||
                    (month == 6 && (dayOfMonth == 5 || dayOfMonth == 6 || dayOfMonth == 21)) ||
                    (month == 7) ||
                    (month == 11 && dayOfMonth == 1) ||
                    (month == 12 && (dayOfMonth == 24 || dayOfMonth == 25 || dayOfMonth == 26 || dayOfMonth == 31)))
            {
                return true;
            }
        }
        return false;
    }

    private boolean includes(String key, Date date){
        int hour = date.getHours();
        int minute = date.getMinutes();

        String hourStr = hour>=10?(""+hour):"0"+hour;
        String minuteStr = minute>=10?(""+minute):"0"+minute;

        String time = hourStr+minuteStr;

        return this.includes(key,time);
    }

    /**
     * @param key
     * @param time
     * @return
     *
     * key的格式为  0800T0900
     * time的格式为 0830
     */
    private boolean includes(String key, String time){

        String[] keys = key.split("T");
        String start = keys[0];
        String end = keys[1];

        Integer startNum = Integer.valueOf(start);
        Integer endNum = Integer.valueOf(end);

        Integer timeNum = Integer.valueOf(time);

        if(timeNum >= startNum && timeNum <= endNum){
            return true;
        }

        return false;
    }
}
