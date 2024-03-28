package com.dogventure.dogweb.dto.gpt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "chatgpt")
public class ChatgptProperties {

    private String apiKey;

    private String url;

    private String model;

    private Integer maxTokens = 500;

    private Double temperature = 1.0;

    private Double topP = 1.0;
}

