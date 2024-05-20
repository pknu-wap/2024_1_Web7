import { useState } from "react";
import "./ReviewForm.css";
import { createReview } from "../api";
import StarRating from "./StarRating";

const INITIAL_VALUES = {
  content: "",
  rate: "0",
  placeId: "",
};

const validate = (input) => {
  const { content, rate } = input;
  const errors = {};

  if (content === "") {
    errors.content = "리뷰 내용이 입력되지 않았습니다.";
  }
  if (rate === "0") {
    errors.rate = "별점이 입력되지 않았습니다.";
  }
};

function ReviewForm({ id, currentToken, onSubmit }) {
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [values, setValues] = useState(INITIAL_VALUES);

  const handleReviewSubmit = async (e) => {
    e.preventDefault();

    const body = {
      rate: values.rate,
      content: values.content,
      placeId: id,
    };

    let result;

    try {
      setIsSubmitting(true);
      result = await createReview(body, currentToken);
      onSubmit(result);
    } catch (error) {
      console.log(error);
    } finally {
      setIsSubmitting(false);
    }
    setValues(INITIAL_VALUES);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setValues((preValues) => ({
      ...preValues,
      [name]: value,
    }));
  };

  const handleSetRate = (rate) => {
    setValues((preValues) => ({
      ...preValues,
      rate,
    }));
  };

  return (
    <form className="ReviewForm" onSubmit={handleReviewSubmit}>
      <div className="ReviewForm-rating-box">
        <StarRating
          onSetRate={handleSetRate}
          defaultRate={values.rate}
          className="aaa"
        />
        <span>별점을 등록해주세요!</span>
      </div>

      <textarea
        className="ReviewForm-content"
        name="content"
        value={values.content}
        placeholder="100자 이내 리뷰를 작성해주세요!"
        onChange={handleChange}
        maxLength={100}
      />

      <button
        className="ReviewForm-submit-btn"
        type="submit"
        disabled={isSubmitting}
      >
        리뷰 등록
      </button>
    </form>
  );
}

export default ReviewForm;
