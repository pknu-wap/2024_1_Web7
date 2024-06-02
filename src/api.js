// 비 로그인시 장소만 필터
export async function getTypePlaces({ type = "" }) {
  try {
    const response = await fetch(
      `${process.env.REACT_APP_API_URL}api/map/naver/guest/place/all`
    );

    const body = await response.json();

    const filteredPlaces = body.filter((place) => {
      if (type !== "all" && place.placeType !== type) {
        return false;
      }
      return true;
    });

    return filteredPlaces;
  } catch (error) {
    console.error("장소를 불러오는 데 실패했습니다.", error);
    throw error;
  }
}

// 비로그인 상태 접속 시 타입, 사이즈 필터
export async function getPlaces({ type = "all", dogSize = null }) {
  try {
    const response = await fetch(
      `${process.env.REACT_APP_API_URL}api/map/naver/guest/place/all`,
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );

    const body = await response.json();

    if (type === "all" && dogSize === null) {
      return body;
    }

    const filteredPlaces = body.filter((place) => {
      if (type !== "all" && place.placeType !== type) return false;
      if (dogSize !== null && place.dogSize !== dogSize) return false;
      return true;
    });

    return filteredPlaces;
  } catch (error) {
    console.error("장소를 불러오는 데 실패했습니다.", error);
    throw error;
  }
}

// 로그인 시 북마크 된 장소 불러오기
export async function getMyPlaces({ token }) {
  try {
    const response = await fetch(
      `${process.env.REACT_APP_API_URL}api/map/naver/place/all`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      }
    );

    const body = await response.json();
    const filteredPlaces = body.filter((place) => place.bookmark === true);

    return filteredPlaces;
  } catch (error) {
    console.error("장소를 불러오는 데 실패했습니다.", error);
    throw error;
  }
}

// 장소 상세 정보 불러오기
export async function getPlaceInfo({ id = "" }) {
  try {
    const response = await fetch(
      `${process.env.REACT_APP_API_URL}api/map/naver/guest/place/${id}`
    );
    const body = await response.json();
    return body;
  } catch (error) {
    console.error("장소 정보를 불러오는 데 실패했습니다.", error);
    throw error;
  }
}

// 로그인 시 장소 상세 정보 (북마크)
export async function getPlaceLoginInfo({ id = "", token }) {
  try {
    const response = await fetch(
      `${process.env.REACT_APP_API_URL}api/map/naver/place/${id}`,
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      }
    );
    const body = await response.json();
    return body;
  } catch (error) {
    console.error("장소 정보를 불러오는 데 실패했습니다.", error);
    throw error;
  }
}

// 검색된 장소
export async function searchPlaces({ word = "" }) {
  try {
    const response = await fetch(
      `${
        process.env.REACT_APP_API_URL
      }api/map/naver/guest/place/search/${encodeURIComponent(word)}`
    );
    const body = await response.json();
    return body;
  } catch (error) {
    console.error("검색 장소를 불러오는 데 실패했습니다.", error);
    throw error;
  }
}

// 리뷰 생성
export async function createReview({ rate, content, placeId }, token) {
  try {
    const response = await fetch(
      `${process.env.REACT_APP_API_URL}api/place/review/write`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          rate: rate,
          content: content,
          placeId: placeId,
        }),
      }
    );
    const data = await response.json();
    console.log(data);
  } catch (error) {
    console.error("리뷰를 작성하는 데 실패했습니다.", error);
    throw error;
  }
}

export async function updateReview(
  { reviewId, placeId, rate, content },
  token
) {
  try {
    const response = await fetch(
      `${process.env.REACT_APP_API_URL}api/place/review/update/${reviewId}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          reviewId: reviewId,
          placeId: placeId,
          rate: rate,
          content: content,
        }),
      }
    );
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("리뷰를 수정하는 데 실패했습니다.", error);
    throw error;
  }
}

// 현재 사용자 name 가져오기
export async function getUserName({ token }) {
  try {
    const response = await fetch(`${process.env.REACT_APP_API_URL}api/mypage`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    });
    const { name } = await response.json();
    return name;
  } catch (error) {
    console.error("사용자의 아이디를 가져오는 데 실패했습니다.", error);
    throw error;
  }
}

export async function addBookmark({ placeId, token }) {
  try {
    const response = await fetch(
      `${process.env.REACT_APP_API_URL}api/bookmark`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          id: placeId,
        }),
      }
    );
    const body = await response.json();
    return body.id;
  } catch (error) {
    console.error("즐겨찾기 오류", error);
  }
}
