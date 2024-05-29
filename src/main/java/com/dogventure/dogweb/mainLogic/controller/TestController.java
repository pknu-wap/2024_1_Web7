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
                         @RequestParam("image3")MultipartFile file3,

                         @RequestParam("image4")MultipartFile file4,
                         @RequestParam("image5")MultipartFile file5,
                         @RequestParam("image6")MultipartFile file6,

                         @RequestParam("image7")MultipartFile file7,
                         @RequestParam("image8")MultipartFile file8,
                         @RequestParam("image9")MultipartFile file9,

                         @RequestParam("image10")MultipartFile file10,
                         @RequestParam("image11")MultipartFile file11,
                         @RequestParam("image12")MultipartFile file12,

                         @RequestParam("image13")MultipartFile file13,
                         @RequestParam("image14")MultipartFile file14,
                         @RequestParam("image15")MultipartFile file15,

                         @RequestParam("image16")MultipartFile file16,
                         @RequestParam("image17")MultipartFile file17,
                         @RequestParam("image18")MultipartFile file18,

                         @RequestParam("image19")MultipartFile file19,
                         @RequestParam("image20")MultipartFile file20,
                         @RequestParam("image21")MultipartFile file21,

                         @RequestParam("image22")MultipartFile file22,
                         @RequestParam("image23")MultipartFile file23,
                         @RequestParam("image24")MultipartFile file24,

                         @RequestParam("image25")MultipartFile file25,
                         @RequestParam("image26")MultipartFile file26,
                         @RequestParam("image27")MultipartFile file27,

                         @RequestParam("image28")MultipartFile file28,
                         @RequestParam("image29")MultipartFile file29,
                         @RequestParam("image30")MultipartFile file30
                         ) throws IOException {

//        List<MultipartFile> multipartFiles = new ArrayList<>();
//        List<List<Image>> imageList = new ArrayList<>();
//        for (int i=0; i<30; i++) {
//
//        }
//
//        Image image1 = new Image(file1.getOriginalFilename(), file1.getBytes());
//        imageRepo.save(image1);
//        Image image2 = new Image(file2.getOriginalFilename(), file2.getBytes());
//        imageRepo.save(image2);
//        Image image3 = new Image(file3.getOriginalFilename(), file3.getBytes());
//        imageRepo.save(image3);
//
//        List<Image> images1 = new ArrayList<>();
//        images1.add(image1);
//        images1.add(image2);
//        images1.add(image3);
//
//
//        Image image4 = new Image(file4.getOriginalFilename(), file4.getBytes());
//        imageRepo.save(image4);
//        Image image5 = new Image(file5.getOriginalFilename(), file5.getBytes());
//        imageRepo.save(image5);
//        Image image6 = new Image(file6.getOriginalFilename(), file6.getBytes());
//        imageRepo.save(image6);
//
//        List<Image> images2 = new ArrayList<>();
//        images2.add(image4);
//        images2.add(image5);
//        images2.add(image6);
//
//
//        Image image7 = new Image(file7.getOriginalFilename(), file7.getBytes());
//        imageRepo.save(image7);
//        Image image8 = new Image(file8.getOriginalFilename(), file8.getBytes());
//        imageRepo.save(image8);
//        Image image9 = new Image(file9.getOriginalFilename(), file9.getBytes());
//        imageRepo.save(image9);
//
//        List<Image> images3 = new ArrayList<>();
//        images3.add(image7);
//        images3.add(image8);
//        images3.add(image9);
//
//
//        Image image10 = new Image(file10.getOriginalFilename(), file10.getBytes());
//        imageRepo.save(image10);
//        Image image11 = new Image(file11.getOriginalFilename(), file11.getBytes());
//        imageRepo.save(image11);
//        Image image12 = new Image(file12.getOriginalFilename(), file12.getBytes());
//        imageRepo.save(image12);
//
//        List<Image> images4 = new ArrayList<>();
//        images4.add(image10);
//        images4.add(image11);
//        images4.add(image12);
//
//
//        Image image13 = new Image(file13.getOriginalFilename(), file13.getBytes());
//        imageRepo.save(image13);
//        Image image14 = new Image(file14.getOriginalFilename(), file14.getBytes());
//        imageRepo.save(image14);
//        Image image15 = new Image(file15.getOriginalFilename(), file15.getBytes());
//        imageRepo.save(image15);
//
//        List<Image> images5 = new ArrayList<>();
//        images1.add(image13);
//        images1.add(image14);
//        images1.add(image15);
//
//
//        Image image16 = new Image(file16.getOriginalFilename(), file16.getBytes());
//        imageRepo.save(image16);
//        Image image17 = new Image(file17.getOriginalFilename(), file17.getBytes());
//        imageRepo.save(image17);
//        Image image18 = new Image(file18.getOriginalFilename(), file18.getBytes());
//        imageRepo.save(image18);
//
//        List<Image> images6 = new ArrayList<>();
//        images1.add(image16);
//        images1.add(image17);
//        images1.add(image18);
//
//
//        Image image19 = new Image(file19.getOriginalFilename(), file19.getBytes());
//        imageRepo.save(image19);
//        Image image20 = new Image(file20.getOriginalFilename(), file20.getBytes());
//        imageRepo.save(image20);
//        Image image21 = new Image(file21.getOriginalFilename(), file21.getBytes());
//        imageRepo.save(image21);
//
//        List<Image> images7 = new ArrayList<>();
//        images1.add(image19);
//        images1.add(image20);
//        images1.add(image21);
//
//
//        Image image22 = new Image(file22.getOriginalFilename(), file22.getBytes());
//        imageRepo.save(image22);
//        Image image23 = new Image(file23.getOriginalFilename(), file23.getBytes());
//        imageRepo.save(image23);
//        Image image24 = new Image(file24.getOriginalFilename(), file24.getBytes());
//        imageRepo.save(image24);
//
//        List<Image> images8 = new ArrayList<>();
//        images1.add(image22);
//        images1.add(image23);
//        images1.add(image24);
//
//
//        Image image25 = new Image(file25.getOriginalFilename(), file1.getBytes());
//        imageRepo.save(image1);
//        Image image2 = new Image(file2.getOriginalFilename(), file2.getBytes());
//        imageRepo.save(image2);
//        Image image3 = new Image(file3.getOriginalFilename(), file3.getBytes());
//        imageRepo.save(image3);
//
//        List<Image> images1 = new ArrayList<>();
//        images1.add(image1);
//        images1.add(image2);
//        images1.add(image3);
//
//
//        Image image1 = new Image(file1.getOriginalFilename(), file1.getBytes());
//        imageRepo.save(image1);
//        Image image2 = new Image(file2.getOriginalFilename(), file2.getBytes());
//        imageRepo.save(image2);
//        Image image3 = new Image(file3.getOriginalFilename(), file3.getBytes());
//        imageRepo.save(image3);
//
//        List<Image> images1 = new ArrayList<>();
//        images1.add(image1);
//        images1.add(image2);
//        images1.add(image3);
//        /*
//         * 동물 병원
//         */
//        Place place1 = new Place("헬로 동물병원", 35.1321, 129.1125, images1, LocalTime.of(10, 0), LocalTime.of(19, 0), "부산 남구 분포로 115 힐탑 탑플레이 A동 403호", "051-627-1275", "헬로 동물병원입니다, 편하게 찾아주세요^^", DogSize.BIG, PlaceType.HOSPITAL, 5.0);
//        placeRepo.save(place1);
//
//        /*
//         * 애견 카페
//         */
//        Place place2 = new Place("덕수네 애견", 35.137, 129.1005, images2, LocalTime.of(11, 30), LocalTime.of(21, 30), "대연동 55-6번지 4층 덕수네애견 남구 부산광역시 KR", "051-625-2956", "덕수네 애견카페입니다, 편하게 찾아주세요^^", DogSize.SMALL, PlaceType.CAFE, 5.0);
//        placeRepo.save(place2);
//
//        /*
//         * 애견 미용
//         */
//        Place place3 = new Place("애견 미용", 35.127, 129.1035, images3, LocalTime.of(11, 30), LocalTime.of(21, 30), "대연동 55-6번지 4층 덕수네애견 남구 부산광역시 KR", "051-625-2956", "덕수네 애견카페입니다, 편하게 찾아주세요^^", DogSize.MEDIUM, PlaceType.BEAUTY, 5.0);
//        placeRepo.save(place3);
//
//        /*
//         * 애견 용품
//         */
//        Place place4 = new Place("애견 용품", 35.107, 129.1095, images4, LocalTime.of(11, 30), LocalTime.of(21, 30), "대연동 55-6번지 4층 덕수네애견 남구 부산광역시 KR", "051-625-2956", "덕수네 애견카페입니다, 편하게 찾아주세요^^", DogSize.BIG, PlaceType.GOODS, 5.0);
//        placeRepo.save(place4);
    }

    @PostMapping("/")
    public String test() {
        return "ok";
    }
}
