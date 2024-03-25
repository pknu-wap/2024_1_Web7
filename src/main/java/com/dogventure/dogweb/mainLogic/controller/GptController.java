package com.dogventure.dogweb.mainLogic.controller;

import com.dogventure.dogweb.mainLogic.service.GptService;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/gpt/")
public class GptController {

    private final ChatgptService service;

    @PostMapping("/send")
    public String sendMessage(@RequestBody String message) {
        return service.sendMessage(message);
    }
}
