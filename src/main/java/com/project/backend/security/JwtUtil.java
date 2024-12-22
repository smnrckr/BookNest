package com.project.backend.security;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final String SECRET_KEY = "955d4164aaecf4c6ded32b08521621dbeff6d10e87c4c50fd8a16c4c62683a91a797bbef0bbf3f3acb57404327ffc9cfcf7acb40c61fa0fbe70c55d4500d6c17";

    public static String generateToken(String username, Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }


    public static String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    private static Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public static boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    public static String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();  // Burada subject 'username' olmalı
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid token signature");
        } catch (Exception e) {
            throw new RuntimeException("Token is invalid or expired");
        }
    }
}
