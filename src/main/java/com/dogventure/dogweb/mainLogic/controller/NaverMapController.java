package com.dogventure.dogweb.mainLogic.controller;

import com.dogventure.dogweb.dto.mainLogic.naverMap.request.NaverMapCategoryDto;
import com.dogventure.dogweb.dto.mainLogic.naverMap.response.DetailPlaceDto;
import com.dogventure.dogweb.dto.mainLogic.naverMap.response.SimplePlaceDto;
import com.dogventure.dogweb.mainLogic.entity.Place;
import com.dogventure.dogweb.mainLogic.service.NaverMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/map/naver/place")
public class NaverMapController {

    private final NaverMapService service;

    @GetMapping("/all")
    public ResponseEntity<List<SimplePlaceDto>> getAllPlace() {

        List<SimplePlaceDto> simplePlaceDtos = service.getAllPlace();
        return new ResponseEntity<>(simplePlaceDtos, HttpStatus.FOUND);
    }

    @GetMapping("/{place_id}")
    public ResponseEntity<DetailPlaceDto> getPlace(@PathVariable("place_id") Long id) {

        DetailPlaceDto detailPlaceDto = service.getPlace(id);
        return new ResponseEntity<>(detailPlaceDto, HttpStatus.FOUND);
    }

    @GetMapping("/search/{word}")
    public ResponseEntity<List<SimplePlaceDto>> searchPlace(@PathVariable String word) {

        List<SimplePlaceDto> simplePlaceDtos = service.searchPlace(word);
        return new ResponseEntity<>(simplePlaceDtos, HttpStatus.FOUND);
    }

    @PostMapping("/category")
    public ResponseEntity<List<SimplePlaceDto>> getPlaceByCategory(@RequestBody NaverMapCategoryDto naverMapCategory) {

        List<SimplePlaceDto> simplePlaceDtos = service.getPlaceByCategory(naverMapCategory);
        return new ResponseEntity<>(simplePlaceDtos, HttpStatus.FOUND);
    }
}
