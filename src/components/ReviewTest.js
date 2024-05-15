import React from "react";

const ReviewTest = () => {
  const submitReview = async () => {
    const url =
      "http://dogventure-be-dev.ap-northeast-2.elasticbeanstalk.com/api/place/review/write";

    const headers = {
      "Content-Type": "application/json",
      Authorization:
        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRlc3Q0QHRlc3QuY29tIiwiYXV0aG9yaXR5IjoiVVNFUiIsImlhdCI6MTcxNTc3Njg4MSwiZXhwIjoxNzE1NzgwNDgxfQ.fREcAoZ07rv2dMeAtNHDv0kwzUk9RdjpAkADaWXDLAg",
    };

    const body = JSON.stringify({
      rate: "5.0",
      content: "test",
      placeId: "1",
    });

    try {
      const response = await fetch(url, {
        method: "POST",
        headers: headers,
        body: body,
      });

      if (!response.ok) {
        throw new Error("er");
      }

      const data = await response.json();
      console.log("Success:", data);
    } catch (error) {
      console.error("Error:", error);
    }
  };

  return (
    <div>
      <button onClick={submitReview}>Submit Review</button>
    </div>
  );
};

export default ReviewTest;
