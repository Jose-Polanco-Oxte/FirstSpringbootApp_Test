package com.devtony.app.security.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;

@Component
public class JwtUtil {

    private final Key SECRET_KEY;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Extrae el id del token
    public Long extractUserId(String token) {
        return Long.valueOf(extractClaim(token, Claims::getSubject)); // El subject será el id
    }

    // Extrae roles del token
    public String extractRoles(String token) {
        return extractClaim(token, claims -> claims.get("roles", String.class));
    }

    // Extrae cualquier claim del token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
    }

    // Genera el token, usando el id como subject
    public String generateToken(String userId, Set<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", String.join(",", roles)); // Almacena roles como cadena separada por comas
        return createToken(claims, userId);
    }

    // Crea el token con el id como subject

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) // Claims adicionales
                .setSubject(subject) // Aquí asignas el id del usuario
                .setIssuedAt(new Date(System.currentTimeMillis())) // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Expiración (10 horas)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // Firma con clave secreta
                .compact();
    }

    // Valida si el token es correcto y pertenece al id esperado
    public boolean isTokenValid(String token, String userId) {
        final String extractedUserId = String.valueOf(extractUserId(token));
        return (extractedUserId.equals(userId) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}

