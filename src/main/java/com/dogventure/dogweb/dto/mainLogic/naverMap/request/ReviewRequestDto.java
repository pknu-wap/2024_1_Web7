package com.dogventure.dogweb.dto.mainLogic.naverMap.request;

import lombok.Getter;

@Getter
public class ReviewRequestDto {

    private Double rate;

    private String content;

    private Long placeId;
}
