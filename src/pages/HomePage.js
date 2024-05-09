import ChatbotHG from "../components/ChatbotHG";
import Map from "../components/Map";
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
        <Map />
      </MapDiv>
      
      <ChatbotHG />
    </>
  );
}

export default HomePage;
