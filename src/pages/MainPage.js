import { useState, useEffect } from "react";
import { getPlaces, searchPlaces } from "../api";
import dog from "../img/dog.jpg";
import "./MainPage.css";
import PlaceList from "../components/PlaceList";
import searchImg from "../img/search.png";
import { Container as MapDiv } from "react-naver-maps";
import Map from "../components/Map";
import placeData from "../place.json";

function AppFilterButton({ selected, children, onClick }) {
  return (
    <button
      disabled={selected}
      className={`AppFilterButton ${selected ? "selected" : ""}`}
      onClick={onClick}
    >
      {children}
    </button>
  );
}

function MainPage() {
  const [items, setItems] = useState([]);
  const [search, setSearch] = useState("");
  const [type, setType] = useState("");

  const handleAllClick = () => {
    setType("all");
    setSearch("");
  };

  const handleCafeClick = () => {
    setType("CAFE");
    setSearch("");
  };

  const handleHospitalClick = () => {
    setType("HOSPITAL");
    setSearch("");
  };

  const handleLoad = async () => {
    let result;

    try {
      if (search) {
        result = await searchPlaces({ word: search });
      } else {
        result = await getPlaces({ type });
      }

      if (result) {
        setItems(result);
      } else {
        console.log("로드할 항목을 찾을 수 없습니다.");
      }
    } catch (error) {
      console.log("로드 중 오류가 발생했습니다.");
    }
  };

  const handleSearchSubmit = async (e) => {
    e.preventDefault();
    const searchTerm = e.target["search"].value;
    setSearch(searchTerm);
  };

  useEffect(() => {
    handleLoad();
  }, [search, type]);

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
      <div className="search-box">
        <form className="search-form" onSubmit={handleSearchSubmit}>
          <input
            className="search-input"
            name="search"
            placeholder="상호명을 입력해보세요!"
            autocomplete="off"
          />
          <button className="search-btn" type="submit">
            <img src={searchImg} alt="검색 이미지" />
          </button>
        </form>
      </div>

      <div>
        <AppFilterButton selected={type === "all"} onClick={handleAllClick}>
          전체
        </AppFilterButton>
        <AppFilterButton selected={type === ""} onClick={handleAllClick}>
          애견용품
        </AppFilterButton>
        <AppFilterButton selected={type === ""} onClick={handleAllClick}>
          미용
        </AppFilterButton>
        <AppFilterButton selected={type === "CAFE"} onClick={handleCafeClick}>
          카페
        </AppFilterButton>
        <AppFilterButton
          selected={type === "HOSPITAL"}
          onClick={handleHospitalClick}
        >
          병원
        </AppFilterButton>
      </div>
      <PlaceList items={items} />
    </>
  );
}

export default MainPage;
