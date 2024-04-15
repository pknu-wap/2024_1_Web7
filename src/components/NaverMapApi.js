import { useEffect, useRef, useState } from "react";
import { Marker, NaverMap } from "react-naver-maps";

const DEFAULT_LAT = "35.13398675558719";
const DEFAULT_LNG = "129.10555363863023";

function NaverMapApi() {
  const [userLocation, setUserLocation] = useState(null);
  const [locations, setLocations] = useState([]);
  const mapElement = useRef(null);
  const { naver } = window;

  useEffect(() => {
    if (!mapElement.current || !naver) return;

    // 사용자의 현재 위치를 가져오는 함수 (https 환경에서만 작동)
    const getUserLocation = () => {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            const { latitude, longitude } = position.coords;
            setUserLocation({ lat: latitude, lng: longitude });
          },
          (error) => {
            console.error("위치 정보를 가져오는 데 실패했습니다.", error);
            // 위치 정보를 가져오지 못한 경우, 기본 위치로 설정
            setUserLocation({ lat: DEFAULT_LAT, lng: DEFAULT_LNG });
          }
        );
      } else {
        console.error("Geolocation이 지원되지 않습니다.");
        //Geolocation을 지원하지 않는 경우, 기본 위치로 설정
        setUserLocation({ lat: DEFAULT_LAT, lng: DEFAULT_LNG });
      }
    };

    getUserLocation();

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
        setLocations(data);
      } catch (error) {
        console.error("장소를 불러오는 데 실패했습니다.", error);
      }
    };

    fetchLocation();
  }, [naver]);

  return (
    <>
      <h1>DogVenture 지도</h1>
      <NaverMap
        ref={mapElement}
        id="map"
        style={{ minHeight: "400px" }}
        defaultCenter={{
          lat: userLocation?.lat || DEFAULT_LAT,
          lng: userLocation?.lng || DEFAULT_LNG,
        }}
        defaultZoom={15}
      >
        <Marker
          key="userMarker"
          position={{
            lat: userLocation?.lat || DEFAULT_LAT,
            lng: userLocation?.lng || DEFAULT_LNG,
          }}
          title="내 위치"
        />

        {/* 장소 마커들 */}
        {locations.map((location) => (
          <Marker
            key={location.id}
            // 여기 만약 안뜨면 defaultposition으로 바꿔보기
            position={{ lat: location.x, lng: location.y }}
            title={location.name}
          />
        ))}
      </NaverMap>
    </>
  );
}

export default NaverMapApi;
