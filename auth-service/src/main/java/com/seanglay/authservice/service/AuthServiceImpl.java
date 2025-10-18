package com.seanglay.authservice.service;

import com.seanglay.authservice.dto.AuthResponseDto;
import com.seanglay.authservice.dto.LoginRequestDto;
import com.seanglay.authservice.model.User;
import com.seanglay.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtService.generateToken(user.getEmail(), Map.of());
        return AuthResponseDto.builder().accessToken(token).expiresAt(jwtService.expirationInstant()).build();
    }
}
