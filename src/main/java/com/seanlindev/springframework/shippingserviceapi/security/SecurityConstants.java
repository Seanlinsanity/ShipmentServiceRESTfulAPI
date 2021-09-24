package com.seanlindev.springframework.shippingserviceapi.security;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864000000;
    public static final long API_ACCESS_EXPIRATION_TIME = 600;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_SECRET = "MY-JWT-SECRET";

    public static final String API_ACCESS_KEY = "Access-Key";
    public static final String API_ACCESS_SIGNATURE = "Access-Signature";
    public static final String API_REQUEST_ID = "X-Request-ID";
    public static final String API_SIGN_ALGORITHM = "HmacSHA256";
}
