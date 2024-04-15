import { useEffect, useState } from "react";
import { Marker, NaverMap } from "react-naver-maps";

const DEFAULT_LAT = "35.1339";
const DEFAULT_LNG = "129.1055";

function MapWithCurrentLocation() {
  const [userLocation, setUserLocation] = useState(null);

  useEffect(() => {
    // 사용자의 현재 위치를 가져오는 함수
    const getUserLocation = () => {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            const { latitude, longitude } = position.coords;
            setUserLocation({ latitude, longitude });
          },
          (error) => {
            console.log("위치 정보를 가져오는 데 실패했습니다.", error);
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
  }, []);

  return (
    <div style={{ width: "100%", height: "400px" }}>
      <NaverMap
        defaultCenter={{
          lat: userLocation?.lat || DEFAULT_LAT,
          lng: userLocation?.lng || DEFAULT_LNG,
        }}
        defaultZoom={15}
        style={{ width: "100%", height: "100%" }}
      >
        {/* 사용자 위치에 마커 표시 */}
        {userLocation && (
          <Marker
            key="userMarker"
            position={{ lat: userLocation.lat, lng: userLocation.lng }}
            title="내 위치"
          />
        )}
      </NaverMap>
    </div>
  );
}

export default MapWithCurrentLocation;
