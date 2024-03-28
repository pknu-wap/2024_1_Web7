package com.dogventure.dogweb.mainLogic.service;

import com.dogventure.dogweb.dto.gpt.ChatgptProperties;
import com.dogventure.dogweb.dto.gpt.request.ChatRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GptService {

    private final ChatgptProperties chatgptProperties;

    private final WebClient webClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public SseEmitter sendMessage(String message) {
        SseEmitter emitter = new SseEmitter();

        ChatRequest chatRequest = new ChatRequest(
                chatgptProperties.getModel(),
                message,
                chatgptProperties.getMaxTokens(),
                chatgptProperties.getTemperature(),
                chatgptProperties.getTopP()
        );

        webClient.post()
                .uri(chatgptProperties.getUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + chatgptProperties.getApiKey())
                .body(BodyInserters.fromValue(chatRequest))
                .retrieve()
                .bodyToFlux(String.class)
                .subscribe(data -> {
                    try {
                        if ("[DONE]".equals(data.trim())) {
                            emitter.complete();
                            return;
                        }

                        JsonNode rootNode = objectMapper.readTree(data);
                        JsonNode choicesNode = rootNode.path("choices");
                        if (choicesNode.isArray()) {
                            for (JsonNode choice : choicesNode) {
                                String text = choice.path("text").asText();
                                for (char c : text.toCharArray()) {
                                    emitter.send(SseEmitter.event().data(String.valueOf(c)));
                                    Thread.sleep(50);
                                }
                            }
                        }
                    } catch (Exception e) {
                        emitter.completeWithError(e);
                    }
                }, emitter::completeWithError);

        return emitter;
    }
}