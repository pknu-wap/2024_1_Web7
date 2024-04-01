package com.dogventure.dogweb.mainLogic.controller;

import com.dogventure.dogweb.dto.mainLogic.naverMap.response.SimplePlaceDto;
import com.dogventure.dogweb.mainLogic.entity.Place;
import com.dogventure.dogweb.mainLogic.service.NaverMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/map/naver/place")
public class NaverMapController {

    private final NaverMapService service;

    @GetMapping("/all")
    public ResponseEntity<List<SimplePlaceDto>> getAllPlace() {

        List<SimplePlaceDto> places = service.getAllPlace();
        return new ResponseEntity<>(places, HttpStatus.OK);
    }
}
