package com.dogventure.dogweb.mainLogic.controller;

import com.dogventure.dogweb.dto.mainLogic.naverMap.request.NaverMapCategoryDto;
import com.dogventure.dogweb.dto.mainLogic.naverMap.response.DetailPlaceDto;
import com.dogventure.dogweb.dto.mainLogic.naverMap.response.GuestDetailPlaceDto;
import com.dogventure.dogweb.dto.mainLogic.naverMap.response.GuestSimplePlaceDto;
import com.dogventure.dogweb.dto.mainLogic.naverMap.response.SimplePlaceDto;
import com.dogventure.dogweb.mainLogic.service.GuestNaverMapService;
import com.dogventure.dogweb.mainLogic.service.NaverMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/map/naver/guest/place")
public class NaverMapGuestController {

    private final GuestNaverMapService service;

    @GetMapping("/all")
    public ResponseEntity<List<GuestSimplePlaceDto>> getAllPlace() {

        List<GuestSimplePlaceDto> simplePlaceDtos = service.getAllPlace();
        return new ResponseEntity<>(simplePlaceDtos, HttpStatus.FOUND);
    }

    @GetMapping("/{place_id}")
    public ResponseEntity<GuestDetailPlaceDto> getPlace(@PathVariable("place_id") Long id) {

        GuestDetailPlaceDto detailPlaceDto = service.getPlace(id);
        return new ResponseEntity<>(detailPlaceDto, HttpStatus.FOUND);
    }

    @GetMapping("/search/{word}")
    public ResponseEntity<List<GuestSimplePlaceDto>> searchPlace(@PathVariable("word") String word) {

        List<GuestSimplePlaceDto> simplePlaceDtos = service.searchPlace(word);
        return new ResponseEntity<>(simplePlaceDtos, HttpStatus.FOUND);
    }

    @PostMapping("/category")
    public ResponseEntity<List<GuestSimplePlaceDto>> getPlaceByCategory(@RequestBody NaverMapCategoryDto naverMapCategory) {

        List<GuestSimplePlaceDto> simplePlaceDtos = service.getPlaceByCategory(naverMapCategory);
        return new ResponseEntity<>(simplePlaceDtos, HttpStatus.FOUND);
    }
}
