package com.dogventure.dogweb.dto.oauth2.response;

import com.dogventure.dogweb.constant.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Oauth2UserDto {

    private String username;
    private String oauth2Key;
    private String email;
    private UserType authority;
}
