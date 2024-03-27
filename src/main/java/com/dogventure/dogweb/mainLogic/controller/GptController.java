package com.dogventure.dogweb.mainLogic.controller;

import com.dogventure.dogweb.mainLogic.service.GptService;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gpt")
public class GptController {

    private final GptService service;

    @PostMapping("/question")
    public String sendMessage(@RequestBody String prompt) {
        return service.sendMessage(prompt);
    }
}
