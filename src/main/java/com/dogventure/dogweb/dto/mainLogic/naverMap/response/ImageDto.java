package com.dogventure.dogweb.dto.mainLogic.naverMap.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageDto {

    private String filename;

    private byte[] data;
}
