import { type } from "@testing-library/user-event/dist/type";

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
export async function getPlaces({ type = "all", dogSizes = [] }) {
  try {
    const response = await fetch(
      `${process.env.REACT_APP_API_URL}api/map/naver/guest/place/all`
    );

    const body = await response.json();

    if (type === "all" && dogSizes.length === 0) {
      return body;
    }

    const filteredPlaces = body.filter((place) => {
      if (type !== "all" && place.placeType !== type) return false;
      if (dogSizes.length === 0) return true;

      // 강아지 사이즈가 하나 이상 선택된 경우, 장소의 강아지 사이즈가 선택된 사이즈 중 하나 이상을 포함하면 반환
      return dogSizes.some((size) => place.dogSize.includes(size));
    });

    return filteredPlaces;
  } catch (error) {
    console.error("장소를 불러오는 데 실패했습니다.", error);
    throw error;
  }
}

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

// export async function createReview(formData) {
//   try {
//     const response = await fetch(
//       `${process.env.REACT_APP_API_URL}api/place/review/write`,
//       {
//         method: "POST",
//         headers: { "Content-Type": "application/json" },
//         body: JSON.stringify(formData),
//       }
//     );
//     const body = await response.json();
//     return body;
//   } catch (error) {
//     console.error("리뷰를 작성하는 데 실패했습니다.", error);
//     throw error;
//   }
// }

export async function createReview(formData, token) {
  try {
    for (const [key, value] of formData.entries()) {
      console.log(`${key}: ${value}`);
    }
    const response = await fetch(
      `${process.env.REACT_APP_API_URL}api/place/review/write`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(formData),
      }
    );
    const body = await response.json();
    return body.id;
  } catch (error) {
    console.error("리뷰를 작성하는 데 실패했습니다.", error);
    throw error;
  }
}

export async function addBookmark(id, token) {
  try {
    const response = await fetch(
      `${process.env.REACT_APP_API_URL}api/bookmark`,
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(id),
      }
    );
    const body = await response.json();
    return body.id;
  } catch (error) {
    console.error("즐겨찾기에 실패했습니다.", error);
    throw error;
  }
}
