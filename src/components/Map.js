import { useEffect, useRef } from "react";
import useGeolocation from "../hooks/useGeolocation";

function Map() {
  const mapRef = useRef(null);
  const { naver } = window;
  const { currentMyLocation } = useGeolocation();
  const { LatLng, Map, Marker, InfoWindow } = naver.maps; // 필요한 객체를 비구조화 할당

  useEffect(() => {
    if (currentMyLocation.lat !== 0 && currentMyLocation.lng !== 0) {
      // 네이버 지도 옵션 선택

      const mapOptions = {
        // 지도의 초기 중심 좌표
        // 사용자의 현재 위치를 가져오는 건 https 환경에서만 작동
        center: new LatLng(currentMyLocation.lat, currentMyLocation.lng),
        logoControl: false, // 네이버 로고 표시 X
        mapDataControl: false, // 지도 데이터 저작권 컨트롤 표시 X
        scaleControl: true, // 지도 축적 컨트롤의 표시 여부
        tileControl: 200, // 지도 타일을 전환할 때 페이드 인 효과의 지속 시간(밀리초)
        zoom: 15, //지도의 초기 줌 레벨
        zoomControl: true, // 줌 컨트롤 표시
        zoomContorlOptions: { position: 9 }, // 줌 컨트롤 우하단에 배치
      };
      mapRef.current = new Map("map", mapOptions);

      // 현재 내 위치에 마커 표시
      const marker = new Marker({
        // 생성될 마커의 위치
        position: LatLng(currentMyLocation.lat, currentMyLocation.lng),
        // 마커를 표시할 Map 객체
        map: mapRef.current,
      });

      const infoWindow = new InfoWindow({
        content: [
          '<div style="padding: 10px; box-shadow: rgba(0, 0, 0, 0.1) 0px 4px 16px 0px;">',
          `   <div style="font-weight: bold; margin-bottom: 5px;">부경대학교</div>`,
          `   <div style="font-size: 13px;">부경대학교다.<div>`,
          "</div>",
        ].join(""),
        maxWidth: 300,
        anchorSize: {
          width: 12,
          height: 14,
        },
        borderColor: "#cecdc7",
      });

      naver.maps.Event.addListener(marker, "click", () => {
        if (infoWindow.getMap()) {
          // 정보창이 닫힐 때 이벤트 발생
          infoWindow.close();
          // 추가해야 하는 코드
          // 정보창이 켜진 후 다른 곳 아무데나 눌러도 닫히도록
        } else if (mapRef.current !== null) {
          // 정보창이 열릴 때 이벤트 발생
          infoWindow.open(mapRef.current, marker);
        }
      });
    }
  }, [currentMyLocation]);

  return <div id="map" style={{ width: "100%", height: "400px" }} />;
}

export default Map;
