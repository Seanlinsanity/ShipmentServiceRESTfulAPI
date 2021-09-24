package com.seanlindev.springframework.shippingserviceapi.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.UUID;

public class SecretKeyBuilder {
    public static String randomSecretKey() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
            String encoded = Base64.getEncoder().encodeToString(hash);
            return encoded;
        } catch (Exception exception) {
            throw new RuntimeException("Failed to generate secret key" + exception.getMessage());
        }
    }
}
