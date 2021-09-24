package com.seanlindev.springframework.shippingserviceapi.security;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTHelper {
    public static String generateToke(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET)
                .compact();
    }

    public static Boolean validateToken(String token) {
        if (token == null) {
            return false;
        }

        token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
        JwtParser parser = Jwts.parser().setSigningKey(SecurityConstants.TOKEN_SECRET);
        try {
            String userEmail = parser
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            if (userEmail == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
    }
}
