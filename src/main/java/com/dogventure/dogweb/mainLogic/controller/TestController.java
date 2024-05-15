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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

        Image image1 = new Image(file1.getOriginalFilename(), file1.getBytes());
        imageRepo.save(image1);
        Image image2 = new Image(file2.getOriginalFilename(), file2.getBytes());
        imageRepo.save(image2);
        Image image3 = new Image(file3.getOriginalFilename(), file3.getBytes());
        imageRepo.save(image3);

        List<Image> images1 = new ArrayList<>();
        images1.add(image1);
        images1.add(image2);
        images1.add(image3);


        Image image4 = new Image(file1.getOriginalFilename(), file1.getBytes());
        imageRepo.save(image1);
        Image image5 = new Image(file2.getOriginalFilename(), file2.getBytes());
        imageRepo.save(image2);
        Image image6 = new Image(file3.getOriginalFilename(), file3.getBytes());
        imageRepo.save(image3);

        List<Image> images2 = new ArrayList<>();
        images2.add(image4);
        images2.add(image5);
        images2.add(image6);


        Image image7 = new Image(file1.getOriginalFilename(), file1.getBytes());
        imageRepo.save(image1);
        Image image8 = new Image(file2.getOriginalFilename(), file2.getBytes());
        imageRepo.save(image2);
        Image image9 = new Image(file3.getOriginalFilename(), file3.getBytes());
        imageRepo.save(image3);

        List<Image> images3 = new ArrayList<>();
        images3.add(image7);
        images3.add(image8);
        images3.add(image9);


        Image image10 = new Image(file1.getOriginalFilename(), file1.getBytes());
        imageRepo.save(image1);
        Image image11 = new Image(file2.getOriginalFilename(), file2.getBytes());
        imageRepo.save(image2);
        Image image12 = new Image(file3.getOriginalFilename(), file3.getBytes());
        imageRepo.save(image3);

        List<Image> images4 = new ArrayList<>();
        images4.add(image10);
        images4.add(image11);
        images4.add(image12);

        /*
         * 동물 병원
         */
        Place place1 = new Place("헬로 동물병원", 35.1321, 129.1125, images1, LocalTime.of(10, 0), LocalTime.of(19, 0), "부산 남구 분포로 115 힐탑 탑플레이 A동 403호", "051-627-1275", "헬로 동물병원입니다, 편하게 찾아주세요^^", DogSize.BIG, PlaceType.HOSPITAL, 5.0);
        placeRepo.save(place1);

        /*
         * 애견 카페
         */
        Place place2 = new Place("덕수네 애견", 35.137, 129.1005, images2, LocalTime.of(11, 30), LocalTime.of(21, 30), "대연동 55-6번지 4층 덕수네애견 남구 부산광역시 KR", "051-625-2956", "덕수네 애견카페입니다, 편하게 찾아주세요^^", DogSize.SMALL, PlaceType.CAFE, 5.0);
        placeRepo.save(place2);

        /*
         * 애견 미용
         */
        Place place3 = new Place("애견 미용", 35.127, 129.1035, images3, LocalTime.of(11, 30), LocalTime.of(21, 30), "대연동 55-6번지 4층 덕수네애견 남구 부산광역시 KR", "051-625-2956", "덕수네 애견카페입니다, 편하게 찾아주세요^^", DogSize.MEDIUM, PlaceType.BEAUTY, 5.0);
        placeRepo.save(place3);

        /*
         * 애견 용품
         */
        Place place4 = new Place("애견 용품", 35.107, 129.1095, images4, LocalTime.of(11, 30), LocalTime.of(21, 30), "대연동 55-6번지 4층 덕수네애견 남구 부산광역시 KR", "051-625-2956", "덕수네 애견카페입니다, 편하게 찾아주세요^^", DogSize.BIG, PlaceType.GOODS, 5.0);
        placeRepo.save(place4);
    }

    @PostMapping("/")
    public String test() {
        return "ok";
    }
}
