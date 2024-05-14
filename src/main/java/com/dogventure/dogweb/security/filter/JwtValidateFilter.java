package com.dogventure.dogweb.security.filter;

import com.dogventure.dogweb.security.utils.JwtUtils;
import com.dogventure.dogweb.security.auth.JwtAuthToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/*
 * 모든 요청에서 JWT 토큰 검증하는 필터
 */
@Slf4j
@RequiredArgsConstructor
public class JwtValidateFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info(request.getHeader(HttpHeaders.AUTHORIZATION));
        if (isSkip(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getToken(request);

        String email = jwtUtils.getUserEmail(token);
        String authority = jwtUtils.getUserAuthority(token);

        if (jwtUtils.isTokenExpired(token)) {
            filterChain.doFilter(request, response);
        }

        Authentication authentication = authenticationManager.authenticate(
                new JwtAuthToken(email, List.of(new SimpleGrantedAuthority(authority)), token)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }


    private String getToken(HttpServletRequest request) {

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

        return token;
    }

    private boolean isSkip(HttpServletRequest request){

        String[] skipUrls = {"/api/basic/signup", "/api/basic/login", "/api/test/place/add", "/api/place/review/write"};

        if (Arrays.stream(skipUrls).anyMatch(url -> url.equals(request.getRequestURI()))) {
            return true;
        } else if (request.getRequestURI().contains("/api/map/naver/guest/place")) {
            return true;
        }

        return false;
    }
}
