package com.dogventure.dogweb.mainLogic.service;

import com.dogventure.dogweb.dto.mainLogic.naverMap.response.DetailPlaceDto;
import com.dogventure.dogweb.dto.mainLogic.naverMap.response.ReviewDto;
import com.dogventure.dogweb.dto.mainLogic.naverMap.response.SimplePlaceDto;
import com.dogventure.dogweb.dto.mainLogic.naverMap.response.UserDto;
import com.dogventure.dogweb.mainLogic.entity.Place;
import com.dogventure.dogweb.mainLogic.entity.Review;
import com.dogventure.dogweb.mainLogic.entity.User;
import com.dogventure.dogweb.mainLogic.repository.PlaceRepository;
import com.dogventure.dogweb.mainLogic.repository.ReviewRepository;
import com.dogventure.dogweb.mainLogic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NaverMapService {

    private final PlaceRepository placeRepo;

    public List<SimplePlaceDto> getAllPlace() {

        List<Place> places = placeRepo.findAll();

        List<SimplePlaceDto> simplePlaceDtos = new ArrayList<>();

        for (Place place : places) {
            simplePlaceDtos.add(new SimplePlaceDto(place.getId(), place.getX(), place.getY(), place.getName(), place.getImage(), place.getDetailContent(), place.getDogSize(), place.getRate()));
        }

        return simplePlaceDtos;
    }

    public DetailPlaceDto getPlace(Long id) {

        Place place = placeRepo.findById(id).orElseThrow(() -> new NullPointerException("찾는 Place 객체가 존재하지 않습니다"));

        List<ReviewDto> reviewDtos = new ArrayList<>();

        for (Review review : place.getReviews()) {
            User user = review.getUser();
            UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getImage());
            reviewDtos.add(new ReviewDto(review.getId(), review.getRate(), userDto, review.getContent()));
        }

        return new DetailPlaceDto(place.getId(), place.getX(), place.getY(), place.getName(), place.getImage(), place.getDetailContent(), place.getDogSize(), place.getRate(), reviewDtos);
    }

    public List<SimplePlaceDto> searchPlace(String word) {

        List<Place> places = placeRepo.findByNameContaining(word);

        if (places == null) {
            return null;
        }

        List<SimplePlaceDto> simplePlaceDtos = new ArrayList<>();

        for (Place place : places) {
            simplePlaceDtos.add(new SimplePlaceDto(place.getId(), place.getX(), place.getY(), place.getName(), place.getImage(), place.getDetailContent(), place.getDogSize(), place.getRate()));
        }

        return simplePlaceDtos;
    }
}
