package com.seanlindev.springframework.shippingserviceapi.utils;

import java.security.SecureRandom;
import java.util.Random;

public class PublicIdGenerator {
    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyz";

    public static String generatePublicId(int length) {
        return generateRandomString(length);
    }

    private static String generateRandomString(int length) {
        StringBuilder value = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            value.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(value);
    }
}
