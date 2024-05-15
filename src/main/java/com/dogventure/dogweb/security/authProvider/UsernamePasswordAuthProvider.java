package com.dogventure.dogweb.security.authProvider;

import com.dogventure.dogweb.security.auth.JwtAuthToken;
import com.dogventure.dogweb.security.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/*
 * 로그인시 이메일, 비밀번호를 검증해서 토큰을 발급하는 Provider
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UsernamePasswordAuthProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Authentication authenticate(Authentication auth) {

        String email = auth.getName();
        String password = auth.getCredentials().toString();

        UserDetails user = userDetailsService.loadUserByUsername(email);

        if (passwordEncoder.matches(password, user.getPassword())) {
            log.info("로그인을 통과하였습니다");
            String token = jwtTokenProvider.createJwt(new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities()));
            log.info("JWT 토큰이 발급되었습니다");

            return new JwtAuthToken(user.getUsername(), user.getAuthorities(), token);

        } else {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {

        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
