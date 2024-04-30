import { useEffect, useRef, useState } from "react";
import useGeolocation from "../hooks/useGeolocation";

function Map() {
  const mapRef = useRef(null);
  const { naver } = window;
  const { currentMyLocation } = useGeolocation();
  const { LatLng, Map, Marker, InfoWindow } = naver.maps; // 필요한 객체를 비구조화 할당
  const [places, setPlaces] = useState([]);

  useEffect(() => {
    if (currentMyLocation.lat !== 0 && currentMyLocation.lng !== 0) {
      // 네이버 지도 옵션 선택

      // 백엔드 장소 GET 코드
      const fetchLocation = async () => {
        try {
          const response = await fetch(
            "http://localhost:8080/api/map/naver/place/all"
          );
          if (!response.ok) {
            throw new Error("장소를 불러오는 데 실패했습니다.");
          }
          const data = await response.json();
          setPlaces(data);
        } catch (error) {
          console.error("장소를 불러오는 데 실패했습니다.", error);
        }
      };

      fetchLocation();

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
      const myLocationMarker = new Marker({
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

      naver.maps.Event.addListener(myLocationMarker, "click", () => {
        if (infoWindow.getMap()) {
          // 정보창이 닫힐 때 이벤트 발생
          infoWindow.close();
          // 추가해야 하는 코드
          // 정보창이 켜진 후 다른 곳 아무데나 눌러도 닫히도록
        } else if (mapRef.current !== null) {
          // 정보창이 열릴 때 이벤트 발생
          infoWindow.open(mapRef.current, myLocationMarker);
        }
      });
    }
  }, [currentMyLocation]);

  // 장소 정보 api 받아와서 마커 표시 및 정보창 띄우는 코드
  useEffect(() => {
    places.forEach((place) => {
      const placeMarker = new Marker({
        key: place.id,
        title: place.name,
        position: new LatLng(place.x, place.y),
        map: mapRef.current,
      });

      // 정보창 사이드에서 뜨도록 css 수정 필요
      const placeInfoWindow = new InfoWindow({
        content: `<div style="padding: 10px; box-shadow: rgba(0, 0, 0, 0.1) 0px 4px 16px 0px;">
            <div style="font-weight: bold; margin-bottom: 5px;">${place.name}</div>
            <div style="font-size: 13px;">${place.detailContent}</div>
            <div>${place.image}</div>
          </div>`,
        maxWidth: 300,
        anchorSize: { width: 12, height: 14 },
        borderColor: "#cecdc7",
      });

      naver.maps.Event.addListener(placeMarker, "click", () => {
        if (placeInfoWindow.getMap()) {
          placeInfoWindow.close();
        } else {
          placeInfoWindow.open(mapRef.current, placeMarker);
        }
      });
    });
  }, [places, currentMyLocation]);

  return <div id="map" style={{ width: "100%", height: "400px" }} />;
}

export default Map;
