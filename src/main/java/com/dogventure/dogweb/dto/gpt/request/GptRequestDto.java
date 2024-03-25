package com.dogventure.dogweb.dto.gpt.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GptRequestDto {

    private String model;

    private String prompt;

    private float temperature;
}
