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
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
@Transactional
public class TestController {

    private final PlaceRepository placeRepo;

    private final ImageRepository imageRepo;

    @PostMapping("/place/add")
    public void addPlace(@RequestParam Map<String, MultipartFile> files) throws IOException {

        List<MultipartFile> fileList = files.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        List<List<Image>> allImageLists = new ArrayList<>();

        for (int i = 0; i < fileList.size(); i += 3) {
            List<Image> images = new ArrayList<>();
            for (int j = i; j < i + 3 && j < fileList.size(); j++) {
                MultipartFile file = fileList.get(j);
                Image image = new Image(file.getOriginalFilename(), file.getBytes());
                imageRepo.save(image);
                images.add(image);
            }
            allImageLists.add(images);
        }


        /*
         * 동물 병원
         */
        Place place1 = new Place("튼튼 동물병원", 35.1383, 129.1024, allImageLists.get(0), LocalTime.of(9, 30), LocalTime.of(19, 0), "부산광역시 남구 대연동 39-22", "051-621-7555", "안녕하세요, 튼튼 동물병원입니다", DogSize.BIG, PlaceType.HOSPITAL, null);
        placeRepo.save(place1);

        Place place2 = new Place("더본외과 동물의료센터", 35.1395, 129.1053, allImageLists.get(1), LocalTime.of(10, 0), LocalTime.of(19, 0), "부산광역시 남구 수영로 364 4층", "051-711-7515", "안녕하세요, 더본외과 동물의료센터입니다", DogSize.BIG, PlaceType.HOSPITAL, null);
        placeRepo.save(place2);

        Place place3 = new Place("다온 동물병원", 35.1401, 129.1041, allImageLists.get(2), LocalTime.of(9, 30), LocalTime.of(19, 0), "부산광역시 남구 수영로 345 힐스테이트푸르지오 혁신상가 124호", "051-622-2171", "안녕하세요, 다온 동물병원입니다", DogSize.BIG, PlaceType.HOSPITAL, null);
        placeRepo.save(place3);

        Place place4 = new Place("더프라임 동물의료원", 35.1427, 129.1081, allImageLists.get(3), LocalTime.of(10, 30), LocalTime.of(19, 0), "부산광역시 수영구 수영로 405-1", "051-625-2345", "안녕하세요, 더프라임 동물의료원입니다", DogSize.BIG, PlaceType.HOSPITAL, null);
        placeRepo.save(place4);

        Place place5 = new Place("24시 UN동물의료센터", 35.1353, 129.0907, allImageLists.get(4), LocalTime.of(0, 1), LocalTime.of(0, 0), "부산광역시 남구 수영로 221", "051-624-2475", "안녕하세요, 24시 UN동물의료센터입니다", DogSize.BIG, PlaceType.HOSPITAL, null);
        placeRepo.save(place5);

        Place place6 = new Place("조양래 동물의료센터", 35.1348, 129.091, allImageLists.get(5), LocalTime.of(0, 1), LocalTime.of(0, 0), "부산광역시 남구 수영로 224-1", "051-621-8880", "안녕하세요, 조양래 동물의료센터입니다", DogSize.BIG, PlaceType.HOSPITAL, null);
        placeRepo.save(place6);

        Place place7 = new Place("W 동물의료센터", 35.1255, 129.111, allImageLists.get(6), LocalTime.of(10, 0), LocalTime.of(19, 0), "부산광역시 남구 용호로 68", "051-626-5050", "안녕하세요, W 동물의료센터입니다", DogSize.BIG, PlaceType.HOSPITAL, null);
        placeRepo.save(place7);

        Place place8 = new Place("바다 동물병원", 35.1527, 129.1161, allImageLists.get(7), LocalTime.of(10, 0), LocalTime.of(18, 30), "부산광역시 수영구 광남로 125 바다동물병원 1층", "051-756-0075", "안녕하세요, 바다 동물병원입니다", DogSize.BIG, PlaceType.HOSPITAL, null);
        placeRepo.save(place8);

        Place place9 = new Place("센텀 동물메디컬센터", 35.1684, 129.114, allImageLists.get(8), LocalTime.of(10, 0), LocalTime.of(20, 0), "부산광역시 수영구 연수로 407-1", "051-746-7582", "안녕하세요, 센텀 동물메디컬센터입니다", DogSize.BIG, PlaceType.HOSPITAL, null);
        placeRepo.save(place9);

        Place place10 = new Place("문현 동물병원", 35.1365, 129.0717, allImageLists.get(9), LocalTime.of(9, 30), LocalTime.of(19, 30), "부산광역시", "051-634-4017", "안녕하세요, 문현 동물병원입니다", DogSize.BIG, PlaceType.HOSPITAL, null);
        placeRepo.save(place10);

        Place place11 = new Place("다솜고양이 메디컬센터", 35.1373, 129.0693, allImageLists.get(10), LocalTime.of(10, 0), LocalTime.of(22, 0), "부산광역시 남구 수영로13번길 3", "051-632-7580", "안녕하세요, 다솜고양이 메디컬센터입니다", DogSize.BIG, PlaceType.HOSPITAL, null);
        placeRepo.save(place11);


        /*
         * 애견 카페
         */
        Place place12 = new Place("도그민", 35.1548, 129.1201, allImageLists.get(11), LocalTime.of(11, 0), LocalTime.of(21, 0), "부산광역시 수영구 민락동 179-1번지 6층", "0507-1306-6720", "안녕하세요, 도그민입니다", DogSize.SMALL, PlaceType.CAFE, null);
        placeRepo.save(place12);

        Place place13 = new Place("FC일석이조", 35.1358, 129.1002, allImageLists.get(12), LocalTime.of(13, 0), LocalTime.of(22, 30), "부산광역시 남구", "051-612-2571", "안녕하세요, FC일석이조입니다", DogSize.SMALL, PlaceType.CAFE, null);
        placeRepo.save(place13);

        Place place14 = new Place("또또애견", 35.1425, 129.0562, allImageLists.get(13), LocalTime.of(12, 0), LocalTime.of(21, 0), "부산광역시 부산진구 신암로 9", "051-633-7789", "안녕하세요, 또또애견입니다", DogSize.SMALL, PlaceType.CAFE, null);
        placeRepo.save(place14);

        Place place15 = new Place("홀킷", 35.1555, 129.066, allImageLists.get(14), LocalTime.of(0, 1), LocalTime.of(0, 0), "부산광역시 부산진구 서전로58번길 48 3층", "010-6292-0678", "안녕하세요, 홀킷입니다", DogSize.SMALL, PlaceType.CAFE, null);
        placeRepo.save(place15);

        Place place16 = new Place("개라모르겠다", 35.155, 129.0631, allImageLists.get(15), LocalTime.of(12, 0), LocalTime.of(22, 0), "부산광역시 부산진구 전포대로 209번길 39-9호 2층", "051-804-1435", "안녕하세요, 개라모르겠다입니다", DogSize.SMALL, PlaceType.CAFE, null);
        placeRepo.save(place16);

        Place place17 = new Place("플루오", 35.1589, 129.0641, allImageLists.get(16), LocalTime.of(12, 0), LocalTime.of(20, 30), "부산광역시 부산진구 서전로37번길 26 3층", "010-4464-7919", "안녕하세요, 플루오입니다", DogSize.SMALL, PlaceType.CAFE, null);
        placeRepo.save(place17);

        Place place18 = new Place("카페개네 서면점", 35.1589, 129.0641, allImageLists.get(17), LocalTime.of(12, 0), LocalTime.of(20, 30), "부산광역시 부산진구 서전로37번길 26 3층", "010-4464-7919", "안녕하세요, 플루오입니다", DogSize.SMALL, PlaceType.CAFE, null);
        placeRepo.save(place18);

        Place place19 = new Place("월월월", 35.1805, 129.0882, allImageLists.get(18), LocalTime.of(12, 0), LocalTime.of(21, 0), "부산광역시 연제구 쌍미천로84번길 6 월월월앳더모먼츠", "010-7584-7599", "안녕하세요, 월월월입니다", DogSize.SMALL, PlaceType.CAFE, null);
        placeRepo.save(place19);

        Place place20 = new Place("아껴줄개", 35.192, 129.1, allImageLists.get(19), LocalTime.of(12, 0), LocalTime.of(21, 0), "부산광역시 동래구 온천천로 463", "051-532-2232", "안녕하세요, 아껴줄개입니다", DogSize.SMALL, PlaceType.CAFE, null);
        placeRepo.save(place20);

        Place place21 = new Place("덕수네 애견카페", 35.0993, 129.0317, allImageLists.get(20), LocalTime.of(12, 0), LocalTime.of(21, 0), "부산광역시 중구 광복동2가", "051-245-2956", "안녕하세요, 덕수네 애견카페입니다", DogSize.SMALL, PlaceType.CAFE, null);
        placeRepo.save(place21);

        /*
         * 애견 미용
         */
        Place place22 = new Place("J@야니스타일 애견미용", 35.148, 129.0573, allImageLists.get(21), LocalTime.of(13, 0), LocalTime.of(21, 0), "부산광역시 동구 J@ 야니스타일", "051-442-5750", "안녕하세요, J@야니스타일 애견미용입니다", DogSize.SMALL, PlaceType.BEAUTY, null);
        placeRepo.save(place22);

        Place place23 = new Place("미소애견미용실", 35.1571, 129.054, allImageLists.get(22), LocalTime.of(10, 0), LocalTime.of(18, 0), "부산광역시 부산진구 가야대로 754 1층", "051-894-2009", "안녕하세요, 미소애견미용실입니다", DogSize.SMALL, PlaceType.BEAUTY, null);
        placeRepo.save(place23);

        Place place24 = new Place("메리몰애견숍", 35.1076, 129.0341, allImageLists.get(23), LocalTime.of(10, 0), LocalTime.of(20, 30), "부산광역시 중구 동광동5가 9-48", "051-469-4948", "안녕하세요, 메리몰애견숍입니다", DogSize.SMALL, PlaceType.BEAUTY, null);
        placeRepo.save(place24);

        Place place25 = new Place("행복애견미용실", 35.1702, 129.0677, allImageLists.get(24), LocalTime.of(10, 30), LocalTime.of(20, 0), "부산광역시", "051-867-8483", "안녕하세요, 행복애견미용실입니다", DogSize.SMALL, PlaceType.BEAUTY, null);
        placeRepo.save(place25);

        Place place26 = new Place("펫코스", 35.1646, 129.1676, allImageLists.get(25), LocalTime.of(9, 0), LocalTime.of(19, 0), "부산광역시 해운대구 해운대해변로357번길 17 3층", "051-710-2002", "안녕하세요, 펫코스입니다", DogSize.SMALL, PlaceType.BEAUTY, null);
        placeRepo.save(place26);

        Place place27 = new Place("핑코애견", 35.1733, 129.1701, allImageLists.get(26), LocalTime.of(9, 0), LocalTime.of(20, 0), "부산광역시 해운대구 좌제3동", "051-945-2800", "안녕하세요, 핑코애견입니다", DogSize.SMALL, PlaceType.BEAUTY, null);
        placeRepo.save(place27);

//        Place place28 = new Place("지니펫살롱", 35.2247, 129.0821, allImageLists.get(26), LocalTime.of(9, 0), LocalTime.of(20, 0), "부산광역시 해운대구 좌제3동", "051-945-2800", "안녕하세요, 핑코애견입니다", DogSize.SMALL, PlaceType.BEAUTY, null);
//        placeRepo.save(place28);


        /*
         * 애견 용품
         */
//        Place place4 = new Place("애견 용품", 35.107, 129.1095, images4, LocalTime.of(11, 30), LocalTime.of(21, 30), "대연동 55-6번지 4층 덕수네애견 남구 부산광역시 KR", "051-625-2956", "덕수네 애견카페입니다, 편하게 찾아주세요^^", DogSize.BIG, PlaceType.GOODS, null);
//        placeRepo.save(place4);
    }

    @PostMapping("/")
    public String test() {
        return "ok";
    }
}
