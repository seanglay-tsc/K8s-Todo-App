package com.seanglay.authservice.service;

import com.seanglay.authservice.dto.AuthResponseDto;
import com.seanglay.authservice.dto.LoginRequestDto;

public interface AuthService {
    AuthResponseDto login(LoginRequestDto loginRequestDto);
}
