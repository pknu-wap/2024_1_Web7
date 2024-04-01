package com.dogventure.dogweb.mainLogic.service;

import com.dogventure.dogweb.dto.mainLogic.naverMap.response.SimplePlaceDto;
import com.dogventure.dogweb.mainLogic.entity.Place;
import com.dogventure.dogweb.mainLogic.repository.PlaceRepository;
import com.dogventure.dogweb.mainLogic.repository.ReviewRepository;
import com.dogventure.dogweb.mainLogic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NaverMapService {

    private final PlaceRepository placeRepo;

    private final ReviewRepository reviewRepo;

    private final UserRepository userRepo;


    public List<SimplePlaceDto> getAllPlace() {

        List<Place> places = placeRepo.findAll();
        List<SimplePlaceDto> simplePlaces = new ArrayList<>();

        for (Place place : places) {
            simplePlaces.add(new SimplePlaceDto(place.getId(), place.getX(), place.getY(), place.getName(), place.getImage(), place.getDetailContent(), place.getDogSize(), place.getRate()));
        }

        return simplePlaces;
    }

}
