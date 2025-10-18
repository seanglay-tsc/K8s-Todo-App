package com.seanglay.authservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class LoginRequestDto {
    private UUID id;
    private String email;
    private String password;
}
