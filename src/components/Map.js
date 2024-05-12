import { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../function/AuthContext";

import useGeolocation from "../hooks/useGeolocation";
import { getPlaces, getPlaceInfo } from "../api";

import Modal from "./Modal";
import PopupModal from "./PopupModal";
import ReviewForm from "./ReviewForm";

import clockImg from "../img/clock.png";
import addImg from "../img/location.png";
import phoneImg from "../img/phone.png";

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
  const [selectedDogSizes, setSelectedDogSizes] = useState([]);
  const [reviews, setReviews] = useState([]);

  const [selectedPlace, setSelectedPlace] = useState(null);

  const [isFilterModalOpen, setIsFilterModalOpen] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isReview, setIsReview] = useState(false);

  const token = localStorage.getItem("Authorization");

  const handleAllClick = () => setType("all");
  const handleCafeClick = () => setType("CAFE");
  const handleHospitalClick = () => setType("HOSPITAL");

  useEffect(() => {
    if (currentMyLocation.lat !== 0 && currentMyLocation.lng !== 0) {
      // 백엔드 장소 GET 코드
      const fetchLocation = async () => {
        try {
          const response = await getPlaces({
            type: type,
            dogSizes: selectedDogSizes,
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
  }, [currentMyLocation, type, selectedDogSizes]);

  // 장소 정보 api 받아와서 마커 표시 및 정보창 띄우는 코드
  useEffect(() => {
    const createMarker = (place) => {
      const { id, name, x, y } = place;

      return new Marker({
        key: id,
        title: name,
        position: new LatLng(x, y),
        map: mapRef.current,
      });
    };

    const handleMarkerClick = async (place) => {
      try {
        const response = await getPlaceInfo({ id: place.id });
        setSelectedPlace(response);
        setReviews(response.reviews);
      } catch (error) {
        console.error("장소 정보를 불러오는 데 실패했습니다.", error);
      }
      setIsOpen(place.isOpen);
      openModal();
    };

    places.forEach((place) => {
      const placeMarker = createMarker(place);
      naver.maps.Event.addListener(placeMarker, "click", () => {
        handleMarkerClick(place);
      });
    });
  }, [places]);

  const openModal = () => setIsModalOpen(true);
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

  const handleCheckboxChange = (event) => {
    const { name, checked } = event.target;
    if (checked) {
      setSelectedDogSizes((prev) => {
        if (prev.includes(name)) {
          return prev; // 이미 선택된 사이즈일 경우 이전 배열 그대로 반환
        } else {
          return [...prev, name]; // 선택된 사이즈 배열에 추가
        }
      });
    } else {
      setSelectedDogSizes((prev) => prev.filter((size) => size !== name)); // 선택 해제된 사이즈를 배열에서 제거
    }
  };

  const handleClickReview = () => {
    if (token) {
      setIsReview(true);
    } else {
      alert("로그인이 필요한 서비스 입니다.");
      navigate("/login");
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
          <button onClick={handleAllClick}>ALL</button>
          <button>미용</button>
          <button>애견용품</button>
          <button onClick={handleHospitalClick}>병원</button>
          <button onClick={handleCafeClick}>카페</button>
        </div>
      </div>

      <div className="dogSize-filter-container">
        <div className="dogSize-filter-box">
          <button onClick={handleFilterButtonClick}>필터</button>
          <button>검색</button>
          <button>내 장소</button>
        </div>
      </div>

      {isFilterModalOpen && (
        <PopupModal
          className="filter-modal"
          isOpen={isFilterModalOpen}
          closeModal={closeFilterModal}
        >
          <div className="filter-btn-box">
            <label>
              <input
                type="checkbox"
                name="SMALL"
                onChange={handleCheckboxChange}
                checked={selectedDogSizes.includes("SMALL")}
              />
              소형
            </label>
            <label>
              <input
                type="checkbox"
                name="MEDIUM"
                onChange={handleCheckboxChange}
                checked={selectedDogSizes.includes("MEDIUM")}
              />
              중형
            </label>
            <label>
              <input
                type="checkbox"
                name="BIG"
                onChange={handleCheckboxChange}
                checked={selectedDogSizes.includes("BIG")}
              />
              대형
            </label>
          </div>
        </PopupModal>
      )}

      {selectedPlace && (
        <Modal isOpen={isModalOpen} closeModal={closeModal}>
          <div className="name-type-rate">
            <h2 className="place-name">{selectedPlace.name}</h2>
            <span className="place-type">{selectedPlace.placeType} |</span>
            <span className="place-rate">{selectedPlace.rate}</span>
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
          <div>
            <img
              className="place-img"
              src={`data:image/jpeg;base64,${selectedPlace.image.data}`}
            />
          </div>
          <hr className="info-window-line" />
          <div className="review-box">
            <div className="review-info-box">
              <div className="review-info">
                <span>리뷰 |</span>
                <span className="place-rate">{selectedPlace.rate}</span>
              </div>
              <button onClick={handleClickReview}>리뷰 쓰기</button>
            </div>

            {isReview && (
              <div className="review-form-box">
                <ReviewForm placeId={selectedPlace.id} token={token} />
              </div>
            )}
            <ul>
              {reviews.map((review) => {
                return (
                  <li key={review.id}>
                    <div className="profile-img">
                      <div>프로필 이미지</div>
                    </div>
                    <div>
                      <span>{review.User.username}</span>
                      <span>{review.rate}</span>
                      <div className="reivew-content">{review.content}</div>
                    </div>
                  </li>
                );
              })}
            </ul>
          </div>
        </Modal>
      )}
    </div>
  );
}

export default Map;
