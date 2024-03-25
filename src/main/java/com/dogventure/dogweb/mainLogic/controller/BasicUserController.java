package com.dogventure.dogweb.mainLogic.controller;

import com.dogventure.dogweb.dto.mainLogic.request.SignupRequestDto;
import com.dogventure.dogweb.dto.mainLogic.response.TokenResponseDto;
import com.dogventure.dogweb.mainLogic.service.BasicUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/basic")
public class BasicUserController {

    private final BasicUserService service;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login() {

        TokenResponseDto tokenDto = service.login();
        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Valid SignupRequestDto request) {

        service.signup(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
