package com.library.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Utility class for date operations.
 */
public class DateUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Get the current date.
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    /**
     * Add days to a date.
     */
    public static LocalDate addDays(LocalDate date, int days) {
        return date.plusDays(days);
    }

    /**
     * Calculate days between two dates.
     */
    public static long calculateDaysBetween(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end);
    }

    /**
     * Format date for display.
     */
    public static String formatDate(LocalDate date) {
        return date.format(FORMATTER);
    }
}
