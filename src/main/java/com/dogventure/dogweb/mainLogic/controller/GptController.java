package com.dogventure.dogweb.mainLogic.controller;

import com.dogventure.dogweb.mainLogic.service.GptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gpt")
public class GptController {

    private final GptService service;

    @PostMapping("/question")
    public ResponseEntity<SseEmitter> sendMessage(@RequestBody String prompt) {

        SseEmitter sseEmitter = service.sendMessage(prompt);
        return new ResponseEntity<>(sseEmitter, HttpStatus.OK);
    }
}
