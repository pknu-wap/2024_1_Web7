package com.dogventure.dogweb.mainLogic.service;

import com.dogventure.dogweb.dto.response.TokenResponseDto;
import com.dogventure.dogweb.mainLogic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicUserService {

    private final UserRepository repository;
    private final PasswordEncoder passEncoder;

    public TokenResponseDto login() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof JwtAuthToken jwtAuth) {
            return new TokenResponseDto(jwtAuth.getToken());
        }
    }
}
