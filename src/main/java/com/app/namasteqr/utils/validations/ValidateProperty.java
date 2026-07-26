package com.app.namasteqr.utils.validations;

import java.util.regex.Pattern;

public class ValidateProperty {

    private static final int NAME_MAX_LENGTH = 50;
    private static final int DESCRIPTION_MAX_LENGTH = 50;
    private static final int ADDRESS_MAX_LENGTH = 255;

    private static final Pattern LOGO_LINK_PATTERN =
            Pattern.compile("^(https?://).+");

    public static void validateProperty(String name,
                                        String description,
                                        String address,
                                        String logoLink) {
        validatePropertyName(name);
        validateDescription(description);
        validateAddress(address);
        validateLogoLink(logoLink);
    }

    public static void validatePropertyName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Property name cannot be empty");
        }

        if (name.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("Property name cannot exceed 50 characters");
        }
    }

    public static void validateDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Property description cannot be empty");
        }

        if (description.length() > DESCRIPTION_MAX_LENGTH) {
            throw new IllegalArgumentException("Property description cannot exceed 50 characters");
        }
    }

    public static void validateAddress(String address) {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Property address cannot be empty");
        }

        if (address.length() > ADDRESS_MAX_LENGTH) {
            throw new IllegalArgumentException("Property address cannot exceed 255 characters");
        }
    }

    public static void validateLogoLink(String logoLink) {
        validateLink(logoLink, "Property logo_link");
    }

    public static void validateLink(String link, String fieldName) {
        if (link == null || link.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }

        if (!LOGO_LINK_PATTERN.matcher(link).matches()) {
            throw new IllegalArgumentException(fieldName + " must start with http:// or https://");
        }
    }
}
