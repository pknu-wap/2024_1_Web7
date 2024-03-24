package com.dogventure.dogweb.security.authProvider;

import com.dogventure.dogweb.security.auth.JwtAuthToken;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtAuthProvider implements AuthenticationProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public Authentication authenticate(Authentication auth) {

        if (auth instanceof JwtAuthToken jwtAuth) {
            String token = jwtAuth.getToken();

            if (validateToken(token)) {
                log.info("JWT 토큰 검증을 성공하였습니다");
                return new JwtAuthToken(jwtAuth.getName(), jwtAuth.getAuthorities());
            }

            throw new AuthenticationServiceException("JWT 토큰 인증 오류입니다");
        }

        log.info("주어진 Authentication이 JwtAuthToken으로 캐스팅 할 수 없습니다");
        throw new AuthenticationServiceException("인증 오류 입니다");
    }

    @Override
    public boolean supports(Class<?> authentication) {

        return JwtAuthToken.class.isAssignableFrom(authentication);
    }

    private boolean validateToken(String token) {

        try {
            Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;

        } catch (SignatureException e) {
            log.info("유효하지 않은 Jwt 토큰입니다");

        } catch (IllegalArgumentException e) {
            log.info("Authorization이 없거나 잘못된 형식입니다");

        } catch (ExpiredJwtException e) {
            log.info("만료된 Jwt 토큰입니다");
        }

        return false;
    }
}
