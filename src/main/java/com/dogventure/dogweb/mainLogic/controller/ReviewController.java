package com.dogventure.dogweb.mainLogic.controller;

import com.dogventure.dogweb.dto.mainLogic.naverMap.request.ReviewRequestDto;
import com.dogventure.dogweb.dto.mainLogic.naverMap.request.ReviewUpdateRequestDto;
import com.dogventure.dogweb.dto.mainLogic.naverMap.response.ReviewIdResponseDto;
import com.dogventure.dogweb.mainLogic.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/place/review")
public class ReviewController {

    private final ReviewService service;

    @PostMapping("/write")
    public ResponseEntity write(@RequestBody ReviewRequestDto requestDto) {

        service.save(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody ReviewUpdateRequestDto requestDto) {

        service.update(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
