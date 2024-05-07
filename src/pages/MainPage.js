import { useState, useEffect } from "react";
import { getPlaces } from "../api";
import dog from "../img/dog.jpg";
import "./MainPage.css";
import PlaceList from "../components/PlaceList";
import { Container as MapDiv } from "react-naver-maps";
import Map from "../components/Map";
import placeData from "../place.json";

function MainPage() {
  const [items, setItems] = useState([]);
  const [search, setSearch] = useState("");

  const handleLoad = async () => {
    let result;
    try {
      result = await getPlaces();
      setItems(result);
    } catch (error) {
      console.log("에러");
    }
  };

  const handleSearchSubmit = (e) => {
    e.preventDefault();
    // setSearch(e.target["search"].value);
    const searchTerm = e.target.elements.search.value;
    setSearch(searchTerm);
  };

  useEffect(() => {
    handleLoad();
  }, []);

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
      <form onSubmit={handleSearchSubmit}>
        <input name="search" />
        <button type="submit">검색</button>
      </form>
      <PlaceList items={items} />
    </>
  );
}

export default MainPage;
