import { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../function/AuthContext";

import useGeolocation from "../hooks/useGeolocation";
import { getPlaces, getPlaceInfo, addBookmark, createReview } from "../api";

import Modal from "./Modal";
import PopupModal from "./PopupModal";
import ReviewForm from "./ReviewForm";

import footImg from "../img/foot_green.png";
import clockImg from "../img/clock.png";
import addImg from "../img/location.png";
import phoneImg from "../img/phone.png";
import allNone from "../img/all_none.png";
import allSelec from "../img/all_selec.png";
import beautyNone from "../img/beauty_none.png";
import beautySelec from "../img/beauty_selec.png";
import cafeNone from "../img/cafe_none.png";
import cafeSelec from "../img/cafe_selec.png";
import goodsNone from "../img/goods_none.png";
import goodsSelec from "../img/goods_selec.png";
import hosNone from "../img/hos_none.png";
import hosSelec from "../img/hos_selec.png";
import rateImg from "../img/rateImg.png";
import filter from "../img/filter.png";
import search from "../img/search.png";
import footBlue from "../img/foot_blue.png";
import size_all from "../img/size_all.png";
import size_all2 from "../img/size_all2.png";
import size_small from "../img/size_small.png";
import size_small2 from "../img/size_small2.png";
import size_mid from "../img/size_mid.png";
import size_mid2 from "../img/size_mid2.png";
import size_big from "../img/size_big.png";
import size_big2 from "../img/size_big2.png";
import bookmark from "../img/bookmark1.png";
import bookmark2 from "../img/bookmark2.png";

import "./Map.css";

function Map() {
  const mapRef = useRef(null);
  const { naver } = window;
  const { currentMyLocation } = useGeolocation();
  const { LatLng, Map, Marker } = naver.maps; // 필요한 객체를 비구조화 할당
  // const { currentUser } = useAuth();

  const navigate = useNavigate();

  const [places, setPlaces] = useState([]);
  const [type, setType] = useState("all");
  const [isOpen, setIsOpen] = useState(true);
  const [dogSize, setDogSize] = useState(null);
  const [reviews, setReviews] = useState([]);
  const [images, setImages] = useState([]);

  const [selectedPlace, setSelectedPlace] = useState(null);

  const [isFilterModalOpen, setIsFilterModalOpen] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isReview, setIsReview] = useState(false);

  let token = localStorage.getItem("Authorization");

  const handleAllClick = () => setType("all");
  const handleCafeClick = () => setType("CAFE");
  const handleHospitalClick = () => setType("HOSPITAL");
  const handleBeautyClick = () => setType("BEAUTY");
  const handleGoodsClick = () => setType("GOODS");

  const handleNullClick = () => {
    setDogSize(null);
    closeFilterModal();
  };
  const handleSmallClick = () => {
    setDogSize("SMALL");
    closeFilterModal();
  };
  const handleMediumClick = () => {
    setDogSize("MEDIUM");
    closeFilterModal();
  };
  const handleBigClick = () => {
    setDogSize("BIG");
    closeFilterModal();
  };

  function ReviewList({ reviews, footImg }) {
    return (
      <ul className="review-ul">
        {reviews.map((review) => (
          <li className="review-list" key={review.id}>
            <img className="review-profile-img" src={footImg} alt="Profile" />
            <div className="review-content-box">
              <div>
                <span className="review-username">{review.user.username}</span>
                <span className="review-rate">{review.rate}</span>
              </div>
              <div className="review-content">{review.content}</div>
            </div>
          </li>
        ))}
      </ul>
    );
  }

  const handleReviewSubmit = (newReview) => {
    setReviews((prevReviews) => [...prevReviews, newReview]);
  };

  useEffect(() => {
    if (currentMyLocation.lat !== 0 && currentMyLocation.lng !== 0) {
      // 백엔드 장소 GET 코드
      const fetchLocation = async () => {
        try {
          const response = await getPlaces({
            type: type,
            dogSize: dogSize,
            // token: token,
          });
          setPlaces(response);
        } catch (error) {
          console.error("장소를 불러오는 데 실패했습니다.", error);
        }
      };

      fetchLocation();

      const mapOptions = {
        // 지도의 초기 중심 좌표
        // 사용자의 현재 위치를 가져오는 건 https 환경에서만 작동
        center: new LatLng(currentMyLocation.lat, currentMyLocation.lng),
        logoControl: false, // 네이버 로고 표시 X
        mapDataControl: false, // 지도 데이터 저작권 컨트롤 표시 X
        scaleControl: true, // 지도 축적 컨트롤의 표시 여부
        tileControl: 200, // 지도 타일을 전환할 때 페이드 인 효과의 지속 시간(밀리초)
        zoom: 15, //지도의 초기 줌 레벨
        zoomControl: true, // 줌 컨트롤 표시
        zoomContorlOptions: { position: 9 }, // 줌 컨트롤 우하단에 배치
      };
      mapRef.current = new Map("map", mapOptions);
    }
  }, [currentMyLocation, type, dogSize]);

  // 장소 정보 api 받아와서 마커 표시 및 정보창 띄우는 코드
  useEffect(() => {
    const createMarker = async (place) => {
      const { id, name, x, y } = place;

      const placeMarker = new Marker({
        key: id,
        title: name,
        position: new LatLng(x, y),
        map: mapRef.current,
      });

      naver.maps.Event.addListener(placeMarker, "click", () => {
        handleMarkerClick(place);
      });

      return placeMarker;
    };

    places.forEach((place) => {
      createMarker(place);
    });

    const handleMarkerClick = async (place) => {
      try {
        const response = await getPlaceInfo({ id: place.id });
        setSelectedPlace(response);
        setImages(response.images);
        setReviews(response.reviews);
      } catch (error) {
        console.error("장소 정보를 불러오는 데 실패했습니다.", error);
      }
      setIsOpen(place.isOpen);
      openModal();
    };
  }, [places]);

  const openModal = () => {
    setIsModalOpen(true);
  };
  const closeModal = () => {
    setIsModalOpen(false);
    setSelectedPlace(null);
    setIsFilterModalOpen(false);
  };

  const handleOverlayClick = (e) => {
    if (e.target !== e.currentTarget) {
      closeModal();
    }
  };

  const openFilterModal = () => setIsFilterModalOpen(true);
  const closeFilterModal = () => setIsFilterModalOpen(false);

  const handleFilterButtonClick = () => {
    openFilterModal();
  };

  // const handleCheckboxChange = (event) => {
  //   const { name, checked } = event.target;
  //   if (checked) {
  //     setDogSize((prev) => {
  //       if (prev.includes(name)) {
  //         return prev; // 이미 선택된 사이즈일 경우 이전 배열 그대로 반환
  //       } else {
  //         return [...prev, name]; // 선택된 사이즈 배열에 추가
  //       }
  //     });
  //   } else {
  //     setDogSize((prev) => prev.filter((size) => size !== name)); // 선택 해제된 사이즈를 배열에서 제거
  //   }
  // };

  const handleClickReview = () => {
    if (token) {
      if (isReview) {
        setIsReview(false);
      } else {
        setIsReview(true);
      }
    } else {
      alert("로그인이 필요한 서비스 입니다.");
      navigate("/login");
    }
  };

  const handleClickBookmark = async () => {
    const placeId = selectedPlace.id;

    try {
      if (selectedPlace && token) {
        await addBookmark({ placeId: placeId, token: token });
        alert("북마크가 추가되었습니다.");
      } else {
        alert("로그인이 필요합니다.");
      }
    } catch (error) {
      console.error("북마크 추가에 실패했습니다.", error);
    }
  };

  return (
    <div className="map-box">
      <div
        id="map"
        style={{ width: "100%", height: "710px" }}
        onClick={handleOverlayClick}
      />
      <div className="type-filter-container">
        <div className="type-filter-box">
          <div className="type-filter-btn" onClick={handleAllClick}>
            {type === "all" ? <img src={allSelec} /> : <img src={allNone} />}
          </div>
          <div className="type-filter-btn" onClick={handleBeautyClick}>
            {type === "BEAUTY" ? (
              <img src={beautySelec} />
            ) : (
              <img src={beautyNone} />
            )}
          </div>
          <div className="type-filter-btn" onClick={handleGoodsClick}>
            {type === "GOODS" ? (
              <img src={goodsSelec} />
            ) : (
              <img src={goodsNone} />
            )}
          </div>
          <div className="type-filter-btn" onClick={handleHospitalClick}>
            {type === "HOSPITAL" ? (
              <img src={hosSelec} />
            ) : (
              <img src={hosNone} />
            )}
          </div>
          <div className="type-filter-btn" onClick={handleCafeClick}>
            {type === "CAFE" ? <img src={cafeSelec} /> : <img src={cafeNone} />}
          </div>
        </div>
      </div>

      <div className="dogSize-filter-container">
        <div className="dogSize-filter-box">
          <button onClick={handleFilterButtonClick}>
            <img src={filter} />
            <span>필터</span>
          </button>
          <button>
            <img src={search} />
            <span>검색</span>
          </button>
          <button>
            <img src={footBlue} />
            <span>내 장소</span>
          </button>
        </div>
      </div>

      {isFilterModalOpen && (
        <PopupModal
          className="filter-modal"
          isOpen={isFilterModalOpen}
          closeModal={closeFilterModal}
        >
          <div className="dogSize-filter-btn-box">
            <div className="dogSize-filter-btn" onClick={handleNullClick}>
              {dogSize === null ? (
                <img src={size_all2} />
              ) : (
                <img src={size_all} />
              )}
              <div className="sizebox"></div>
            </div>

            <div className="dogSize-filter-btn" onClick={handleSmallClick}>
              {dogSize === "SMALL" ? (
                <img src={size_small2} />
              ) : (
                <img src={size_small} />
              )}
              <span>소형견 (10kg 미만)</span>
            </div>
            <div className="dogSize-filter-btn" onClick={handleMediumClick}>
              {dogSize === "MEDIUM" ? (
                <img src={size_mid2} />
              ) : (
                <img src={size_mid} />
              )}
              <span>중형견 (10kg~25kg 미만)</span>
            </div>
            <div className="dogSize-filter-btn" onClick={handleBigClick}>
              {dogSize === "BIG" ? (
                <img src={size_big2} />
              ) : (
                <img src={size_big} />
              )}
              <span>대형견 (25kg 이상)</span>
            </div>
          </div>
        </PopupModal>
      )}

      {selectedPlace && (
        <Modal isOpen={isModalOpen} closeModal={closeModal}>
          <div className="name-type-rate-bookmark">
            <div className="name-type-rate">
              <h2 className="place-name">{selectedPlace.name}</h2>
              <span className="place-type">{selectedPlace.placeType} |</span>
              <span className="place-rate">{selectedPlace.rate}</span>
            </div>

            <img className="bookmark-btn-img" src={bookmark} />
          </div>
          <hr className="info-window-line" />
          <div className="isOpen-add">
            <div className="isOpen-box">
              <img className="info-img" src={clockImg} alt="시계 이미지" />
              {{ isOpen } ? "영업 중" : "금일 영업 마감"}
            </div>
            <div className="place-add">
              <img className="info-img" src={addImg} alt="위치 이미지" />
              {selectedPlace.address}
            </div>
            <div className="place-call">
              <img className="info-img" src={phoneImg} alt="전화 이미지" />
              {selectedPlace.phoneNumber}
            </div>
          </div>
          <div className="place-imgs">
            {images.map((image, index) => {
              const base64URL = image.data;
              const imageURL = `data:image/jpeg;base64,${base64URL}`;

              return (
                <img
                  key={index}
                  className="place-img"
                  src={imageURL}
                  alt={"장소 이미지"}
                />
              );
            })}
          </div>
          <hr className="info-window-line" />
          <div className="review-box">
            <div className="review-info-box">
              <div className="review-info">
                <span>리뷰 | </span>
                <img className="rate-img" src={rateImg} />
                <span className="place-rate">{selectedPlace.rate}</span>
              </div>
              <button onClick={handleClickReview}>리뷰 쓰기</button>
            </div>

            {isReview && (
              <div className="review-form-box">
                <ReviewForm
                  id={selectedPlace.id}
                  currentToken={token}
                  onSubmit={handleReviewSubmit}
                />
              </div>
            )}
            <ReviewList reviews={reviews} footImg={footImg} />
          </div>
        </Modal>
      )}
    </div>
  );
}

export default Map;
