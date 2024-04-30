package com.dogventure.dogweb.mainLogic.service;

import com.dogventure.dogweb.dto.mainLogic.naverMap.request.PlaceIdRequestDto;
import com.dogventure.dogweb.dto.mainLogic.naverMap.response.SimplePlaceDto;
import com.dogventure.dogweb.mainLogic.entity.Place;
import com.dogventure.dogweb.mainLogic.entity.User;
import com.dogventure.dogweb.mainLogic.repository.PlaceRepository;
import com.dogventure.dogweb.mainLogic.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkService {

    private final UserRepository UserRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    public void bookmark(PlaceIdRequestDto requestDto) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = UserRepository.findUserByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException("토큰 인증을 받은 사용자가 존재하지 않습니다"));

        Place place = placeRepository.findById(requestDto.getId()).orElseThrow(() -> new EntityNotFoundException("Place가 없습니다"));

        user.getBookmarkPlaces().add(place);

        userRepository.save(user);
    }

    public void unBookmark(PlaceIdRequestDto requestDto) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = UserRepository.findUserByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException("토큰 인증을 받은 사용자가 존재하지 않습니다"));

        Place place = placeRepository.findById(requestDto.getId()).orElseThrow(() -> new EntityNotFoundException("Place가 없습니다"));

        user.getBookmarkPlaces().remove(place);

        userRepository.save(user);
    }

    public List<SimplePlaceDto> getBookmarkedPlaces() {

    }
}
