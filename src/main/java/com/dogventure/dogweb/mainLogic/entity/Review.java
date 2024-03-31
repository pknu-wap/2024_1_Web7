package com.dogventure.dogweb.mainLogic.entity;

import com.dogventure.dogweb.dto.mainLogic.request.ReviewCreateRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// DB에 쓰일 필드와 여려 엔티티간 연관관꼐 정의
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id // == PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false, length = 500)
    private String content;
    @Column(name = "user_name", nullable = false)
    private String username;



    public Review(ReviewCreateRequestDto reviewCreateRequestDto) {
        this.title = reviewCreateRequestDto.getTitle();
        this.content = reviewCreateRequestDto.getContent();
        this.username = reviewCreateRequestDto.getUsername();
    }
}
