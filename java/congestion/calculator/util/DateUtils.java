package congestion.calculator.util;

import java.util.*;

public class DateUtils {
    //这个没用到
    public static void sort2(Date [] dates){
        List<Long> longs = new ArrayList<>(dates.length);

        for(int i=0;i<dates.length;i++){
            Date date = dates[i];
            Long time = date.getTime();
            longs.add(time);
        }

        Collections.sort(longs);

        for (int i = 0; i < longs.size(); i++) {
            Long time2 = longs.get(i);
            dates[i] = new Date(time2);
        }
    }

    public static void sort(Date [] dates){
        List<Date> dateList = Arrays.asList(dates);

        dateList.sort((o1,o2)->o1.compareTo(o2));

        for (int i = 0; i < dateList.size(); i++) {
            dates[i] = dateList.get(i);
        }
    }


    public static Boolean isTollFreeDate(Date date)
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
}
