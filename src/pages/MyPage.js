import "./MyPage.css";
import pencil from "../img/reviewPen.png";
import ex1 from "../img/ex1.png";
import ex2 from "../img/ex2.png";
import ex3 from "../img/ex3.png";
import ex4 from "../img/ex4.png";
import ex5 from "../img/ex5.png";
import { useEffect, useState } from "react";
import {
  getUserData,
  updateUserData,
  postPetData,
  postPetImageData,
} from "../mypageapi";

function MyPage() {
  let token = localStorage.getItem("Authorization");
  const [userModifyMode, setUserModifyMode] = useState(false);
  const [showPetModal, setShowPetModal] = useState(false);
  const [showImageOptions, setShowImageOptions] = useState(false);
  const [showProfileImages, setShowProfileImages] = useState(false);
  const [nickname, setNickname] = useState("");
  const [petName, setPetName] = useState("");
  const [petType, setPetType] = useState("");
  const [petSex, setPetSex] = useState("");
  const [petAge, setPetAge] = useState("");
  const [petImage, setPetImage] = useState(null);
  const [currentImageIndex, setCurrentImageIndex] = useState(0);
  const imageUrls = [ex1, ex2, ex3, ex4, ex5];
  const [dogInfo, setDogInfo] = useState({});

  const handleCloseModal = (e) => {
    if (e.target.classList.contains("modal")) {
      setShowPetModal(false);
    }
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];

    if (file) {
      setPetImage(file);
    }
    setShowImageOptions(!showImageOptions);
  };

  const handleImageSelect = async (url) => {
    try {
      const response = await fetch(url);
      const blob = await response.blob();
      const file = new File([blob], "profileImage.png", { type: "image/png" });
      setPetImage(file);
    } catch (error) {
      console.error("Error fetching the image:", error);
    }
    setShowImageOptions(false);
  };

  useEffect(() => {
    const getUser = async () => {
      const response = await getUserData(token);

      setNickname(response.name);
      if (response.dog !== null) {
        setDogInfo(response.dog);
      }
    };
    getUser();
  }, [showPetModal]);

  return (
    <div className="mypage-container">
      <div className="user-wrapper">
        {userModifyMode ? (
          <div className="user-content">
            <div className="user-left-content">
              <input
                placeholder="수정할 닉네임을 작성해주세요!"
                value={nickname}
                onChange={(e) => {
                  setNickname(e.target.value);
                }}
              />
            </div>
            <div className="user-right-content">
              <div
                className="user-right-check"
                onClick={() => {
                  updateUserData(token, nickname);
                  setUserModifyMode(false);
                }}
              >
                확인하기
              </div>
            </div>
          </div>
        ) : (
          <div className="user-content">
            <div className="user-left-content">
              <span className="nickname">
                {nickname} <span className="nim">님</span>
              </span>

              <div className="review">
                <span className="review-text">작성한 리뷰</span>
                <span className="review-count">
                  {dogInfo.count === undefined ? 0 : dogInfo.count} 개
                </span>
              </div>
            </div>{" "}
            <div
              className="user-right-content"
              onClick={() => {
                setUserModifyMode(true);
              }}
            >
              <img src={pencil} width={15} height={15} />
              <span>닉네임 수정</span>
            </div>
          </div>
        )}
      </div>

      <div className="pet-wrapper">
        <div className="pet-content">
          {Object.keys(dogInfo).length !== 0 ? (
            <>
              <div className="pet-title">나의 반려동물</div>
              <div className="pet-info">
                <div className="pet-info-left">
                  {dogInfo.image?.data && (
                    <img
                      src={`data:image/png;base64,${dogInfo.image.data}`}
                      alt="Pet"
                    />
                  )}
                </div>
                <div className="pet-info-right">
                  <div className="pet-info-name">{dogInfo.dogName}</div>
                  <div className="pet-info-type">{dogInfo.species}</div>
                  <div className="pet-info-2">
                    <div className="pet-info-sex">{dogInfo.gender}</div>
                    <div className="pet-info-age">
                      {dogInfo.registrationNumber}
                    </div>
                  </div>
                </div>
              </div>
              <div
                className="pet-info-edit"
                onClick={() => setShowPetModal(true)}
              >
                <img src={pencil} width={18} height={18} />
                반려견정보 수정
              </div>
            </>
          ) : (
            <>
              <div className="nopet-title">등록된 반려견 정보가 없습니다.</div>
              <div className="nopet-desc">
                반려견을 등록하고 알맞은 장소로 떠나보세요!
              </div>
              <div
                className="nopet-enroll"
                onClick={() => setShowPetModal(true)}
              >
                등록하기
              </div>
            </>
          )}
        </div>
      </div>

      {showPetModal && (
        <div className="modal" onClick={handleCloseModal}>
          <div className="modal-content">
            <div className="modal-header">
              <h2>나의 반려동물</h2>
            </div>
            <div className="modal-body">
              <div className="pet-info">
                <div className="pet-info-left">
                  <div className="image-placeholder">
                    {showImageOptions && (
                      <div className="image-options">
                        <label className="image-option">
                          이미지 불러오기
                          <input
                            type="file"
                            style={{ display: "none" }}
                            onChange={handleImageChange}
                          />
                        </label>
                        <div
                          className="image-option"
                          onClick={() => {
                            setShowProfileImages(true);
                            setShowImageOptions(false);
                          }}
                        >
                          무료 프로필 이미지 사용
                        </div>
                      </div>
                    )}
                    {showProfileImages ? (
                      <div className="profile-image-modal">
                        <div className="image-slider">
                          <img
                            src={imageUrls[currentImageIndex]}
                            className="selected-image"
                            alt={`profile-${currentImageIndex}`}
                          />
                          <div className="pagination">
                            {imageUrls.map((_, index) => (
                              <div
                                key={index}
                                className={`dot ${
                                  index === currentImageIndex ? "active" : ""
                                }`}
                                onClick={() => setCurrentImageIndex(index)}
                              />
                            ))}
                          </div>
                        </div>
                        <div
                          className="select-button"
                          onClick={() => {
                            setShowProfileImages(false);
                            handleImageSelect(imageUrls[currentImageIndex]);
                          }}
                        >
                          선택하기
                        </div>
                      </div>
                    ) : (
                      <>
                        {petImage ? (
                          <img
                            src={URL.createObjectURL(petImage)}
                            alt="Pet"
                            className="pet-image"
                          />
                        ) : (
                          <div
                            className="circle"
                            onClick={() =>
                              setShowImageOptions(!showImageOptions)
                            }
                          >
                            <button className="plus-button">+</button>
                          </div>
                        )}
                      </>
                    )}
                  </div>
                </div>
                <div className="pet-info-right">
                  <input
                    type="text"
                    name="name"
                    value={petName}
                    onChange={(e) => {
                      setPetName(e.target.value);
                    }}
                    placeholder="이름을 입력해주세요."
                  />
                  <input
                    type="text"
                    name="type"
                    value={petType}
                    onChange={(e) => {
                      setPetType(e.target.value);
                    }}
                    placeholder="견종을 입력해주세요."
                  />
                  <div className="pet-info-2">
                    <select
                      className="modal-select"
                      name="sex"
                      value={petSex}
                      onChange={(e) => {
                        setPetSex(e.target.value);
                      }}
                    >
                      <option value="" disabled>
                        성별
                      </option>
                      <option value="암컷">암컷</option>
                      <option value="수컷">수컷</option>
                      <option value="중성화">중성화</option>
                    </select>
                    <input
                      type="text"
                      name="age"
                      value={petAge}
                      onChange={(e) => {
                        setPetAge(e.target.value);
                      }}
                      placeholder="태어난 날을 입력해주세요."
                    />
                  </div>
                </div>
              </div>
            </div>
            <div className="modal-footer">
              <button
                onClick={async () => {
                  await postPetImageData(token, petImage);
                  await postPetData(token, petName, petType, petSex, petAge);
                  setShowPetModal(false);
                }}
              >
                등록하기
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default MyPage;
