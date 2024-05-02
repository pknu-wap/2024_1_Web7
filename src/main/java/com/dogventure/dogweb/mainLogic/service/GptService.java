package com.dogventure.dogweb.mainLogic.service;

import com.dogventure.dogweb.dto.gpt.ChatgptProperties;
import com.dogventure.dogweb.dto.gpt.request.ChatRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Service
@RequiredArgsConstructor
public class GptService {

    private final ChatgptProperties chatgptProperties;
    private final WebClient webClient = WebClient.builder().build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SseEmitter sendMessage(String message) {
        SseEmitter emitter = new SseEmitter();

        ChatRequest chatRequest = new ChatRequest(
                chatgptProperties.getModel(),
                convertMessage(message),
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
                                    Thread.sleep(10);
                                }
                            }
                        }
                    } catch (Exception e) {
                        emitter.completeWithError(e);
                    }
                }, emitter::completeWithError);

        return emitter;
    }

    private String convertMessage(String message) {

        String baseMessage = "아래의 메시지에 대한 답변을 할 때, 강아지 말투를 사용해서 답변해줘. 예를 들면 말 끝마다 '멍!'을 붙이는 식으로.\n\n";

        return baseMessage + message;
    }
}