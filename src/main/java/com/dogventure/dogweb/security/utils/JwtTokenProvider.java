package com.dogventure.dogweb.security.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    // @Value: 값 주입 어노테이션
    @Value("${jwt.secret}")
    private String secretKey;

    private Long expiredMs = 1000 * 60 * 60l;

    public String createJwt(Authentication auth) {

        String authority = auth.getAuthorities().iterator().next().getAuthority();

        return Jwts
                .builder()
                .claim("email", auth.getName())
                .claim("authority", authority)
                .setIssuedAt(new Date(System.currentTimeMillis()))                          // 발행 시간 정보
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))            // 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, secretKey)                              // 사용할 암호화 알고리즘 & signature에 들어갈 secret값 세팅
                .compact();
    }
}

