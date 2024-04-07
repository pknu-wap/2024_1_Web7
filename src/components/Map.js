import { useEffect, useRef } from "react";

const DEFAULT_LAT = "35.1339";
const DEFAULT_LNG = "129.1055";

function Map() {
  const mapElement = useRef(null);
  const { naver } = window;

  useEffect(() => {
    if (!mapElement.current || !naver) return;

    // 지도에 표시할 위치의 위도와 경도 좌표를 파라미터로 넘겨줌
    // 네이버 지도 초기화
    const location = new naver.maps.LatLng(DEFAULT_LAT, DEFAULT_LNG);
    const mapOptions = {
      center: location,
      zoom: 17,
      zoomControl: true,
    };

    // 마커 생성
    const map = new naver.maps.Map(mapElement.current, mapOptions);
    new naver.maps.Marker({
      position: location,
      map,
    });
  }, []);

  return (
    <>
      <h1>DogVenture 지도</h1>
      <div ref={mapElement} style={{ minHeight: "400px" }} />
    </>
  );
}

export default Map;
