package com.dogventure.dogweb.mainLogic.service;

import com.dogventure.dogweb.dto.mainLogic.naverMap.request.ReviewDeleteRequestDto;
import com.dogventure.dogweb.dto.mainLogic.naverMap.request.ReviewRequestDto;
import com.dogventure.dogweb.dto.mainLogic.naverMap.request.ReviewUpdateRequestDto;
import com.dogventure.dogweb.mainLogic.entity.Place;
import com.dogventure.dogweb.mainLogic.entity.Review;
import com.dogventure.dogweb.mainLogic.entity.User;
import com.dogventure.dogweb.mainLogic.repository.PlaceRepository;
import com.dogventure.dogweb.mainLogic.repository.ReviewRepository;
import com.dogventure.dogweb.mainLogic.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;

    @Transactional
    public void save(ReviewRequestDto requestDto) throws IllegalAccessException {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException("토큰 인증을 받은 사용자가 존재하지 않습니다"));

        Place place = placeRepository.findById(requestDto.getPlaceId()).orElse(null);

        Review review = reviewRepository.findByUserAndPlace(user, place).orElse(null);

        // 리뷰 수정
        if (review != null) {
            review.setRate(requestDto.getRate());
            review.setContent(requestDto.getContent());

            reviewRepository.save(review);

            Double averageRate = place.getReviews().stream().mapToDouble(Review::getRate).average().getAsDouble();

            place.setRate(averageRate);

            placeRepository.save(place);

            return;
        }

        if (reviewRepository.existsByUserAndPlace(user, place)) {
            throw new IllegalAccessException("한 장소에 두개 이상의 리뷰는 작성할 수 없습니다");
        }

        Review newReview = new Review(requestDto.getRate(), requestDto.getContent(), user, place);

        reviewRepository.save(newReview);

        place.addReview(newReview);

        placeRepository.save(place);

        Double averageRate = place.getReviews().stream().mapToDouble(Review::getRate).average().getAsDouble();

        place.setRate(averageRate);

        placeRepository.save(place);
    }

    @Transactional
    public void update(ReviewUpdateRequestDto requestDto) {

        Review review = reviewRepository.findById(requestDto.getReviewId()).orElseThrow(() -> new EntityNotFoundException("Review가 존재하지 않습니다"));

        review.setRate(requestDto.getRate());
        review.setContent(requestDto.getContent());

        reviewRepository.save(review);

        Place place = placeRepository.findById(requestDto.getPlaceId()).orElse(null);

        Double averageRate = place.getReviews().stream().mapToDouble(Review::getRate).average().getAsDouble();

        place.setRate(averageRate);

        placeRepository.save(place);
    }

    public void delete(ReviewDeleteRequestDto requestDto) {

        if (!reviewRepository.existsById(requestDto.getReviewId())) {
            throw new NullPointerException("해당하는 리뷰가 존재하지 않습니다");
        }

        reviewRepository.deleteById(requestDto.getReviewId());

        Place place = placeRepository.findById(requestDto.getPlaceId()).orElse(null);

        if (place.getReviews().isEmpty()) {
            place.setRate(null);
            placeRepository.save(place);
            return;
        }

        Double averageRate = place.getReviews().stream().mapToDouble(Review::getRate).average().getAsDouble();

        place.setRate(averageRate);

        placeRepository.save(place);
    }
}
