package com.language.service.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MyCalendar {
    private static final int WEEKEND_1 = Calendar.SATURDAY;
    private static final int WEEKEND_2 = Calendar.SUNDAY;
    private static Map<String, String> holidays = null;
    private static MyCalendar myCalendar = null;

    private MyCalendar() {
        holidays = new HashMap<String, String>();
        holidays.put("4,30", "Giải phóng");
        holidays.put("5,1", "Lao động");
        holidays.put("9,2", "Quốc khánh");
    }

    public static Date getPreviousWorkingDay(Date date) {

        Date previousWorkingDate = null;
        try {
            if (myCalendar == null) {
                myCalendar = new MyCalendar();
            }

            if (date != null) {
                Calendar calInstance = Calendar.getInstance();
                calInstance.setTime(date);
                int weekDay = 0;

                do {
                    calInstance.add(Calendar.DATE, -1);
                    weekDay = calInstance.get(Calendar.DAY_OF_WEEK);
                } while (weekDay == WEEKEND_1 || weekDay == WEEKEND_2
                        || holidays.get((calInstance.get(Calendar.MONTH) + 1) + "," + calInstance.get(Calendar.DATE)) != null);

                previousWorkingDate = calInstance.getTime();
            }

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return previousWorkingDate;
    }


    public static Date getFirstWorkingDayOfMonth() {
        Date firstWorkingDayOfMonth = null;
        Date dtNow = new Date();
        try {
            if (myCalendar == null) {
                myCalendar = new MyCalendar();
            }

            Calendar cal = Calendar.getInstance();
            cal.setTime(dtNow);
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
            int weekDay = cal.get(Calendar.DAY_OF_WEEK);
            while (weekDay == WEEKEND_1 || weekDay == WEEKEND_2
                    || holidays.get((cal.get(Calendar.MONTH) + 1) + "," + cal.get(Calendar.DATE)) != null) {
                cal.add(Calendar.DATE, 1);
                weekDay = cal.get(Calendar.DAY_OF_WEEK);
            }
            firstWorkingDayOfMonth = cal.getTime();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return firstWorkingDayOfMonth;
    }

}