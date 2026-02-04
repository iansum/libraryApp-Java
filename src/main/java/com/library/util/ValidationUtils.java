package com.library.util;

import java.util.regex.Pattern;

/**
 * Utility class for validation operations
 */
public class ValidationUtils {
    private static final Pattern ISBN_PATTERN = Pattern.compile("^(?:\\d{10}|\\d{13})$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern MEMBER_ID_PATTERN = Pattern.compile("^M\\d{3,}$");

    /**
     * Validate ISBN format (10 or 13 digits)
     */
    public static boolean isValidISBN(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return false;
        }
        return ISBN_PATTERN.matcher(isbn.trim()).matches();
    }

    /**
     * Validate email format
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Validate member ID format (M followed by at least 3 digits)
     */
    public static boolean isValidMemberId(String memberId) {
        if (memberId == null || memberId.trim().isEmpty()) {
            return false;
        }
        return MEMBER_ID_PATTERN.matcher(memberId.trim()).matches();
    }
}
