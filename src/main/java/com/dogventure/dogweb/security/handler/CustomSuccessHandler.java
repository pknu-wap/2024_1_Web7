package com.dogventure.dogweb.security.handler;

import com.dogventure.dogweb.dto.oauth2.response.Oauth2UserResponseDto;
import com.dogventure.dogweb.security.auth.JwtAuthToken;
import com.dogventure.dogweb.security.utils.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.http.HttpStatus.*;

/*
 * 소셜 로그인을 성공적으로 수행할 경우 토큰 발행
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Oauth2UserResponseDto oauth2User = (Oauth2UserResponseDto) authentication.getPrincipal();

        String token = jwtTokenProvider.createJwt(new UsernamePasswordAuthenticationToken(oauth2User.getEmail(), null, oauth2User.getAuthorities()));
        log.info("JWT 토큰이 발급되었습니다");

        Authentication auth = new JwtAuthToken(oauth2User.getEmail(), oauth2User.getAuthorities(), token);

        SecurityContextHolder.getContext().setAuthentication(auth);

        response.setStatus(HttpStatus.OK.value());
        response.sendRedirect("http://dogventure.ap-northeast-2.elasticbeanstalk.com/login?token=" + token);
    }
}



