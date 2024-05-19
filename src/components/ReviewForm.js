import { useState } from "react";
import "./ReviewForm.css";
import { createReview } from "../api";

const INITIAL_VALUES = {
  content: "",
  rate: "5.0",
  placeId: "",
};

function ReviewForm({ id, currentToken, onSubmitSuccess }) {
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
    } catch (error) {
      console.log(error);
    } finally {
      setIsSubmitting(false);
    }
    // const { content } = result;
    setValues(INITIAL_VALUES);
    // onSubmitSuccess(content);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setValues((preValues) => ({
      ...preValues,
      [name]: value,
    }));
  };

  return (
    <form className="ReviewForm" onSubmit={handleReviewSubmit}>
      <div className="ReviewForm-rating-box">
        <span>별점을 등록해주세요!</span>
        <input
          className="ReviewForm-rate"
          type="number"
          name="rate"
          value={values.rate}
          min={0}
          max={5}
          onChange={handleChange}
        />
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
