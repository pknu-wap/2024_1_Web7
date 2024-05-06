package com.dogventure.dogweb.mainLogic.controller;

import com.dogventure.dogweb.constant.DogSize;
import com.dogventure.dogweb.constant.PlaceType;
import com.dogventure.dogweb.mainLogic.entity.Image;
import com.dogventure.dogweb.mainLogic.entity.Place;
import com.dogventure.dogweb.mainLogic.repository.ImageRepository;
import com.dogventure.dogweb.mainLogic.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
@Transactional
public class TestController {

    private final PlaceRepository placeRepo;

    private final ImageRepository imageRepo;

    @PostMapping("/place/add")
    public void addPlace(@RequestParam("image1")MultipartFile file1,
                         @RequestParam("image2")MultipartFile file2,
                         @RequestParam("image3")MultipartFile file3) throws IOException {

        // 1.덕수네 애견
        Image image1 = new Image(file1.getOriginalFilename(), file1.getBytes());
        imageRepo.save(image1);
        Place place1 = new Place("덕수네 애견", 35.137, 129.1005, image1, LocalTime.of(11, 30), LocalTime.of(21, 30), "대연동 55-6번지 4층 덕수네애견 남구 부산광역시 KR", "051-625-2956", "덕수네 애견카페입니다, 편하게 찾아주세요^^", DogSize.BIG, PlaceType.CAFE, 5.0);
        placeRepo.save(place1);

        // 2.튼튼 동물병원
        Image image2 = new Image(file2.getOriginalFilename(), file2.getBytes());
        imageRepo.save(image2);
        Place place2 = new Place("튼튼 동물병원", 35.1384, 129.1024, image2, LocalTime.of(9, 30), LocalTime.of(19, 0), "부산광역시 남구 대연동 39-22", "051-621-7555", "튼튼 동물병원입니다, 편하게 찾아주세요^^", DogSize.MEDIUM, PlaceType.HOSPITAL, 5.0);
        placeRepo.save(place2);

        // 3.다온 동물병원
        Image image3 = new Image(file3.getOriginalFilename(), file3.getBytes());
        imageRepo.save(image3);
        Place place3 = new Place("다온 동물병원", 35.1404, 129.1042, image3, LocalTime.of(9, 30), LocalTime.of(19, 0), "부산광역시 남구 수영로 345 힐스테이트푸르지오 혁신상가 124호", "051-622-2171", "다온 동물병원입니다, 편하게 찾아주세요^^", DogSize.SMALL, PlaceType.HOSPITAL, 5.0);
        placeRepo.save(place3);
    }

    @PostMapping("/")
    public String test() {
        return "ok";
    }
}
