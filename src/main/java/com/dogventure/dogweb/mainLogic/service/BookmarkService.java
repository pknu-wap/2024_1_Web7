package com.dogventure.dogweb.mainLogic.service;

import com.dogventure.dogweb.dto.mainLogic.naverMap.request.PlaceIdRequestDto;
import com.dogventure.dogweb.dto.mainLogic.naverMap.response.ImageDto;
import com.dogventure.dogweb.dto.mainLogic.naverMap.response.SimplePlaceDto;
import com.dogventure.dogweb.mainLogic.entity.Image;
import com.dogventure.dogweb.mainLogic.entity.Place;
import com.dogventure.dogweb.mainLogic.entity.User;
import com.dogventure.dogweb.mainLogic.repository.PlaceRepository;
import com.dogventure.dogweb.mainLogic.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
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

        if (user.getBookmarkPlaces().contains(place)) {
            user.getBookmarkPlaces().remove(place);
            userRepository.save(user);

        } else {
            user.getBookmarkPlaces().add(place);
            userRepository.save(user);
        }
    }

    public List<SimplePlaceDto> getBookmarkedPlaces() {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = UserRepository.findUserByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException("토큰 인증을 받은 사용자가 존재하지 않습니다"));

        List<SimplePlaceDto> bookmarkedPlaces = new ArrayList<>();

        for (Place place : user.getBookmarkPlaces()) {

            List<Image> images = place.getImages();
            List<ImageDto> imageDtos = new ArrayList<>();

            for (Image image : images) {
                imageDtos.add(new ImageDto(image.getFilename(), image.getData()));
            }

            LocalTime now = LocalTime.now();
            boolean isOpen = !now.isBefore(place.getStartTime()) && !now.isAfter(place.getEndTime());

            bookmarkedPlaces.add(new SimplePlaceDto(place.getId(), place.getX(), place.getY(), place.getName(), imageDtos, isOpen, place.getDetailContent(), place.getPlaceType(), place.getDogSize(), place.getRate(), true));
        }

        return bookmarkedPlaces;
    }
}
