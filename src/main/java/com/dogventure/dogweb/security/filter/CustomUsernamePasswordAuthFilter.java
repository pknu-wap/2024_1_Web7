package com.dogventure.dogweb.security.filter;

import com.dogventure.dogweb.dto.mainLogic.login.request.LoginRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
 * 비밀번호 검증 필터 (이 필터가 비밀번호 검증 Provider를 실행)
 */
@RequiredArgsConstructor
public class CustomUsernamePasswordAuthFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (isSkip(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            LoginRequestDto loginRequestDto = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequestDto.getEmail(), loginRequestDto.getPassword()
            ));

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (IOException e) {
            throw new AuthenticationServiceException("인증 중 오류 발생", e);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isSkip(HttpServletRequest request) {

        if (!request.getRequestURI().equals("/api/basic/login")) {
            return true;
        }

        return false;
    }
}
