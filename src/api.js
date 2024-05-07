export async function getPlaces() {
  try {
    const response = await fetch(
      `${process.env.REACT_APP_API_URL}api/map/naver/guest/place/all`,
      {
        method: "GET",
        headers: { "Content-Type": "application/json" },
      }
    );
    // if (!response.ok) {
    //   throw new Error("데이터를 불러오는데 실패했습니다.");
    // }
    const body = await response.json();
    return body;
  } catch (error) {
    console.error("장소를 불러오는 데 실패했습니다.", error);
    throw error;
  }
}
