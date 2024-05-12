import { useState } from "react";
import { createReviewform } from "../api";
import "./ReviewForm.css";

function sanitize(type, value) {
  switch (type) {
    case "number":
      return Number(value) || 0;

    default:
      return value;
  }
}

const INITIAL_VALUES = {
  content: "",
};

function ReviewForm({ onSubmitSuccess, review }) {
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [values, setValues] = useState(INITIAL_VALUES);

  const handleReviewSubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData();
    formData.append("content", values.content);

    let result;

    try {
      setIsSubmitting(true);
      result = await createReviewform(formData);
    } catch (error) {
      console.log(error);
      return;
    } finally {
      setIsSubmitting(false);
    }
    const { content } = result;
    setValues(INITIAL_VALUES);
    onSubmitSuccess(content);
  };

  const handleChange = (name, value) => {
    setValues((preValues) => ({
      ...preValues,
      [name]: value,
    }));
  };

  const handleInputChange = (e) => {
    const { name, value, type } = e.target;
    handleChange(name, sanitize(type, value));
  };

  return (
    <form className="ReviewForm" onSubmit={handleReviewSubmit}>
      <div className="ReviewForm-rating">별점을 등록해주세요!</div>

      <textarea
        className="ReviewForm-content"
        name="content"
        value={values.content}
        placeholder="100자 이내 리뷰를 작성해주세요!"
        onChange={handleInputChange}
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
