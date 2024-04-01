package com.dogventure.dogweb.mainLogic.service;

import com.dogventure.dogweb.constant.UserType;
import com.dogventure.dogweb.dto.mainLogic.login.request.SignupRequestDto;
import com.dogventure.dogweb.dto.mainLogic.login.response.TokenResponseDto;
import com.dogventure.dogweb.mainLogic.entity.User;
import com.dogventure.dogweb.mainLogic.repository.UserRepository;
import com.dogventure.dogweb.security.auth.JwtAuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BasicUserService {

    private final UserRepository repository;
    private final PasswordEncoder passEncoder;

    @Value("${admin.email}")
    private String adminEmail;

    public TokenResponseDto login() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof JwtAuthToken jwtAuth) {
            return new TokenResponseDto(jwtAuth.getToken());
        } else {
            log.info("주어진 Authentication이 JwtAuthToken으로 캐스팅 할 수 없습니다");
            throw new ClassCastException("형변환 오류입니다");
        }
    }

    public void signup(SignupRequestDto request) {

        String encodedPassword = passEncoder.encode(request.getPassword());
        UserType role = checkRole(request.getEmail());

        User user = new User(request.getUsername(), request.getEmail(), encodedPassword, role);
        repository.save(user);
    }

    private UserType checkRole(String email) {

        if (email.equals(adminEmail)) {
            return UserType.ADMIN;
        } else {
            return UserType.USER;
        }
    }
}
