package com.dogventure.dogweb.mainLogic.service;

import com.dogventure.dogweb.constant.DogSize;
import com.dogventure.dogweb.constant.PlaceType;
import com.dogventure.dogweb.dto.mainLogic.naverMap.request.NaverMapCategoryDto;
import com.dogventure.dogweb.dto.mainLogic.naverMap.response.*;
import com.dogventure.dogweb.mainLogic.entity.*;
import com.dogventure.dogweb.mainLogic.repository.PlaceRepository;
import com.dogventure.dogweb.mainLogic.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GuestNaverMapService {

    private final PlaceRepository repository;

    public List<GuestSimplePlaceDto> getAllPlace() {

//        Pageable pageable = PageRequest.of(0, 9);
        List<Place> places = repository.findAll();

        List<GuestSimplePlaceDto> simplePlaceDtos = new ArrayList<>();

        for (Place place : places) {

            List<Image> images = place.getImages();
            List<ImageDto> imageDtos = new ArrayList<>();

            for (Image image : images) {
                imageDtos.add(new ImageDto(image.getFilename(), image.getData()));
            }

            ZoneId zoneId = ZoneId.of("Asia/Seoul");
            LocalTime now = LocalTime.now(zoneId);
            boolean isOpen = !now.isBefore(place.getStartTime()) && !now.isAfter(place.getEndTime());

            simplePlaceDtos.add(new GuestSimplePlaceDto(place.getId(), place.getX(), place.getY(), place.getName(), imageDtos, isOpen, place.getDetailContent(), place.getPlaceType(), place.getDogSize(), place.getRate()));
        }

        return simplePlaceDtos;
    }

    public GuestDetailPlaceDto getPlace(Long id) {

        Place place = repository.findById(id).orElseThrow(() -> new NullPointerException("찾는 Place 객체가 존재하지 않습니다"));

        List<ReviewDto> reviewDtos = new ArrayList<>();

        for (Review review : place.getReviews()) {
            User user = review.getUser();

            if (user.getDog() == null) {
                UserDto userDto = new UserDto(user.getId(), null, user.getUsername());
                reviewDtos.add(new ReviewDto(review.getId(), review.getRate(), userDto, review.getContent()));
            } else {
                Dog dog = user.getDog();
                ImageDto userImageDto = new ImageDto(dog.getImage().getFilename(), dog.getImage().getData());
                UserDto userDto = new UserDto(user.getId(), userImageDto, user.getUsername());
                reviewDtos.add(new ReviewDto(review.getId(), review.getRate(), userDto, review.getContent()));
            }
        }

        List<Image> images = place.getImages();
        List<ImageDto> imageDtos = new ArrayList<>();

        for (Image image : images) {
            imageDtos.add(new ImageDto(image.getFilename(), image.getData()));
        }

        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        LocalTime now = LocalTime.now(zoneId);
        boolean isOpen = !now.isBefore(place.getStartTime()) && !now.isAfter(place.getEndTime());

        return new GuestDetailPlaceDto(place.getId(), place.getX(), place.getY(), place.getName(), imageDtos, isOpen, place.getStartTime().toString(), place.getEndTime().toString(), place.getAddress(), place.getPhoneNumber(), place.getDetailContent(), place.getPlaceType(), place.getDogSize(), place.getRate(), reviewDtos);
    }

    public List<GuestSimplePlaceDto> searchPlace(String word) {

        List<Place> places = repository.findByNameContaining(word);

        if (places == null) {
            return null;
        }

        List<GuestSimplePlaceDto> simplePlaceDtos = new ArrayList<>();

        for (Place place : places) {

            List<Image> images = place.getImages();
            List<ImageDto> imageDtos = new ArrayList<>();

            for (Image image : images) {
                imageDtos.add(new ImageDto(image.getFilename(), image.getData()));
            }

            ZoneId zoneId = ZoneId.of("Asia/Seoul");
            LocalTime now = LocalTime.now(zoneId);
            boolean isOpen = !now.isBefore(place.getStartTime()) && !now.isAfter(place.getEndTime());

            simplePlaceDtos.add(new GuestSimplePlaceDto(place.getId(), place.getX(), place.getY(), place.getName(), imageDtos, isOpen, place.getDetailContent(), place.getPlaceType(), place.getDogSize(), place.getRate()));
        }

        return simplePlaceDtos;
    }

    public List<GuestSimplePlaceDto> getPlaceByCategory(NaverMapCategoryDto naverMapCategory) {

        PlaceType type = naverMapCategory.getPlaceType();
        DogSize size = naverMapCategory.getDogSize();
        List<Place> places;

        if (type==null && size!=null) {
            places = repository.findByDogSize(size);
        } else if (type!=null && size==null) {
            places = repository.findByPlaceType(type);
        } else if (type!=null && size!=null) {
            places = repository.findByPlaceTypeAndDogSize(type, size);
        } else {
            throw new NullPointerException("PlaceType, DogSize 두 필드 모두 null 입니다");
        }

        List<GuestSimplePlaceDto> simplePlaceDtos = new ArrayList<>();

        for (Place place : places) {

            List<Image> images = place.getImages();
            List<ImageDto> imageDtos = new ArrayList<>();

            for (Image image : images) {
                imageDtos.add(new ImageDto(image.getFilename(), image.getData()));
            }

            ZoneId zoneId = ZoneId.of("Asia/Seoul");
            LocalTime now = LocalTime.now(zoneId);
            boolean isOpen = !now.isBefore(place.getStartTime()) && !now.isAfter(place.getEndTime());

            simplePlaceDtos.add(new GuestSimplePlaceDto(place.getId(), place.getX(), place.getY(), place.getName(), imageDtos, isOpen, place.getDetailContent(), place.getPlaceType(), place.getDogSize(), place.getRate()));
        }

        return simplePlaceDtos;
    }
}