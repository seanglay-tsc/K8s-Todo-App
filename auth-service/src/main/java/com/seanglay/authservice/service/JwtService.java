package com.seanglay.authservice.service;

import java.time.Instant;
import java.util.Map;

public interface JwtService {

    String generateToken(String subject, Map<String, Object> claims);

    <T> T extractClaim(String token, String claimKey, Class<T> clazz);

    boolean isTokenValid(String token, String subject);

    Instant expirationInstant();
}

