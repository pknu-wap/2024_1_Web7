package com.dogventure.dogweb.mainLogic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GptConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
