package com.dogventure.dogweb.security.service;

import com.dogventure.dogweb.constant.UserType;
import com.dogventure.dogweb.mainLogic.entity.User;
import com.dogventure.dogweb.mainLogic.repository.UserRepository;
import com.dogventure.dogweb.security.entity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * 기존의 사용자 객체에서 UserDetails로 변환시켜주는 Service
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User findUser = userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("이메일과 일치하는 사용자가 없습니다"));
        UserType authority = findUser.getAuthority();

        return new UserDetailsImpl(findUser, List.of(new SimpleGrantedAuthority(authority.name())));
    }
}
