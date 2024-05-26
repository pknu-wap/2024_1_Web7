package com.dogventure.dogweb.mainLogic.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double rate;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;            //엔티티 클래스의 필드 이름과 JPA 쿼리 메소드의 이름이 일치해야 함

    // Spring MVC가 요청 본문을 읽어 Review 객체로 변환
    public Review(Double rate, String content, User user, Place place) {
        this.rate = rate;
        this.content = content;
        this.user = user;
        this.place = place;
    }
}
