package com.dogventure.dogweb.dto.mainLogic.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreateRequestDto {

    private String title;
    private String content;
    private String username;

}
