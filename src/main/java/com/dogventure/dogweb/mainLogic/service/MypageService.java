package com.dogventure.dogweb.mainLogic.service;

import com.dogventure.dogweb.dto.mainLogic.mypage.request.MypageUpdateRequestDto;
import com.dogventure.dogweb.dto.mainLogic.mypage.response.MypageResponseDto;
import com.dogventure.dogweb.dto.mainLogic.naverMap.response.ImageDto;
import com.dogventure.dogweb.mainLogic.entity.Image;
import com.dogventure.dogweb.mainLogic.entity.User;
import com.dogventure.dogweb.mainLogic.repository.ImageRepository;
import com.dogventure.dogweb.mainLogic.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class MypageService {

    private final UserRepository repository;
    private final ImageRepository imageRepository;

    public MypageResponseDto getMypage() {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = repository.findUserByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException("토큰 인증을 받은 사용자가 존재하지 않습니다"));

        MypageResponseDto responseDto = new MypageResponseDto(user.getUsername(), user.getDescription());

        return responseDto;
    }

    public void updateMypage(MypageUpdateRequestDto requestDto) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = repository.findUserByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException("토큰 인증을 받은 사용자가 존재하지 않습니다"));

        user.setUsername(requestDto.getName());
        user.setDescription(requestDto.getDescription());

        repository.save(user);
    }
}
