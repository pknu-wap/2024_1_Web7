package com.dogventure.dogweb.dto.mainLogic.naverMap.response;

import com.dogventure.dogweb.constant.DogSize;
import com.dogventure.dogweb.constant.PlaceType;
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

    private ImageDto image;

    private boolean isOpen;

    private String address;

    private String phoneNumber;

    private String detailContent;

    private PlaceType placeType;

    private DogSize dogSize;

    private Double rate;

    private List<ReviewDto> reviews;
}
