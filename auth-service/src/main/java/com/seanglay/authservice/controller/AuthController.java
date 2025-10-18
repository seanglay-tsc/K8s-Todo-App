package com.seanglay.authservice.controller;

import com.seanglay.authservice.dto.AuthResponseDto;
import com.seanglay.authservice.dto.LoginRequestDto;
import com.seanglay.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AuthController.URL)
@RequiredArgsConstructor
public class AuthController {
    public static final String URL = "/api/auth";

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }
}