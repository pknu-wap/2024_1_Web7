import { useEffect, useRef, useState } from "react";
import { Container as MapDiv, Marker, NaverMap } from "react-naver-maps";

const DEFAULT_LAT = "35.1339";
const DEFAULT_LNG = "129.1055";

function NaverMapApi() {
  const [locations, setLocations] = useState([]);
  const mapElement = useRef(null);
  const { naver } = window;

  useEffect(() => {
    if (!mapElement.current || !naver) return;

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
        defaultCenter={{ lat: DEFAULT_LAT, lng: DEFAULT_LNG }}
        defaultZoom={15}
      >
        {locations.map((location) => (
          <Marker
            key={location.id}
            position={{ lat: location.x, lng: location.y }}
            title={location.name}
          />
        ))}
      </NaverMap>
    </>
  );
}

export default NaverMapApi;
