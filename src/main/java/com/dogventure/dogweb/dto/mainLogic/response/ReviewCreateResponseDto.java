package com.dogventure.dogweb.dto.mainLogic.response;

import com.dogventure.dogweb.dto.mainLogic.request.ReviewCreateRequestDto;
import com.dogventure.dogweb.mainLogic.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreateResponseDto {

    private String title;
    private String content;
    private String username;
    private String message;

    public ReviewCreateResponseDto(Review review, String message) {

        // entity의 review를 생성자로 받습니다.
        this.title = review.getTitle();
        this.content = review.getContent();
        this.username = review.getUsername();
        this.message = message;
    }
}
