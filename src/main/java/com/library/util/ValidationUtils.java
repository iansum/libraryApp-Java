package com.library.util;

import java.util.regex.Pattern;

/**
 * Utility class for input validation.
 */
public class ValidationUtils {
    // ISBN can be ISBN-10 or ISBN-13 format
    private static final Pattern ISBN_PATTERN = Pattern.compile("^(?:\\d{10}|\\d{13})$");
    
    // Simple email validation pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    // Member ID format: alphanumeric, 3-20 characters
    private static final Pattern MEMBER_ID_PATTERN = Pattern.compile("^[A-Za-z0-9]{3,20}$");

    /**
     * Validate ISBN format.
     */
    public static boolean isValidISBN(String isbn) {
        return isbn != null && ISBN_PATTERN.matcher(isbn).matches();
    }

    /**
     * Validate email format.
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validate member ID format.
     */
    public static boolean isValidMemberId(String memberId) {
        return memberId != null && MEMBER_ID_PATTERN.matcher(memberId).matches();
    }
}
