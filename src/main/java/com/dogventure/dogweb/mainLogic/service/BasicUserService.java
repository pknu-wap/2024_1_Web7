package com.dogventure.dogweb.mainLogic.service;

import com.dogventure.dogweb.constant.UserType;
import com.dogventure.dogweb.dto.mainLogic.login.request.SignupRequestDto;
import com.dogventure.dogweb.dto.mainLogic.login.response.TokenResponseDto;
import com.dogventure.dogweb.mainLogic.entity.ExpiredToken;
import com.dogventure.dogweb.mainLogic.entity.User;
import com.dogventure.dogweb.mainLogic.repository.ExpiredTokenRepository;
import com.dogventure.dogweb.mainLogic.repository.UserRepository;
import com.dogventure.dogweb.security.auth.JwtAuthToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BasicUserService {

    private final UserRepository repository;
    private final ExpiredTokenRepository expiredTokenRepository;
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

    public void logout(HttpServletRequest request) {

        String token = null;

        String fullToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (fullToken!=null && fullToken.startsWith("Bearer ")) {
            token = fullToken.split(" ")[1];
        }

        if (token == null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("Authorization")) {
                    token = cookie.getValue();
                }
            }
        }

        ExpiredToken expiredToken = new ExpiredToken(token);

        expiredTokenRepository.save(expiredToken);
    }


    private UserType checkRole(String email) {

        if (email.equals(adminEmail)) {
            return UserType.ADMIN;
        } else {
            return UserType.USER;
        }
    }
}
