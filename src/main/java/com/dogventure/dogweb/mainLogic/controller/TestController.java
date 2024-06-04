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

        Place place2 = new Place("더본외과 동물의료센터", 35.1395, 129.1053, allImageLists.get(1), LocalTime.of(10, 0), LocalTime.of(19, 0), "부산광역시 남구 수영로 364 4층", "051-711-7515", "더본외과동물의료센터는 보다 전문적인 외과 진료를 위해 외과 치료에 최적화되어 있는 부산 경남 최초 수술 전문 동물병원입니다.  부산 최초 유일하게 무균 상태 상시 유지되어 있는 무균&양압 수술실 시스템이 갖추어져 있고 대학 병원급 CT, MRI 영상 시스템이 도입되어 진단부터 수술까지 원스톱으로 이루어지고 있습니다. 또한, 외과 진료에 좀 더 전문적인 의료 서비스를 위해 숙련된 외과 박사 의료진들이 항시 대기 중이며 안주하지 않고 발전할 수 있는 동물병원이 되기 위해 끊임없이 연구하며 정진하고 있습니다.  골관절센터 / 척추신경센터 / 종양항암센터 / 재활센터 / 영상진단센터 총 5가지의 센터를 갖추고 있습니다.  01. 골관절센터 골관절 센터에서는 슬개골 탈구, 십자인대 파열과 같은 관절에 생기는 문제를 정밀검사를 통해서 정확하게 파악하고 치료를 하고 있습니다.  02. 척추신경센터 척추 신경 센터는 목, 허리 디스크 등으로 인해 나타나는 신경 증상을 치료하는 센터입니다. 최첨단 CT와 MRI로 정확한 문제를 발견하고 치료를 하고 있습니다.  03. 종양항암센터 더본 외과동물의료센터는 외과박사 3인이 모여 최첨단 장비들로 종양을 발견하고 치료하며 예방하고 있습니다.  04. 재활센터 재활 프로그램을 통하여 반려동물의 통증을 감소 시키고 손상된 근골격계 및 신경계의 기능을 회복, 강화 시키고 있습니다.  05. 영상진단센터(MRI/CT/초음파) 더본 외과동물의료센터는 내,외과 구별 없이 모든 질병에 대한 영상 촬영, 진단 서비스를 제공하며 진단 후 최적의 치료 계획을 세웁니다.", DogSize.BIG, PlaceType.HOSPITAL, null);
        placeRepo.save(place2);

        Place place3 = new Place("다온 동물병원", 35.1401, 129.1041, allImageLists.get(2), LocalTime.of(9, 30), LocalTime.of(19, 0), "부산광역시 남구 수영로 345 힐스테이트푸르지오 혁신상가 124호", "051-622-2171", "정직한 진료를 최우선으로 하는 다온동물병원입니다  전북대와 충남대 동물병원 대표 진료 수의사 2차 동물병원 (대구동물메디컬센터)에서 오랜 진료 경험을 바탕으로, 언제나 정직한 진료, 실력과 정성으로 보답하는 의료진이 되기 위해 노력하고 있습니다  대표원장- 김진원 김자원 홍나래  진료과목: 내과 종양 항암치료, 고양이 진료 수술 특화  평일진료: 오전 9시반- 오후 7시까지(6시30분접수마감) 토, 공휴일: 오전 9시반- 오후 6시까지(5시접수마감) 점심시간: 오전 12시30분~오후 1시30분", DogSize.BIG, PlaceType.HOSPITAL, null);
        placeRepo.save(place3);

        Place place4 = new Place("더프라임 동물의료원", 35.1427, 129.1081, allImageLists.get(3), LocalTime.of(10, 30), LocalTime.of(19, 0), "부산광역시 수영구 수영로 405-1", "051-625-2345", "함께하는 반려동물의 행복과 건강을 위해 항상 노력하고 고민하며 발전하는 양/한방협진 더프라임동물의료원입니다.  - 숙련된 노하우를 갖춘 전문 의료진과 스텝이 항시 상주하고 있습니다. - 정밀한 검사를 위해 대학병원급의 첨단의료장비를 보유하고 있습니다. - 야간 응급진료 및 공휴일에도 진료가 가능합니다. - 주차장을 갖추고 있기에 언제든지 원활한 방문이 가능합니다.  <더프라임동물의료원 진료과목> - 한방과 : 디스크, 아토피, 재활치료 등 ※ 모든 양한방협진 진료는 예약 후 진료가 가능합니다. ※ - 정형외과 / 외과 : 슬개골탈구, 대퇴골탈구, 복합골절교정 등 각종 외과 전문 수술 - 내과 : 전염병, 설사, 구토 등 소화기계 질환 및 각종 내과중증질환 - 안과 : 3안검탈출증교정, 안충 제거, 안검플랩, 안구 적출, 각막조직제거 등 - 산부인과 : 임신 관리 및 출산, 제왕절개술, 산부인과 질환 등 - 신경계과 : 발작, 경련, 마비, 신경증상 등 - 치과 : 발치, 스케일링, 치아관리 등  <더프라임동물의료원 부설> * 슬개골 교정 센터 * * 한방 디스크치료 센터 * * 노령 동물 관리 클리닉 * * 고양이 맞춤 전문 진료 클리닉 * * 반려동물 전문 미용 *  <진료시간> 월요일 ~ 토요일 : AM 10:30~PM 07:00 공휴일 : AM 10:30~PM 05:00 일요일 휴진 (점심시간 : PM 01:00~PM 02:30) ※ 모든 야간진료는 전화 상담 후 진료 가능 ※  ** 카카오톡 '더프라임동물의료원' 검색 후 친구 추가! ** 진료 일정 및 다양한 병원 소식을 확인할 수 있으며, 1:1 자세한 상담 및 예약이 가능합니다.  - 모든 내용의 자세한 설명은 블로그에서 확인하실 수 있습니다. -", DogSize.BIG, PlaceType.HOSPITAL, null);
        placeRepo.save(place4);

        Place place5 = new Place("24시 UN동물의료센터", 35.1353, 129.0907, allImageLists.get(4), LocalTime.of(0, 1), LocalTime.of(0, 0), "부산광역시 남구 수영로 221", "051-624-2475", "부산 남구 대연동에 위치한 24시 UN동물의료센터는 석,박사 출신의 전문 의료진을 포함한 30여명 의료진들이 반려동물과 행복한 동행을 위해 2016년에 개원되었습니다.  1,2,3층 160여평의 단독 병원으로 의료 장비에 대한 투자를 끊임없이 하고 있으며 심장신장/내분비특화센터, 외과수술센터, 고양이친화센터, 영상진단센터, 야간응급센터를 갖추고 있습니다.  [365일 24시간 동행ㅣ야간응급센터] 야간 전담 의료진이 상주하여 위급한 상황에 응급 진료가 가능하며 책임 간호제가 도입되어 입원 중인 아픈 아이들을 24시간 집중 케어하고 있습니다.  [심장신장/내분비 특화센터] 수의학 내과 박사 대표원장님이 책임지고 있으며 폭 넓은 내과 질환들을 다루고 있습니다. 심장/순환기 질환에 특화된 최첨단 장비(심장초음파, 신장투석기, 산소자동공급시스템 등)를 보유하고 있으며 중환자를 집중 치료 할 수 있도록 정밀하고 체계적인 진료 시스템이 구축되어 있습니다.  [외과수술센터] 슬개골탈구, 십자인대, 골절, 디스크 등 외과 수술이 가능하며 외과학 박사 대표원장님의 정확한 진단부터 수술, 회복까지 체계적인 의료 서비스를 제공하고 있습니다.  [고양이친화센터] 고양이의학에 가장 권위 있는 단체 국제고양이수의사회(ISFM)에서 고양이친화병원 GOLD 등급을 인증 받은 곳입니다. 고양이 전문 대표원장님의 특화된 진료가 가능하며 강아지와 독립된 대기실 및 진료 공간에서 의료 서비스를 받으실 수 있습니다.  [영상진단센터] 내외과 구분 없이 모든 질병에 대한 정확한 진단을 위해 최고사양 CT/MRI 검사가 가능합니다. 영상의학과 전공의 원장님이 영상 판독 및 진단을 내려줍니다.  [진료시간] 주간진료 10:00 ~ 21:00 야간진료 21:00 ~ 24:00 심야진료 24:00 ~ 08:30 점심시간 12:30 ~ 14:00  [면회시간] 강아지 11:00 ~ 19:00 고양이 11:00 ~ 18:30 (점심시간 제외)", DogSize.MEDIUM, PlaceType.HOSPITAL, null);
        placeRepo.save(place5);

        Place place6 = new Place("조양래 동물의료센터", 35.1348, 129.091, allImageLists.get(5), LocalTime.of(0, 1), LocalTime.of(0, 0), "부산광역시 남구 수영로 224-1", "051-621-8880", "사람과 반려동물의 건강하고 행복한 동행  조양래동물의료센터를 찾아주셔서 감사합니다. 저희 의료진은 보호자님의 반려동물을 사랑과 정성으로 진료할 것을 약속드립니다.  - 석, 박사 의료진을 비롯한 40여명의 스텝들 간의 상호 체계적이고 전문화된 진료 시스템 - 내과, 외과, 영상의학과 협진을 통한 분과 진료 - 국내를 넘어 세계적으로 인정받는 최고사양의 동물전용 동물혈액분석장비 마취기 환자모니터기 도입 - 연간 2000건 이상의 안전한 수술 경력 - 최고사양 치과 전문 치료장비 full setting - 안락한 진료 환경을 위해 국내 최초 강아지/고양이 진료 공간 분리 (2F강아지전문클리닉/3F고양이전문클리닉 운영)  * 부설센터 : 동물내시경센터, 동물혈액분석연구소, 동물암진단센터, 수의치과전문센터", DogSize.MEDIUM, PlaceType.HOSPITAL, null);
        placeRepo.save(place6);

        Place place7 = new Place("W 동물의료센터", 35.1255, 129.111, allImageLists.get(6), LocalTime.of(10, 0), LocalTime.of(19, 0), "부산광역시 남구 용호로 68", "051-626-5050", "반려동물의 위한 새로운 선택 양방 / 한방 / 재활 유기적인 협력 시스템 정성 정직 정확한 진료  (진료과목) 예방의학과 / 외과 / 내과 / 한방외과 / 한방내과 / 재활의학과 / 방사선과  (특화진료) * 피부 특화진료센터 ( 재발성 피부병 / 노령성 피부병 / 난치성 피부병 )  * W한방동물병원 ( 디스크 / 마비/ 신경성질환 / 만성 질환 / 난치성 피부병 )  * W재활동물병원 ( 수술 후 재활 / 비수술적 재활 치료 / 노령견 운동 센터 )  * W척추관절센터 ( 수술, 비수술 척추 관절 치료 / 통증클리닉 )  * W치과전문클리닉", DogSize.MEDIUM, PlaceType.HOSPITAL, null);
        placeRepo.save(place7);

        Place place8 = new Place("바다 동물병원", 35.1527, 129.1161, allImageLists.get(7), LocalTime.of(10, 0), LocalTime.of(18, 30), "부산광역시 수영구 광남로 125 바다동물병원 1층", "051-756-0075", "안녕하세요, 바다 동물병원입니다", DogSize.MEDIUM, PlaceType.HOSPITAL, null);
        placeRepo.save(place8);

        Place place9 = new Place("센텀 동물메디컬센터", 35.1684, 129.114, allImageLists.get(8), LocalTime.of(10, 0), LocalTime.of(20, 0), "부산광역시 수영구 연수로 407-1", "051-746-7582", "- 365일 연중무휴 최고의 전문진료 동물병원 - 정형외과/심장영상 등 중증 진료부터 - 강아지/고양이 건강검진과 일반 진료까지! - 입원 24시간 모니터링 - 수영역 2번 출구 100m 위치  [정형외과 전문] - 슬개골탈구, 고관절탈구, 십자인대파열, 골절 등 - 수의외과학 박사 집도 - 슬개골 재탈구 책임보증제 실시  [심장/영상 전문] - 이첨판폐쇄부전증 등 심장 질환 전문 - 심장 전문 영상의학과 석사 원장님 판독 및 진단 - 심장 정밀검진 및 모니터링 관리  [고양이 전문병원] - 고양이 단독 진료층 (3층) 분리 - 고양이 전문 원장님 진료 - 고양이 친화병원 골드  [진료시간] 회진시간 : 09:00 ~ 10:00 주간진료 : 10:00 ~ 19:00 야간진료 : 19:00 ~ 20:00", DogSize.MEDIUM, PlaceType.HOSPITAL, null);
        placeRepo.save(place9);

        Place place10 = new Place("문현 동물병원", 35.1365, 129.0717, allImageLists.get(9), LocalTime.of(9, 30), LocalTime.of(19, 30), "부산 남구 수영로 38", "051-634-4017", "안녕하세요, 문현 동물병원입니다", DogSize.SMALL, PlaceType.HOSPITAL, null);
        placeRepo.save(place10);

        Place place11 = new Place("다솜고양이 메디컬센터", 35.1373, 129.0693, allImageLists.get(10), LocalTime.of(10, 0), LocalTime.of(22, 0), "부산광역시 남구 수영로13번길 3", "051-632-7580", "'우리아가'라는 마음으로 진료하는 다솜고양이메디컬센터입니다. 다솜은 고양이 / 심장 / 치과 / 외과 / 영상 전문 동물병원으로 다양한 임상사례를 경험한 대학병원, 대형병원출신 석박사급 의료진의 분과 진료를 통해 전문성과 안전성을 갖추고있습니다.  국내 최초 ISFM 고양이친화인증을 받은 '고양이전문병원'으로 고양이만을 위한 전문적인 시설과 진료 방식으로 진료합니다.  1층 [다솜동물메디컬센터] 2층 [다솜고양이메디컬센터] 3층 [동물영상센터(CT, MRI, 동물심장센터) / 고양이입원병동 / 대기실] 4층 [외과센터 / 치과센터]", DogSize.SMALL, PlaceType.HOSPITAL, null);
        placeRepo.save(place11);


        /*
         * 애견 카페
         */
        Place place12 = new Place("도그민", 35.1548, 129.1201, allImageLists.get(11), LocalTime.of(11, 0), LocalTime.of(21, 0), "부산광역시 수영구 민락동 179-1번지 6층", "0507-1306-6720", "강아지 없이도 입장가능한 중소형견(~15kg) 애견카페입니다", DogSize.BIG, PlaceType.CAFE, null);
        placeRepo.save(place12);

        Place place13 = new Place("FC일석이조", 35.1358, 129.1002, allImageLists.get(12), LocalTime.of(13, 0), LocalTime.of(22, 30), "부산광역시 남구", "051-612-2571", "안녕하세요, FC일석이조입니다", DogSize.BIG, PlaceType.CAFE, null);
        placeRepo.save(place13);

        Place place14 = new Place("또또애견", 35.1425, 129.0562, allImageLists.get(13), LocalTime.of(12, 0), LocalTime.of(21, 0), "부산광역시 부산진구 신암로 9", "051-633-7789", "안녕하세요, 또또애견입니다", DogSize.SMALL, PlaceType.CAFE, null);
        placeRepo.save(place14);
