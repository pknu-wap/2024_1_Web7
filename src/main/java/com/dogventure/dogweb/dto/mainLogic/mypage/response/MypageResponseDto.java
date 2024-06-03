package com.dogventure.dogweb.dto.mainLogic.mypage.response;

import com.dogventure.dogweb.dto.mainLogic.naverMap.response.ImageDto;
import com.dogventure.dogweb.mainLogic.entity.Dog;
import com.dogventure.dogweb.mainLogic.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MypageResponseDto {

    private String name;

    private String description;

    private Dog dog;

    private Integer count;
}
