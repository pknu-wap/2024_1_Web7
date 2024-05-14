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
                         @RequestParam("image3")MultipartFile file3,
                         @RequestParam("image3")MultipartFile file4,
                         @RequestParam("image3")MultipartFile file5,
                         @RequestParam("image3")MultipartFile file6,
                         @RequestParam("image3")MultipartFile file7,
                         @RequestParam("image3")MultipartFile file8,
                         @RequestParam("image3")MultipartFile file9,
                         @RequestParam("image3")MultipartFile file10) throws IOException {

        /*
         * 동물 병원
         */
        // 1.헬로 동물병원
        Image image1 = new Image(file1.getOriginalFilename(), file1.getBytes());
        imageRepo.save(image1);
        Place place1 = new Place("헬로 동물병원", 35.1321, 129.1125, image1, LocalTime.of(10, 0), LocalTime.of(19, 0), "부산 남구 분포로 115 힐탑 탑플레이 A동 403호", "051-627-1275", "헬로 동물병원입니다, 편하게 찾아주세요^^", DogSize.BIG, PlaceType.HOSPITAL, 5.0);
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

        // 4.더본 외과 동물의료센터
        Image image4 = new Image(file4.getOriginalFilename(), file4.getBytes());
        imageRepo.save(image4);
        Place place4 = new Place("더본 외과 동물의료센터", 35.1394, 129.1052, image4, LocalTime.of(10, 0), LocalTime.of(19, 0), "부산광역시 남구 수영로 364 4층", "051-711-7515", " 더본외과 동물의료센터입니다, 편하게 찾아주세요^^", DogSize.BIG, PlaceType.HOSPITAL, 5.0);
        placeRepo.save(place4);

        // 5.웰케어 동물건검진센터/동물한방 재활센터
        Image image5 = new Image(file5.getOriginalFilename(), file5.getBytes());
        imageRepo.save(image5);
        Place place5 = new Place("웰케어 동물건검진센터/동물한방 재활센터", 35.1394, 129.1052, image5, LocalTime.of(9, 0), LocalTime.of(19, 0), "부산광역시 남구 수영로 364 3층", "051-711-7514", "웰케어 동물건검진센터/동물한방 재활센터, 편하게 찾아주세요^^", DogSize.MEDIUM, PlaceType.HOSPITAL, 5.0);
        placeRepo.save(place5);

        // 6.남천 동물병원
        Image image6 = new Image(file6.getOriginalFilename(), file6.getBytes());
        imageRepo.save(image6);
        Place place6 = new Place("남천 동물병원", 35.1377, 129.1086, image6, LocalTime.of(9, 30), LocalTime.of(19, 0), "부산광역시 남구 수영로 황령대 505", "051-622-0046", "남천 동물병원입니다, 편하게 찾아주세요^^", DogSize.SMALL, PlaceType.HOSPITAL, 5.0);
        placeRepo.save(place6);

        /*
         * 애견 카페
         */
        // 7.덕수네 애견
        Image image7 = new Image(file7.getOriginalFilename(), file7.getBytes());
        imageRepo.save(image7);
        Place place7 = new Place("덕수네 애견", 35.137, 129.1005, image7, LocalTime.of(11, 30), LocalTime.of(21, 30), "대연동 55-6번지 4층 덕수네애견 남구 부산광역시 KR", "051-625-2956", "덕수네 애견카페입니다, 편하게 찾아주세요^^", DogSize.BIG, PlaceType.CAFE, 5.0);
        placeRepo.save(place7);

        // 8.11월4일
        Image image8 = new Image(file8.getOriginalFilename(), file8.getBytes());
        imageRepo.save(image8);
        Place place8 = new Place("11월 4일", 35.1192, 129.1156, image8, LocalTime.of(12, 0), LocalTime.of(19, 0), "부산 남구 동명로 169번 8 1층", "0507-1433-0147", "11월 4일입니다, 편하게 찾아주세요^^", DogSize.SMALL, PlaceType.CAFE, 5.0);
        placeRepo.save(place8);

        // 9.도그민
        Image image9 = new Image(file9.getOriginalFilename(), file9.getBytes());
        imageRepo.save(image9);
        Place place9 = new Place("도그민", 35.1547, 129.12, image9, LocalTime.of(11, 0), LocalTime.of(22, 0), "부산 수영구 광안해변로 239 샤인빌딩 6층", "0507-1306-6720", "도그민입니다, 편하게 찾아주세요^^", DogSize.MEDIUM, PlaceType.CAFE, 5.0);
        placeRepo.save(place9);

        // 10.마요네즈 애견유치원
        Image image10 = new Image(file10.getOriginalFilename(), file10.getBytes());
        imageRepo.save(image10);
        Place place10 = new Place("마요네즈 애견유치원", 35.156, 129.1186, image10, LocalTime.of(10, 0), LocalTime.of(21, 0), "부산 수영구 광남로 165 2층", "0507-1473-9033", "마요네즈 애견유치원입니다, 편하게 찾아주세요^^", DogSize.MEDIUM, PlaceType.CAFE, 5.0);
        placeRepo.save(place10);
    }

    @PostMapping("/")
    public String test() {
        return "ok";
    }
}
