package com.seanglay.authservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class AuthResponseDto {
    String accessToken;
    Instant expiresAt;
}
