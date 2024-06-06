package com.dogventure.dogweb.mainLogic.controller;

import com.dogventure.dogweb.dto.mainLogic.naverMap.request.ReviewDeleteRequestDto;
import com.dogventure.dogweb.dto.mainLogic.naverMap.request.ReviewRequestDto;
import com.dogventure.dogweb.dto.mainLogic.naverMap.request.ReviewUpdateRequestDto;
import com.dogventure.dogweb.mainLogic.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/place/review")
public class ReviewController {

    private final ReviewService service;

    @PostMapping("/write")
    public ResponseEntity write(@RequestBody ReviewRequestDto requestDto) throws IllegalAccessException {

        service.save(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@RequestBody ReviewDeleteRequestDto requestDto) {

        service.delete(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
