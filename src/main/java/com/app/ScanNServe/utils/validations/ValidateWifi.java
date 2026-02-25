package com.app.ScanNServe.utils.validations;

import java.util.regex.Pattern;

public class ValidateWifi {

    // At least 1 lowercase, 1 uppercase, 1 digit, 1 special char, length > 8
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{9,}$");

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
}

