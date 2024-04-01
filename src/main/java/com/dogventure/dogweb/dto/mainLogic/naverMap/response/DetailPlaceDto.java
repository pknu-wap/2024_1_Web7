package com.dogventure.dogweb.dto.mainLogic.naverMap.response;

import com.dogventure.dogweb.constant.DogSize;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DetailPlaceDto {

    private Long id;

    private Double x;

    private Double y;

    private String name;

    private String image;

    private String detailContent;

    private DogSize dogSize;

    private Double rate;

    private List<ReviewDto> reviews;
}
