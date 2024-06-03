function ReviewEdit() {
  const token = localStorage.getItem("Authorization");
  async function updateReview(token) {
    const body = {
      reviewId: 10,
      placeId: 1,
      rate: 3,
      content: "별로",
    };
    try {
      const response = await fetch(
        `${process.env.REACT_APP_API_URL}api/place/review/update/10`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(body),
        }
      );
    } catch (error) {
      console.error("리뷰를 수정하는 데 실패했습니다.", error);
      throw error;
    }
  }

  return <buttton onClick={() => updateReview(token)}>리뷰 수정</buttton>;
}

export default ReviewEdit;
