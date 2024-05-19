import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { getTypePlaces, searchPlaces } from "../api";
import "./MainPage.css";

import mainImg from "../img/mainBg.png";
import searchImg from "../img/searchBtn.png";
import mapLocation from "../img/mapLocation.png";

import mapBtn from "../img/dogventure_logo_white.png";
import PlaceList from "../components/PlaceList";
import ChatbotHG from "../components/ChatbotHG";

function AppFilterButton({ selected, children, onClick }) {
  return (
    <button
      className={`filter-btn ${selected ? "selected-btn" : ""}`}
      onClick={onClick}
    >
      {children}
    </button>
  );
}

function MainPage() {
  const [items, setItems] = useState([]);
  const [search, setSearch] = useState("");
  const [type, setType] = useState("all");

  const handleAllClick = () => {
    setType("all");
    setSearch("");
    document.querySelector(".search-input").value = "";
  };

  const handleCafeClick = () => {
    setType("CAFE");
    setSearch("");
    document.querySelector(".search-input").value = "";
  };

  const handleHospitalClick = () => {
    setType("HOSPITAL");
    setSearch("");
    document.querySelector(".search-input").value = "";
  };

  const handleBeautyClick = () => {
    setType("BEAUTY");
    setSearch("");
    document.querySelector(".search-input").value = "";
  };

  const handleGoodslClick = () => {
    setType("GOODS");
    setSearch("");
    document.querySelector(".search-input").value = "";
  };

  const handleLoad = async () => {
    let result;

    try {
      if (search) {
        result = await searchPlaces({ word: search });
      } else {
        result = await getTypePlaces({ type });
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
    setType("all");
  };

  useEffect(() => {
    handleLoad();
  }, [search, type]);

  return (
    <>
      <div className="bg-box">
        <img className="bg-img" src={mainImg} />
        <Link to="/map">
          <button className="map-btn">
            <img className="map-btn-img" src={mapLocation} />
            <div className="map-btn-text">지도로 이동하기</div>
          </button>
        </Link>
      </div>
      <div className="mainPage">
        <div className="place-nav">
          <div className="mainPage-filter-btn-box">
            <AppFilterButton selected={type === "all"} onClick={handleAllClick}>
              전체
            </AppFilterButton>
            <AppFilterButton
              selected={type === "GOODS"}
              onClick={handleGoodslClick}
            >
              애견용품
            </AppFilterButton>
            <AppFilterButton
              selected={type === "BEAUTY"}
              onClick={handleBeautyClick}
            >
              미용
            </AppFilterButton>
            <AppFilterButton
              selected={type === "CAFE"}
              onClick={handleCafeClick}
            >
              카페
            </AppFilterButton>
            <AppFilterButton
              selected={type === "HOSPITAL"}
              onClick={handleHospitalClick}
            >
              병원
            </AppFilterButton>
          </div>

          <div className="search-box">
            <form className="search-form" onSubmit={handleSearchSubmit}>
              <input
                className="search-input"
                name="search"
                placeholder="상호명을 입력해보세요!"
                autoComplete="off"
              />
              <button className="search-btn" type="submit">
                <img src={searchImg} alt="검색 이미지" />
              </button>
            </form>
          </div>
        </div>
        <hr className="place-nav-line" />
        <PlaceList items={items} />
      </div>
      {/* <ChatbotHG /> */}
    </>
  );
}

export default MainPage;
