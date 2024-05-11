import { type } from "@testing-library/user-event/dist/type";

// 비로그인 상태 접속 시 장소 리스트 및 필터
export async function getPlaces({ type = "", dogSize = null }) {
  try {
    const response = await fetch(
      `${process.env.REACT_APP_API_URL}api/map/naver/guest/place/all`
    );

    const body = await response.json();
    let typeFilterPlaces = body;

    if (type === "CAFE") {
      typeFilterPlaces = body.filter((place) => place.placeType === "CAFE");
    } else if (type === "HOSPITAL") {
      typeFilterPlaces = body.filter((place) => place.placeType === "HOSPITAL");
    }

    let dogSizeFilterPlaces = typeFilterPlaces;

    if (dogSize === "SMALL") {
      dogSizeFilterPlaces = body.filter((place) => place.dogSize === "SMALL");
    } else if (dogSize === "MEDIUM") {
      dogSizeFilterPlaces = body.filter((place) => place.dogSize === "MEDIUM");
    } else if (dogSize === "BIG") {
      dogSizeFilterPlaces = body.filter((place) => place.dogSize === "BIG");
    }

    return dogSizeFilterPlaces;
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
