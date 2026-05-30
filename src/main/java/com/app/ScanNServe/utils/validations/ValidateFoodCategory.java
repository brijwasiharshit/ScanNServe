package com.app.ScanNServe.utils.validations;

public class ValidateFoodCategory {

    private static final int NAME_MAX_LENGTH = 100;

    public static void validateCategoryName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Food category name cannot be empty");
        }

        String trimmed = name.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Food category name cannot be empty");
        }

        if (trimmed.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("Food category name cannot exceed " + NAME_MAX_LENGTH + " characters");
        }
    }
}
