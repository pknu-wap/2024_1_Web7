import Map from "../components/Map";
// import MapWithCurrentLocation from "../components/MapWithCurrentLocation";
// import NaverMapApi from "../components/NaverMapApi";
// import Map from "../components/Map";
import { Container as MapDiv } from "react-naver-maps";

function HomePage() {
  return (
    <>
      <MapDiv
        style={{
          width: "100%",
          height: "600px",
        }}
      >
        {/* <MapWithCurrentLocation /> */}
        {/* <NaverMapApi /> */}
        <Map />
      </MapDiv>
      {/* <Map /> */}
    </>
  );
}

export default HomePage;
