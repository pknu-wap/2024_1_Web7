// import Map from "../components/Map";
import { Container as MapDiv } from "react-naver-maps";
import dog from "../img/dog.jpg";
import "./MainPage.css";
import PlaceList from "../components/PlaceList";
import { useState } from "react";
import placeData from "../place.json";
import Map from "../components/Map";

function MainPage() {
  const [itmes, setItems] = useState([]);
  const [search, setSearch] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [loadingError, setLoadingError] = useState(null);

  const handleSearchSubmit = (e) => {
    e.prevetnDefault();
    setSearch(e.target["search"].value);
  };

  return (
    <>
      {/* <MapDiv
        style={{
          width: "100%",
          height: "600px",
        }}
      >
        <Map />
      </MapDiv> */}
      <div className="bg-box">
        <img className="bg-img" src={dog} />
      </div>
      <PlaceList items={placeData} />
    </>
  );
}

export default MainPage;
