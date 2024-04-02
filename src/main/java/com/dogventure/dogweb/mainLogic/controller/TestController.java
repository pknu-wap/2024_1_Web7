package com.dogventure.dogweb.mainLogic.controller;

import com.dogventure.dogweb.constant.DogSize;
import com.dogventure.dogweb.constant.PlaceType;
import com.dogventure.dogweb.mainLogic.entity.Image;
import com.dogventure.dogweb.mainLogic.entity.Place;
import com.dogventure.dogweb.mainLogic.repository.ImageRepository;
import com.dogventure.dogweb.mainLogic.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {

    private final PlaceRepository placeRepo;

    private final ImageRepository imageRepo;

    @PostMapping("/place/add")
    public void addPlace(@RequestParam("image")MultipartFile file) throws IOException {
//        Image image = new Image(file.getOriginalFilename(), file.getBytes());
//        imageRepo.save(image);
//        Place place = new Place("재미있는카페", 1.3542, 2.9882, image, "재밌는 애견카페입니다", DogSize.BIG, PlaceType.CAFE, 4.6);
//        placeRepo.save(place);
    }
}
