package com.dogventure.dogweb.mainLogic.controller;

import com.dogventure.dogweb.dto.mainLogic.mypage.request.DogRequestDto;
import com.dogventure.dogweb.dto.mainLogic.mypage.request.MypageUpdateRequestDto;
import com.dogventure.dogweb.dto.mainLogic.mypage.response.MypageResponseDto;
import com.dogventure.dogweb.mainLogic.service.DogService;
import com.dogventure.dogweb.mainLogic.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MypageController {

    private final MypageService service;
    private final DogService dogService;

    @GetMapping()
    public ResponseEntity<MypageResponseDto> getMypage() {

        MypageResponseDto responseDto = service.getMypage();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity updateMypage(@RequestBody MypageUpdateRequestDto requestDto) {

        service.updateMypage(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/dog/image")
    public ResponseEntity setDogImage(@RequestParam("image") MultipartFile file) throws IOException {

        dogService.setImage(file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/dog/detail")
    public ResponseEntity setDogDetail(@RequestBody DogRequestDto requestDto) {

        dogService.setDetail(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
