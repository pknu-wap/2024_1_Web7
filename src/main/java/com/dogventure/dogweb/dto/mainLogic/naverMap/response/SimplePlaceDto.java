package com.dogventure.dogweb.dto.mainLogic.naverMap.response;

import com.dogventure.dogweb.constant.DogSize;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimplePlaceDto {

    private Long id;

    private Double x;

    private Double y;

    private String name;

    private String image;

    private String detailContent;

    private DogSize dogSize;

    private Double rate;
}
