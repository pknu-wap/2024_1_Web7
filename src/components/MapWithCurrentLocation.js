import { useEffect, useState } from "react";
import { InfoWindow, Marker, NaverMap } from "react-naver-maps";

// 프론트 코드만 작성
// 서버 연결 안되어 있을 시 마커 표시 안되는 버그 발생 때문

const DEFAULT_LAT = "35.1339";
const DEFAULT_LNG = "129.1055";

function MapWithCurrentLocation() {
  const [userLocation, setUserLocation] = useState(null);
  const [infoWindowOpen, setInfoWindowOpen] = useState(false);

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

  const handleMarkerClick = () => {
    setInfoWindowOpen(!infoWindowOpen); // 정보창 열기/닫기 토글
  };

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
        <Marker
          key="userMarker"
          position={{
            lat: userLocation?.lat || DEFAULT_LAT,
            lng: userLocation?.lng || DEFAULT_LNG,
          }}
          title="내 위치"
          onClick={handleMarkerClick}
        />

        {infoWindowOpen && (
          <InfoWindow
            anchor={{
              lat: DEFAULT_LAT,
              lng: DEFAULT_LNG,
            }}
            onCloseClick={() => setInfoWindowOpen(false)}
          >
            <div>
              <h3>부경대학교</h3>
              <p>대학교다.</p>
            </div>
          </InfoWindow>
        )}
      </NaverMap>
    </div>
  );
}

export default MapWithCurrentLocation;
