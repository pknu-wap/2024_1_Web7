package com.dogventure.dogweb.mainLogic.controller;

import com.dogventure.dogweb.dto.mainLogic.request.ReviewCreateRequestDto;
import com.dogventure.dogweb.dto.mainLogic.response.ReviewCreateResponseDto;
import com.dogventure.dogweb.mainLogic.entity.Review;
import com.dogventure.dogweb.mainLogic.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/post")
    public ResponseEntity<ReviewCreateResponseDto> reviewwrite(@RequestBody ReviewCreateRequestDto reviewCreateRequestDto, Authentication authentication) {
        Review review = new Review(reviewCreateRequestDto);
        // 이거 맞음?
        if (authentication.isAuthenticated()) {
            reviewService.createReview(review);
        }
        ReviewCreateResponseDto reviewCreateResponseDto = new ReviewCreateResponseDto(review, "리뷰 등록 성공");
        log.info("Controller user : {}", authentication.getName());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
