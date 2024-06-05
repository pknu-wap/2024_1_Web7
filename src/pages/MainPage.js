import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { searchPlaces, getPlaces } from "../api";
import "./MainPage.css";

import mainImg from "../img/dogBack.gif";
import mapLocation from "../img/mapLocation_white.png";
import bgLogo from "../img/BgLogo.png";
import loadingImg from "../img/LoadingImg.gif";

import PlaceList from "../components/PlaceList";
import ChatbotHG from "../components/ChatbotHG";
import Search from "../components/Search";

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
  const [isLoading, setIsLoading] = useState(false);

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
        setIsLoading(true);
        result = await searchPlaces({ word: search });
      } else {
        setIsLoading(true);
        result = await getPlaces({ type });
      }

      if (result) {
        setItems(result);
      } else {
        console.log("로드할 항목을 찾을 수 없습니다.");
      }
    } catch (error) {
      console.log("로드 중 오류가 발생했습니다.");
    } finally {
      setIsLoading(false);
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
        <img className="bg-title-img" src={bgLogo} />
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
          <Search handleSearchSubmit={handleSearchSubmit} />
        </div>
        <hr className="place-nav-line" />
        {isLoading ? (
          <img src={loadingImg} alt="로딩 이미지" />
        ) : (
          <PlaceList items={items} />
        )}
      </div>
      <ChatbotHG />
    </>
  );
}

export default MainPage;
