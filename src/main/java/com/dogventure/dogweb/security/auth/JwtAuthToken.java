package com.dogventure.dogweb.security.auth;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/*
 * JWT 검증에 사용할 직접 구현한 토큰
 */
@Getter
public class JwtAuthToken extends AbstractAuthenticationToken {

    private final Object principal; // email 값
    private String token;

    // SecurityContext 저장용 생성자
    public JwtAuthToken(String principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
    }

    // 토큰 검증용 생성자
    public JwtAuthToken(String principal, Collection<? extends GrantedAuthority> authorities, String token) {
        super(authorities);
        this.principal = principal;
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

}