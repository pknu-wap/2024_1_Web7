package com.dogventure.dogweb.dto.mainLogic.naverMap.request;

import lombok.Getter;

@Getter
public class ReviewUpdateRequestDto {

    private Long reviewId;

    private Long placeId;

    private Double rate;

    private String content;
}
