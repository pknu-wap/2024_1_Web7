package com.dogventure.dogweb.security.utils;

import com.dogventure.dogweb.mainLogic.repository.ExpiredTokenRepository;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secretKey;

    private final ExpiredTokenRepository repository;

    public String getUserEmail(String token) {
        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("email", String.class);
    }

    public String getUserAuthority(String token) {
        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("authority", String.class);
    }

    public boolean isTokenExpired(String token) {

        if (repository.existsByToken(token)) {
            return false;
        }

        return true;
    }
}
