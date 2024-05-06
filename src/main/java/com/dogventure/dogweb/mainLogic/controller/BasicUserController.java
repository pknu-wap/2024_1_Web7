package com.dogventure.dogweb.mainLogic.controller;

import com.dogventure.dogweb.dto.mainLogic.login.request.SignupRequestDto;
import com.dogventure.dogweb.dto.mainLogic.login.response.TokenResponseDto;
import com.dogventure.dogweb.mainLogic.service.BasicUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/basic")
public class BasicUserController {

    private final BasicUserService service;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(HttpServletResponse response) throws IOException {

        TokenResponseDto tokenDto = service.login();
        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Valid SignupRequestDto request, HttpServletResponse response) throws IOException {

        service.signup(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        service.logout(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
