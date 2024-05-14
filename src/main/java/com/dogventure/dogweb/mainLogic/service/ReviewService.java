package com.dogventure.dogweb.mainLogic.service;

import com.dogventure.dogweb.dto.mainLogic.naverMap.request.ReviewRequestDto;
import com.dogventure.dogweb.dto.mainLogic.naverMap.request.ReviewUpdateRequestDto;
import com.dogventure.dogweb.dto.mainLogic.naverMap.response.ReviewIdResponseDto;
import com.dogventure.dogweb.mainLogic.entity.Place;
import com.dogventure.dogweb.mainLogic.entity.Review;
import com.dogventure.dogweb.mainLogic.entity.User;
import com.dogventure.dogweb.mainLogic.repository.PlaceRepository;
import com.dogventure.dogweb.mainLogic.repository.ReviewRepository;
import com.dogventure.dogweb.mainLogic.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;

    public void save(ReviewRequestDto requestDto) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException("토큰 인증을 받은 사용자가 존재하지 않습니다"));

        Place place = placeRepository.findById(requestDto.getPlaceId()).orElse(null);

        Review review = new Review(requestDto.getRate(), requestDto.getContent(), user, place);

        reviewRepository.save(review);
    }

    public void update(ReviewUpdateRequestDto requestDto) {

        Review review = reviewRepository.findById(requestDto.getReviewId()).orElseThrow(() -> new EntityNotFoundException("Review가 존재하지 않습니다"));

        review.setRate(requestDto.getRate());
        review.setContent(requestDto.getContent());

        reviewRepository.save(review);
    }
}
