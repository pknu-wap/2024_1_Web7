package com.dogventure.dogweb.dto.oauth2.response;

import com.dogventure.dogweb.dto.oauth2.response.Oauth2UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class Oauth2UserResponseDto implements OAuth2User {

    private final Oauth2UserDto userDto;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userDto.getAuthority().name();
            }
        });

        return authorities;
    }

    @Override
    public String getName() {
        return userDto.getUsername();
    }

    public String getOauth2Key() {
        return userDto.getOauth2Key();
    }

    public String getEmail() {
        return userDto.getEmail();
    }
}

