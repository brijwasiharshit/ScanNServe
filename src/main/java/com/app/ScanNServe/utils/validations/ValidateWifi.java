package com.app.ScanNServe.utils.validations;

import java.util.regex.Pattern;

public class ValidateWifi {

    // At least 1 lowercase, 1 uppercase, 1 digit, 1 special char, length > 8
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{9,}$");

    // Basic SSID validation: 1-32 visible characters, no leading/trailing spaces
    private static final Pattern SSID_PATTERN =
            Pattern.compile("^[\\S ].{0,30}\\S$");

    public static boolean validatePassword(String password, String confirmPassword) {

        if (password == null || password.isBlank()
                || !PASSWORD_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException(
                    "Wifi password must be longer than 8 characters and include uppercase, lowercase, number, and special character"
            );
        }

        if (confirmPassword == null || !password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Wifi passwords do not match");
        }

        return true;
    }

    public static boolean validateSsid(String ssid) {
        if (ssid == null || ssid.isBlank()) {
            throw new IllegalArgumentException("Wifi SSID cannot be empty");
        }

        if (!SSID_PATTERN.matcher(ssid).matches()) {
            throw new IllegalArgumentException("Wifi SSID must be 1-32 characters and cannot start or end with space");
        }

        return true;
    }
}

