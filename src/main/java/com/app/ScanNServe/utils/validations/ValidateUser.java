package com.app.ScanNServe.utils.validations;

import java.util.regex.Pattern;

public class ValidateUser {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");

    private static final Pattern NAME_PATTERN =
            Pattern.compile("^[A-Za-z ]{2,}$");

    private static final Pattern CONTACT_PATTERN =
            Pattern.compile("^\\d{10}$");

    public static Boolean validate(
            String email,
            String password,
            String confirmPassword,
            String username,
            String role,
            String contactNumber
    ) {

        // Email validation
        if (email == null || email.isBlank() || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email address");
        }

        // Password validation
        if (password == null || password.isBlank()
                || !PASSWORD_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException(
                    "Password must be at least 8 characters long and include uppercase, lowercase, number, and special character"
            );
        }

        // Confirm password validation
        if (confirmPassword == null || !password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        // Username validation
        if (username == null || username.isBlank() || !NAME_PATTERN.matcher(username).matches()) {
            throw new IllegalArgumentException("Username must contain only alphabets and be at least 2 characters long");
        }

        // Role validation
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("Role cannot be null or empty");
        }

        // Contact number validation (optional)
        if (contactNumber != null && !contactNumber.isBlank()
                && !CONTACT_PATTERN.matcher(contactNumber).matches()) {
            throw new IllegalArgumentException("Contact number must be a valid 10-digit number");
        }

        return true;
    }
}
