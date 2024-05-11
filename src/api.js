import { type } from "@testing-library/user-event/dist/type";

// 비로그인 상태 접속 시 장소 리스트 및 필터
export async function getPlaces({ type = "", dogSize = null }) {
  try {
    const response = await fetch(
      `${process.env.REACT_APP_API_URL}api/map/naver/guest/place/all`
    );
    const body = await response.json();
    let filterPlaces = body;

    if (type === "CAFE") {
      filterPlaces = body.filter((place) => place.placeType === "CAFE");
    } else if (type === "HOSPITAL") {
      filterPlaces = body.filter((place) => place.placeType === "HOSPITAL");
    }

    return filterPlaces;
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
