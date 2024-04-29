package com.dogventure.dogweb.dto.mainLogic.mypage.response;

import com.dogventure.dogweb.dto.mainLogic.naverMap.response.ImageDto;
import com.dogventure.dogweb.mainLogic.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MypageResponseDto {

    private ImageDto image;

    private String name;

    private String description;
}
