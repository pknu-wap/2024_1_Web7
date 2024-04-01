package com.dogventure.dogweb.dto.mainLogic.naverMap.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewDto {

    private Long id;

    private Double rate;

    private UserDto user;

    private String content;
}
