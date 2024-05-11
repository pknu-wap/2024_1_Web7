import { useEffect, useRef, useState } from "react";
import useGeolocation from "../hooks/useGeolocation";
import { getPlaces, getPlaceInfo } from "../api";
import Modal from "./Modal";
import clockImg from "../img/clock.png";
import addImg from "../img/location.png";
import phoneImg from "../img/phone.png";
import "./Map.css";

function Map() {
  const mapRef = useRef(null);
  const { naver } = window;
  const { currentMyLocation } = useGeolocation();
  const { LatLng, Map, Marker } = naver.maps; // 필요한 객체를 비구조화 할당
  const [places, setPlaces] = useState([]);
  const [type, setType] = useState("all");
  const [selectedPlace, setSelectedPlace] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isOpen, setIsOpen] = useState(true);

  const handleAllClick = () => {
    setType("all");
  };

  const handleCafeClick = () => {
    setType("CAFE");
  };

  const handleHospitalClick = () => {
    setType("HOSPITAL");
  };

  useEffect(() => {
    if (currentMyLocation.lat !== 0 && currentMyLocation.lng !== 0) {
      // 백엔드 장소 GET 코드
      const fetchLocation = async () => {
        try {
          const response = await getPlaces({ type: type });
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
  }, [currentMyLocation, type]);

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
  };

  const handleOverlayClick = (e) => {
    if (e.target !== e.currentTarget) {
      closeModal();
    }
  };

  return (
    <div className="map-box">
      <div
        id="map"
        style={{ width: "100%", height: "800px" }}
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
            <div>
              <span>리뷰 |</span>
              <span className="place-rate">{selectedPlace.rate}</span>
            </div>
            <button>리뷰 쓰기</button>
          </div>
        </Modal>
      )}
    </div>
  );
}

export default Map;
