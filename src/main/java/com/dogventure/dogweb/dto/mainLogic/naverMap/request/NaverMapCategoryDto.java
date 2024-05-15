package com.dogventure.dogweb.dto.mainLogic.naverMap.request;

import com.dogventure.dogweb.constant.DogSize;
import com.dogventure.dogweb.constant.PlaceType;
import lombok.Getter;

@Getter
public class NaverMapCategoryDto {

    private PlaceType placeType;

    private DogSize dogSize;
}
