import { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../function/AuthContext";

import useGeolocation from "../hooks/useGeolocation";
import {
  getPlaces,
  getPlaceInfo,
  addBookmark,
  searchPlaces,
  getPlaceLoginInfo,
  getMyPlaces,
  getUserName,
} from "../api";

import Modal from "./Modal";
import PopupModal from "./PopupModal";
import ReviewForm from "./ReviewForm";

import footImg from "../img/foot_green.png";
import clockImg from "../img/inOpen.png";
import addImg from "../img/mapLocation.png";
import phoneImg from "../img/phoneImg.png";
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
import searchImg from "../img/search.png";
import footBlue from "../img/foot_blue.png";
import footGreen from "../img/foot_red.png";
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
import reviewPen from "../img/reviewPen.png";

import "./Map.css";
import MapSearch from "./MapSearch";
import ReviewEdit from "./ReviewEdit";

function Map() {
  const mapRef = useRef(null);
  const { naver } = window;
  const { currentMyLocation } = useGeolocation();
  const { LatLng, Map, Marker } = naver.maps; // 필요한 객체를 비구조화 할당

  const navigate = useNavigate();

  const [places, setPlaces] = useState([]);
  const [type, setType] = useState("all");
  const [isOpen, setIsOpen] = useState(true);
  const [dogSize, setDogSize] = useState(null);
  const [reviews, setReviews] = useState([]);
  const [images, setImages] = useState([]);
  const [search, setSearch] = useState("");
  const [isBookmark, setIsBookmark] = useState(false);
  const [isMyPlaces, setIsMyPlaces] = useState(false);
  const [username, SetUsername] = useState("");
  const [reviewFormData, setReviewFormData] = useState({
    rate: null,
    content: "",
  });
  const [isEdit, setIsEdit] = useState(false);
  const [currentReviewId, setCurrentReviewId] = useState("");

  const [selectedPlace, setSelectedPlace] = useState(null);

  const [isFilterModalOpen, setIsFilterModalOpen] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isReview, setIsReview] = useState(false);
  const [reviewUpdate, setReviewUpdate] = useState(false);
  const [isSearch, setIsSearch] = useState(false);

  let token = localStorage.getItem("Authorization");

  useEffect(() => {
    const currentUsername = async () => {
      if (token) {
        try {
          const username = await getUserName({ token: token });
          SetUsername(username);
        } catch (error) {
          console.error("유저 아이디를 찾지 못했습니다.", error);
        }
      }
    };

    currentUsername();
  }, [token]);

  const handleAllClick = () => {
    setType("all");
    setSearch("");
  };
  const handleCafeClick = () => {
    setType("CAFE");
    setSearch("");
  };
  const handleHospitalClick = () => setType("HOSPITAL");
  const handleBeautyClick = () => {
    setType("BEAUTY");
    setSearch("");
  };
  const handleGoodsClick = () => {
    setType("GOODS");
    setSearch("");
  };

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

  function StarRating({ rate }) {
    const stars = [];
    for (let i = 0; i < rate; i++) {
      stars.push(<img key={i} src={rateImg} alt="별점" />);
    }

    return <span className="user-rate">{stars}</span>;
  }

  function ReviewList({ reviews, footImg }) {
    return (
      <ul className="review-ul">
        {reviews.map((review) => {
          const base64URL = review.user.image?.data
            ? `data:image/jpeg;base64,${review.user.image.data}`
            : footImg;

          return (
            <li className="review-list" key={review.id}>
              <img
                className="review-profile-img"
                src={base64URL}
                alt={`${review.user.username}'s profile`}
              />
              <div className="review-content-box">
                <div className="review-user-rate-update">
                  <div className="review-user-rate">
                    <span className="review-username">
                      {review.user.username}
                    </span>
                    <span className="review-rate ">
                      <StarRating rate={review.rate} />
                    </span>
                  </div>

                  {review.user.username === username && (
                    <button onClick={() => handleReviewEdit(review)}>
                      리뷰 수정
                    </button>
                  )}
                </div>
                <div className="review-content">
                  <div className="review-content-inbox">{review.content}</div>
                </div>
              </div>
            </li>
          );
        })}
      </ul>
    );
  }

  const handleReviewEdit = (review) => {
    setReviewFormData({
      reviewId: review.id,
      rate: review.rate,
      content: review.content,
      placeId: selectedPlace.id,
    });
    setCurrentReviewId(review.id);
    setIsEdit(true);
    setIsReview(true);
  };

  const handleReviewSubmit = (submittedReview) => {
    setReviews((prevReviews) => {
      // const reviewIndex = prevReviews.findIndex(
      //   (review) => review.id === submittedReview.reviewId
      // );

      if (!isEdit) {
        return [...prevReviews, submittedReview];
      }

      // if (reviewIndex !== -1) {
      //   // 기존 리뷰를 찾아서 수정된 리뷰로 대체
      //   return prevReviews.map((review) =>
      //     review.id === reviewIndex ? submittedReview : review
      //   );
      // } else {
      //   // 새로운 리뷰 추가
      //   return [...prevReviews, submittedReview];
      // }
    });

    setIsReview(false);
  };

  // 리뷰 실시간 업데이트
  // useEffect(() => {
  //   const place = selectedPlace;

  //   const handleReviewUpdate = async () => {
  //     try {
  //       const response = await getPlaceLoginInfo({
  //         id: place.id,
  //         token: token,
  //       });
  //       setReviews(response.reviews);
  //     } catch (error) {}
  //   };

  //   handleReviewUpdate();
  // }, [reviewUpdate]);

  // 필터 별 마커 표시
  useEffect(() => {
    if (currentMyLocation.lat !== 0 && currentMyLocation.lng !== 0) {
      // 백엔드 장소 GET 코드
      const fetchLocation = async () => {
        let response;
        try {
          if (search) {
            response = await searchPlaces({ word: search });
          } else if (isMyPlaces) {
            response = await getMyPlaces({ token: token });
          } else {
            response = await getPlaces({
              type: type,
              dogSize: dogSize,
              // token: token,
            });
          }

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
  }, [currentMyLocation, type, dogSize, search, isMyPlaces]);

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
      if (token) {
        try {
          const response = await getPlaceLoginInfo({
            id: place.id,
            token: token,
          });
          setSelectedPlace(response);
          setImages(response.images);
          setReviews(response.reviews);
          setIsBookmark(response.bookmark);
        } catch (error) {
          console.error("저장된 장소 정보를 불러오는 데 실패했습니다.", error);
        }
      } else if (!token) {
        try {
          const response = await getPlaceInfo({ id: place.id });
          setSelectedPlace(response);
          setImages(response.images);
          setReviews(response.reviews);
        } catch (error) {
          console.error("장소 정보를 불러오는 데 실패했습니다.", error);
        }
      }

      setIsOpen(place.isOpen);
      openModal();
    };
  }, [places]);

  // 모달 재렌더링
  useEffect(() => {
    setIsModalOpen(true);
  }, [isBookmark]);

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

  const handleClickSearch = () => {
    setIsSearch(!isSearch);
  };

  const handleSearchSubmit = async (e) => {
    e.preventDefault();
    const searchTerm = e.target["search"].value;
    setSearch(searchTerm);
    setType("all");
    setIsSearch(false);
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

    if (isBookmark) {
      try {
        if (selectedPlace && token) {
          await addBookmark({ placeId: placeId, token: token });
          alert("북마크가 최소되었습니다.");
        } else {
          alert("로그인이 필요합니다.");
        }
      } catch (error) {
        console.error("북마크 추가에 실패했습니다.", error);
      }
    } else {
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
    }

    setIsBookmark(!isBookmark);
  };

  const handleClickMyPlace = () => {
    setIsMyPlaces(!isMyPlaces);
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
          <button onClick={handleClickSearch}>
            <img src={searchImg} />
            <span>검색</span>
          </button>
          {token &&
            (isMyPlaces ? (
              <button onClick={handleClickMyPlace}>
                <img src={footGreen} />
                <span>내 장소</span>
              </button>
            ) : (
              <button onClick={handleClickMyPlace}>
                <img src={footBlue} />
                <span>내 장소</span>
              </button>
            ))}
        </div>
      </div>

      {isSearch && <MapSearch handleSearchSubmit={handleSearchSubmit} />}

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
              <h2 className="place-name">{selectedPlace.name} </h2>
              <span className="place-type">{selectedPlace.placeType} |</span>
              <span className="place-rate">
                {selectedPlace.rate ? (
                  <>
                    <img className="rate-img2" src={rateImg} />
                    {selectedPlace.rate.toFixed(1)}
                  </>
                ) : (
                  <div>등록된 별점이 없습니다.</div>
                )}
              </span>
            </div>
            {token && (
              <>
                {isBookmark ? (
                  <img
                    onClick={handleClickBookmark}
                    className="bookmark-btn-img"
                    src={bookmark2}
                  />
                ) : (
                  <img
                    onClick={handleClickBookmark}
                    className="bookmark-btn-img"
                    src={bookmark}
                  />
                )}
              </>
            )}
          </div>
          <hr className="info-window-line" />
          <div className="isOpen-add">
            <div className="isOpen-box">
              <img className="info-img" src={clockImg} alt="시계 이미지" />
              {{ isOpen } ? "영업 중" : "금일 영업 마감"}
              <div className="isOpen-time">
                <div>|</div>
                <div>
                  {selectedPlace.openTime} - {selectedPlace.endTime}
                </div>
              </div>
            </div>
            <div className="place-add">
              <img
                className="info-img location"
                src={addImg}
                alt="위치 이미지"
              />
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
                <span className="place-rate">
                  {selectedPlace.rate.toFixed(1)}
                </span>
              </div>
              <button onClick={handleClickReview}>
                <img className="reviewPen-img" src={reviewPen} />
                리뷰 쓰기
              </button>
            </div>

            {isReview && (
              <div className="review-form-box">
                <ReviewForm
                  id={selectedPlace.id}
                  currentToken={token}
                  onSubmit={handleReviewSubmit}
                  reviewUpdate={reviewUpdate}
                  setReviewUpdate={setReviewUpdate}
                  reviewFormData={reviewFormData}
                  isEdit={isEdit}
                  setIsEdit={setIsEdit}
                  currentReviewId={currentReviewId}
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
