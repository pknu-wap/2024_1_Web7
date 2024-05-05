const BASE_URL = "http://localhost:8080";

export async function searchPlaces({ word = "" }) {
  const query = `word=${word}`;
  const response = await fetch(
    `${BASE_URL}/api/map/naver/quest/place/search/${query}`
  );
  if (!response.ok) {
    throw new Error("데이터를 불러오는데 실패했습니다.");
  }
  const body = await response.json();
  return body;
}