//
//        Place place15 = new Place("홀킷", 35.1555, 129.066, allImageLists.get(14), LocalTime.of(0, 1), LocalTime.of(0, 0), "부산광역시 부산진구 서전로58번길 48 3층", "010-6292-0678", "안녕하세요, 홀킷입니다", DogSize.SMALL, PlaceType.CAFE, null);
//        placeRepo.save(place15);
//
//        Place place16 = new Place("개라모르겠다", 35.155, 129.0631, allImageLists.get(15), LocalTime.of(12, 0), LocalTime.of(22, 0), "부산광역시 부산진구 전포대로 209번길 39-9호 2층", "051-804-1435", "안녕하세요, 개라모르겠다입니다", DogSize.SMALL, PlaceType.CAFE, null);
//        placeRepo.save(place16);
//
//        Place place17 = new Place("스포독", 35.1766, 129.1263, allImageLists.get(16), LocalTime.of(12, 0), LocalTime.of(19, 0), "부산 해운대구 센텀동로 102 센텀필상가1관 옥상", "0507-1359-1972", "※ 스포독과 함께 즐길 수 있는 7가지 포인트 ※  1. 스포독 : 도심 속 드넓은 루프탑 반려견 운동장  - 시즌별 운영시간 봄&가을 : 13:00~20:00 여름 : 15:00~21:30 (수영장 OPEN!) 겨울 : 12:00~19:00  소형견 운동장 / 중대형견 운동장 분리 - 소형견 입장료 (11kg 미만) : 6,000원 - 중형견 입장료(11kg~19kg) : 7,000원 - 대형견 입장료(20kg 이상) : 8,000원  ※ 할인이벤트 ※ - 2견 이상 데리고 올 시 다둥이견 할인! - 중,고,대학생 학생증 제시하면 1견 입장료 할인!  - 사람 입장료 : 6,000원 * 여름한정 레인데이 보호자 입장료 무료 * 음료 및 간단한 디저트류 반입 가능  - 주차 : 1시간 30분 무료 * 배달음식 주문 시 추가 주차시간 1시간 제공 ( 이후 주차 요금은 시간당 1,000원 추가입니다. 데스크에서 결제하시면 됩니다 )  2. 요트독 : 반려견과 함께 즐기는 요트투어  - 프라이빗 요트 : 요트 전체 대여 및 투어  - 퍼블릭 요트 투어 : 다른 손님들과 함께 동승하는 요트투어 ※ 스포독 1견 무료입장권 2장 제공  - 365일 / 13시~20시 (1시간 단위로 운영)  3. 면세독 : 온라인보다 저렴한 반려생활의 필수템 가득!  4. 돌봄 : 보호자의 마음으로 돌봐주는 전문케어시스템 - 입장료 내시면 2시간~4시간 케어 가능합니다!  5. 대관 : 운영시간 외 나와 강아지만을 위한 공간 모임, 동호회 등 단체 대관도 OK!  - 소형견 운동장 (11kg 미만) 1시간 : 5만원 / 2시간 : 8만원 - 중.대형견 운동장 (11kg 이상) 1시간 : 6만원 / 2시간 : 9만원  6. 제휴음식 : 허기진 배를 달래 줄 맛있는 제휴음식들 - 피자알볼로, 쌀통닭, 강다짐  7. 다양한 반려견 관련 행사 : 반려견에 의한, 반려견을 위한 다채로운 행사 - 운동회, 독스타 컨테스트, 할로윈파티, 플리마켓, 마켓독 (오프라인 중고마켓) 등  주소 : 부산시 해운대구 센텀필상가 1관 옥상 문의 : 051-783-1976 홈페이지 : http://spodog.modoo.at 인스타그램 : @spodog_love", DogSize.MEDIUM, PlaceType.CAFE, null);
//        placeRepo.save(place17);
//
//        Place place18 = new Place("카페개네 서면점", 35.1589, 129.0641, allImageLists.get(17), LocalTime.of(12, 0), LocalTime.of(20, 30), "부산광역시 부산진구 서전로37번길 26 3층", "010-4464-7919", "안녕하세요, 카페개네 서면점입니다", DogSize.MEDIUM, PlaceType.CAFE, null);
//        placeRepo.save(place18);
//
//        Place place19 = new Place("월월월", 35.1805, 129.0882, allImageLists.get(18), LocalTime.of(12, 0), LocalTime.of(21, 0), "부산광역시 연제구 쌍미천로84번길 6 월월월앳더모먼츠", "010-7584-7599", "<1층> 카페&스토어 펫-프랜들리 복합문화공간 * 실내외 자유로운 반려견(10kg미만) 출입 ※ 수컷강아지 기저귀 필수 착용(매장에서 구입가능) * Signature I 베이글, 분식류, 커피, 시즌 베버리지  <2층> 스튜디오, 대관 룸 * 생파, 독서모임, 교육/세미나,상업사진촬영 브라이덜 샤워 대관 등  <루프탑> 플레이 그라운드(Play Ground)  * 전용주차장 I 4~5대 주차가능", DogSize.MEDIUM, PlaceType.CAFE, null);
//        placeRepo.save(place19);
//
//        Place place20 = new Place("아껴줄개", 35.192, 129.1, allImageLists.get(19), LocalTime.of(12, 0), LocalTime.of(21, 0), "부산광역시 동래구 온천천로 463", "051-532-2232", "온천천 산책로가 잘 보이는 2층 애견카페입니다. 방문훈련 / 출장강연/ 애견유치원 / 애견호텔 / 어질리티/펫트니스 운영중 ]", DogSize.MEDIUM, PlaceType.CAFE, null);
//        placeRepo.save(place20);
//
//        Place place21 = new Place("덕수네 애견카페", 35.0993, 129.0317, allImageLists.get(20), LocalTime.of(12, 0), LocalTime.of(21, 0), "부산광역시 중구 광복동2가", "051-245-2956", "덕수네애견카페 광복동,남포동,애견카페, DOG CAFE 반려견없이 입장가능![유아동입장가능] 반려견호텔,놀이터,애견유치원", DogSize.MEDIUM, PlaceType.CAFE, null);
//        placeRepo.save(place21);
//
//        /*
//         * 애견 미용
//         */
//        Place place22 = new Place("J@야니스타일 애견미용", 35.148, 129.0573, allImageLists.get(21), LocalTime.of(13, 0), LocalTime.of(21, 0), "부산광역시 동구 J@ 야니스타일", "051-442-5750", "애견의모든것 안심하고 맡길수있는 오픈미용실 입니다 미용. 호텔. 용품밎사료 없는것 빼고 다~~있읍니다 ~~^^많이많이. 찾아주세요", DogSize.SMALL, PlaceType.BEAUTY, null);
//        placeRepo.save(place22);
//
//        Place place23 = new Place("미소애견미용실", 35.1571, 129.054, allImageLists.get(22), LocalTime.of(10, 0), LocalTime.of(18, 0), "부산광역시 부산진구 가야대로 754 1층", "051-894-2009", "안녕하세요, 미소애견미용실입니다", DogSize.SMALL, PlaceType.BEAUTY, null);
//        placeRepo.save(place23);
//
//        Place place24 = new Place("메리몰펫샵 문현점", 35.1076, 129.0341, allImageLists.get(23), LocalTime.of(10, 0), LocalTime.of(19, 0), "부산 남구 수영로39번나길 53 1층 메리몰펫샵", "010-5761-2944", "안녕하세요. 반려견 가위컷 전문, 부산애견미용 메리몰펫샵 문현점입니다. 애견미용,스파, 가위컷,놀이방, 호텔, 대형견 목욕,미용 등 애견미용전문 펫샵이므로 부담없이 상담 문의해 주세요.", DogSize.SMALL, PlaceType.BEAUTY, null);
//        placeRepo.save(place24);
//
//        Place place25 = new Place("행복애견미용실", 35.1702, 129.0677, allImageLists.get(24), LocalTime.of(10, 30), LocalTime.of(20, 0), "부산 부산진구 거제대로 18", "051-867-8483", "안녕하세요, 행복애견미용실입니다", DogSize.SMALL, PlaceType.BEAUTY, null);
//        placeRepo.save(place25);
//
//        Place place26 = new Place("펫코스", 35.1646, 129.1676, allImageLists.get(25), LocalTime.of(9, 0), LocalTime.of(19, 0), "부산광역시 해운대구 해운대해변로357번길 17 3층", "051-710-2002", "안녕하십니까. 해운대 중동 큰마음반려동물센터에 위치한 펫코스입니다 :)  강아지 미용을 전문으로, 5000여가지 이상의 반려동물 용품까지 매장에서 만나보실 수 있습니다!  - 경력 10년의 펫 스타일리스트가 제공하는 숙련된 애견 미용 - 달목욕부터 대형견 전용 스파까지 가능한 강아지 목욕 - 오픈된 미용공간으로 안심할 수 있는 강아지 미용 케어  이 모든 것들을 펫코스에서 만나보실 수 있습니다!  해운대 강아지 미용 / 해운대 애견 미용 고민하셨다면 10년 경력의 믿을 수 있는 안전한 애견 미용, 펫코스에서 만나보세요!", DogSize.BIG, PlaceType.BEAUTY, null);
//        placeRepo.save(place26);
//
//        Place place27 = new Place("핑코애견", 35.1733, 129.1701, allImageLists.get(26), LocalTime.of(9, 0), LocalTime.of(20, 0), "부산광역시 해운대구 좌제3동", "051-945-2800", "애견미용실입니다  * 애견관리학과 출신 미용사. 10년경력의 풍부한 실전경력 ---- 애견미용사, 애견훈련사, 애견핸들러, 동물간호사, 교원자격증 보유  * 마이크로 버블 샤워기 장착. 모든 미용시 마이크로 버블샤워로 깔끔하고 시원한 피부세정및 피부병예방 * 편백나무욕조 제작, 100% 편백나무 오일 스파  ---- 편백나무의 효능 - 알레르기 및 피부질환개선, 강력한 향균효과, 스트레스완화, 진정효과, 쾌적효과 강력 탈취 및 정화기능, 면역기능증대효과", DogSize.BIG, PlaceType.BEAUTY, null);
//        placeRepo.save(place27);
//
//        Place place28 = new Place("희야네애견미용실", 35.188, 129.9888, allImageLists.get(27), LocalTime.of(10, 0), LocalTime.of(20, 0), "부산광역시 사상구 모라동 979-1", "051-303-0279", "모라동 가위컷 전문 희야네 애견미용실입니다.  미용은 100% 예약제로 운영되며, 스트레스 받지 않는 공간에서 반려견을 위한 최고의 서비스를 제공합니다.", DogSize.BIG, PlaceType.BEAUTY, null);
//        placeRepo.save(place28);
//
//        Place place29 = new Place("안앤정 애견미용", 35.094, 129.964, allImageLists.get(28), LocalTime.of(10, 0), LocalTime.of(19, 0), "부산광역시 사하구 하신중앙로 170 현대아파트 상가동 1층", "010-5709-8318", "안녕하세요. 안앤정 애견미용입니다. 오픈형 미용실 / 1:1 맞춤 케어 / 예약제 / 스파 / 전체미용시 신평동 전지역 무료 픽업 서비스", DogSize.MEDIUM, PlaceType.BEAUTY, null);
//        placeRepo.save(place29);
//
//        Place place30 = new Place("멍멍스타 펫살롱", 35.1019, 128.9972, allImageLists.get(29), LocalTime.of(9, 0), LocalTime.of(19, 0), "부산 사하구 낙동대로 174 멍멍스타", "051-293-3398", "** 반려견을 사랑하는 마음에 미용업을 시작해서15년째 한자리에서 멍멍스타를 운영하고 있습니다 ^^  ** 애견 미용경력 16년!! 클리핑, 가위컷 전문으로 세심하고 트렌디한 미용이 가능합니다^^  ** 2인 근무로 미용받는 애기들을 안전하게 보정하여 스트레스 받지 않는 환경에서 미용합니다^^  + 당일 스케줄에 따라 조기 마감 될 수 있는 점 양해 부탁드립니다. + 건강상태가 좋지 않은 애기, 입질이 심한 애기들은 돌발 상황에 즉각 대처가능한 동물병원 미용실을 방문하시는게 애기들에게 도움이 됩니다.  **** 미용비 안내***** *전체미용비 : ** 소형견 기준 (얼굴컷 포함시) 35,000~ ** 초소형견 기준 썸머컷 (스포팅: 등 털은 짧게, 다리털은 통통하게+ 얼굴컷) 6만~ / 비숑 썸머컷 6만5천원~ **전체가위컷 7만~ ** 위생미용 + 목욕 (소형견,단모기준) 30,000~ ** 포메, 스피츠와 같이 이중모,장모는 부분+목욕 35,000원부터 시작이고, 엉킴이 있어서 풀어야 하는 경우 추가비 발생 **털 길이, 체중 ,엉킴, 미용태도에 따라 추가비용 발생  -포메,스피츠, 페키 견종 등 이중모를 가진 친구들은 알로페시아 증상의 이유로 미용후 털이 균일하게 자라지 않을 수 있습니다. 가위컷을 해도 알로페시아 증상이 나타날수 있으니 참고 해주세요.  < 태도 추가비> 미용시, 노령견으로 부축이 꼭 필요하거나 미용에 대한 심한 거부, 입질, 산만함의 정도에 따라 5000~10,000원의 추가비 발생합니다 ( 미용시,다른 아이들에 비하여 위험도가 커서 상당한 집중이 요구되므로 추가비가 있음을 알려드립니다. 양해 부탁드립니다.)  -저희 가게는 분양,호텔 안합니다.", DogSize.MEDIUM, PlaceType.BEAUTY, null);
//        placeRepo.save(place30);
//
//        Place place31 = new Place("초량애견샵", 35.1202, 129.0394, allImageLists.get(30), LocalTime.of(10, 0), LocalTime.of(20, 30), "부산 동구 초량중로 100-2", "051-463-1357", "안녕하세요, 초량애견샵입니다", DogSize.MEDIUM, PlaceType.BEAUTY, null);
//        placeRepo.save(place31);
//
//
//        /*
//         * 애견 용품
//         */
//        Place place32 = new Place("핫독애견용품", 35.1201, 129.1119, allImageLists.get(31), LocalTime.of(10, 0), LocalTime.of(21, 0), "부산광역시 남구 용호동 370-32", "051-611-0177", "안녕하세요, 핫독애견용품입니다", DogSize.SMALL, PlaceType.GOODS, null);
//        placeRepo.save(place32);
//
//        Place place33 = new Place("행복애견", 35.141, 129.1072, allImageLists.get(32), LocalTime.of(10, 0), LocalTime.of(21, 0), "부산광역시 수영구 남천제1동 24-6", "051-626-0994", "반러견 미용 전문샵입니다 미용은 100% 예약제입니다", DogSize.SMALL, PlaceType.GOODS, null);
//        placeRepo.save(place33);
//
//        Place place34 = new Place("펫마트 해운대점", 35.1739, 129.1741, allImageLists.get(33), LocalTime.of(9, 0), LocalTime.of(22, 0), "부산광역시 해운대구 좌동로91번길 38-7", "051-702-1556", "해운대 펫마트 오픈!  강아지-고양이-소동물 국내 반려동물용품 NO.1 프랜차이즈 해운대 최대규모/최다품목/최저가격  @주차가능! 130평 대규모 매장! @설날, 추석 당일만 휴무 @반려동물동반가능! @무료 와이파이! @파격적 적립혜택! 오픈기념 사은품 증정! @다양한 할인및 구매사은품! 매월 할인 이벤트!  반려동물용품은 역시 펫마트!  좌동 재래시장 인근! 병천순대국밥 골목 50m 안! ING동물병원 근처! 구.대궐쌈밥 자리!  좌동 908-3 넓은 주차장에 편하게 주차하시고 쇼핑하세요:)  <프리미엄 펫용품점 '펫칼코' 샵인샵 입점!!> *러프웨어 *스타캣휠 *피콜로카네 *아르르 *피단", DogSize.SMALL, PlaceType.GOODS, null);
//        placeRepo.save(place34);
//
//        Place place35 = new Place("펫아울렛 센텀강변점", 35.1745, 129.1188, allImageLists.get(34), LocalTime.of(10, 0), LocalTime.of(22, 0), "부산광역시 수영구 좌수영로 117", "051-755-5302", "저희 펫아울렛 센텀강변점은 다양한 애견용품을 판매하는 대형 펫아울렛 매장입니다. 애견 애묘 사료, 간식, 용품 및 애견 미용도 가능한 멀티샵입니다. 많은 방문 및 문의 환영합니다 :)", DogSize.SMALL, PlaceType.GOODS, null);
//        placeRepo.save(place35);
//
//        Place place36 = new Place("펫스토어", 35.1638, 129.0546, allImageLists.get(35), LocalTime.of(10, 0), LocalTime.of(22, 0), "부산광역시 부산진구 새싹로 76", "051-808-9319", "1층 2층 애견용품 (강아지 고양이)- 사료, 간식, 장난감, 패드  3층 미용,호텔 영업시간 :am10:00~ pm10:00 친절한직원들이 기다리고있습니다.", DogSize.BIG, PlaceType.GOODS, null);
//        placeRepo.save(place36);
//
//        Place place37 = new Place("펫마트 다대점", 35.0601, 128.9809, allImageLists.get(36), LocalTime.of(9, 0), LocalTime.of(22, 0), "부산광역시 사하구 다송로72번길 50", "051-266-1550", "한국프랜차이즈대상 2관왕  반려동물을 위한 모든 것! 강아지-고양이-소동물 국내 반려동물용품 NO.1 프랜차이즈 최대규모/최다품목/최저가격  @주차가능 / 연중무휴 / 반려동물동반가능 / 무료와이파이 @파격적 적립혜택 / 구매사은품 상시 증정! @다양한 묶음할인 제품 / 매월 할인 이벤트 진행 중!  반려동물용품은 역시 펫마트!", DogSize.BIG, PlaceType.GOODS, null);
//        placeRepo.save(place37);
//
//        Place place38 = new Place("핫독(HOT DOG)애견용품", 35.0979, 129.0328, allImageLists.get(37), LocalTime.of(11, 0), LocalTime.of(21, 0), "남포동2가 28-3번지 중구 부산광역시", "051-247-0999", "안녕하세요, 핫독(HOT DOG)애견용품입니다", DogSize.BIG, PlaceType.GOODS, null);
//        placeRepo.save(place38);
//
//        Place place39 = new Place("애견용품", 35.1568, 128.9776, allImageLists.get(38), LocalTime.of(10, 0), LocalTime.of(21, 0), "감전동 128-1번지 사상구 부산광역시", "051-323-6664", "안녕하세요, 애견용품입니다", DogSize.MEDIUM, PlaceType.GOODS, null);
//        placeRepo.save(place39);
//
//        Place place40 = new Place("펫존애완용품할인매장", 35.0856, 128.9857, allImageLists.get(39), LocalTime.of(10, 0), LocalTime.of(20, 30), "부산 사하구 을숙도대로 741 영생비치맨션", "051-996-9631", "안녕하세요, 펫존애완용품할인매장입니다", DogSize.MEDIUM, PlaceType.GOODS, null);
//        placeRepo.save(place40);
    }

    @PostMapping("/")
    public String test() {
        return "ok";
    }
}
