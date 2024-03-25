package com.dogventure.dogweb.security.service;

import com.dogventure.dogweb.dto.oauth2.response.*;
import com.dogventure.dogweb.constant.UserType;
import com.dogventure.dogweb.mainLogic.entity.User;
import com.dogventure.dogweb.mainLogic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Oauth2UserServiceImpl extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User user = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Oauth2ResponseDto oauth2Response;

        if (registrationId.equals("google")) {
            oauth2Response = new GoogleResponseDto(user.getAttributes());

        } else if (registrationId.equals("naver")) {
            oauth2Response = new NaverResponseDto(user.getAttributes());

        } else {
            return null;
        }

        String oauth2Key = oauth2Response.getProvider() + " " + oauth2Response.getProviderId();
        String name = oauth2Response.getName();
        String email = oauth2Response.getEmail();

        try {
            User findUser = userRepository.findUserByOauth2Key(oauth2Key).orElseThrow(() -> new UsernameNotFoundException("OAuth2 키와 일치하는 사용자가 없습니다"));
            // 유저 데이터를 업데이트 하는 기능 추후 추가

            Oauth2UserDto userDto = new Oauth2UserDto(name, oauth2Key, email, findUser.getAuthority());
            return new Oauth2UserResponseDto(userDto);

        } catch (UsernameNotFoundException e) {
            User newUser = new User(name, oauth2Key, checkRole(email), email);
            userRepository.save(newUser);

            Oauth2UserDto userDto = new Oauth2UserDto(name, oauth2Key, email, newUser.getAuthority());
            return new Oauth2UserResponseDto(userDto);
        }
    }

    private UserType checkRole(String email) {

        if (email.equals("admin@naver.com") || email.equals("admin@google.com")) {
            return UserType.ADMIN;
        } else {
            return UserType.USER;
        }
    }
}