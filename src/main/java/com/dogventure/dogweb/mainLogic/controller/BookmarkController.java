package com.dogventure.dogweb.mainLogic.controller;

import com.dogventure.dogweb.dto.mainLogic.naverMap.request.PlaceIdRequestDto;
import com.dogventure.dogweb.dto.mainLogic.naverMap.response.SimplePlaceDto;
import com.dogventure.dogweb.mainLogic.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
public class BookmarkController {

    private final BookmarkService service;

    @PostMapping
    public void bookmark(@RequestBody PlaceIdRequestDto requestDto) {
        service.bookmark(requestDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SimplePlaceDto>> getAllBookmarks() {

        List<SimplePlaceDto> simplePlaceDtoList = service.getBookmarkedPlaces();

        return new ResponseEntity<>(simplePlaceDtoList, HttpStatus.OK);
    }
}
