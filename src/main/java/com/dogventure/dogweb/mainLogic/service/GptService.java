package com.dogventure.dogweb.mainLogic.service;

import com.dogventure.dogweb.dto.gpt.request.ChatRequest;
import com.dogventure.dogweb.dto.gpt.ChatgptProperties;
import com.dogventure.dogweb.dto.gpt.response.ChatResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class GptService {

    private final ChatgptProperties chatgptProperties;

    private final RestTemplate restTemplate = new RestTemplate();

    public String sendMessage(String message) {

        ChatRequest chatRequest = new ChatRequest(chatgptProperties.getModel(), message, chatgptProperties.getMaxTokens(), chatgptProperties.getTemperature(), chatgptProperties.getTopP());
        ChatResponse chatResponse = this.getResponse(this.buildHttpEntity(chatRequest), ChatResponse.class, chatgptProperties.getUrl());
        try {
            return chatResponse.getChoices().get(0).getText();
        } catch (Exception e) {
            log.error("parse chatgpt message error", e);
            throw e;
        }
    }

    protected <T> HttpEntity<?> buildHttpEntity(T request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        headers.add("Authorization", "Bearer "+chatgptProperties.getApiKey());
        return new HttpEntity<>(request, headers);
    }

    private  <T> T getResponse(HttpEntity<?> httpEntity, Class<T> responseType, String url) {

        ResponseEntity<T> responseEntity = restTemplate.postForEntity(url, httpEntity, responseType);
        if (responseEntity.getStatusCodeValue() != HttpStatus.OK.value()) {
            throw new RuntimeException();
        }

        return responseEntity.getBody();
    }
}
