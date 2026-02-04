package com.library.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Utility class for date operations
 */
public class DateUtils {
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Get current date and time
     */
    public static Date getCurrentDate() {
        return new Date();
    }
    
    /**
     * Add days to a given date
     */
    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }
    
    /**
     * Calculate days between two dates
     */
    public static long calculateDaysBetween(Date start, Date end) {
        long diffInMillis = end.getTime() - start.getTime();
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }
    
    /**
     * Format date for display
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return "N/A";
        }
        return DATE_FORMAT.format(date);
    }
}
