import { useState } from "react";
import "./ReviewForm.css";
import { createReview } from "../api";

const INITIAL_VALUES = {
  content: "",
  rate: "5.0",
  placeId: "",
};

function ReviewForm({ id, currentToken }) {
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [values, setValues] = useState(INITIAL_VALUES);

  const handleReviewSubmit = async (e) => {
    e.preventDefault();

    let result;

    try {
      setIsSubmitting(true);
      result = await createReview(
        {
          rate: "5.0",
          content: values.content,
          placeId: id,
        },
        currentToken
      );
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

  return (
    <form className="ReviewForm" onSubmit={handleReviewSubmit}>
      <div className="ReviewForm-rating">별점을 등록해주세요!</div>

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
