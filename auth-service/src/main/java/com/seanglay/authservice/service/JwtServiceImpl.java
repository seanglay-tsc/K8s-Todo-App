package com.seanglay.authservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.ttl-seconds:3600}")
    private long ttlSeconds;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Override
    public String generateToken(String subject, Map<String, Object> claims) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(ttlSeconds);

        return Jwts.builder().setSubject(subject).addClaims(claims).setIssuedAt(Date.from(now)).setExpiration(Date.from(exp)).signWith(getSigningKey(), SignatureAlgorithm.HS512).compact();
    }

    @Override
    public <T> T extractClaim(String token, String claimKey, Class<T> clazz) {
        try {
            Claims claims = parseClaims(token);
            Object value = claims.get(claimKey);
            return clazz.cast(value);
        } catch (Exception e) {
            log.warn("Failed to extract claim: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public boolean isTokenValid(String token, String subject) {
        try {
            Claims claims = parseClaims(token);
            String tokenSubject = claims.getSubject();
            return tokenSubject.equals(subject) && claims.getExpiration().after(new Date());
        } catch (JwtException e) {
            log.warn("Invalid JWT: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public Instant expirationInstant() {
        return Instant.now().plusSeconds(ttlSeconds);
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }
}