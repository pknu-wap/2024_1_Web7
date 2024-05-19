import ReviewForm from "./ReviewForm";

function ReviewList({ reviews, footImg }) {
  cosnt[(editingId, setEditingId)] = useState(null);

  return (
    <ul className="review-ul">
      {reviews.map((review) => {
        if (review.user.id === editingId) {
          const { id, content } = review;
          const initialValues = { content };
          return (
            <li key={review.id}>
              <ReviewForm />
            </li>
          );
        }

        return (
          <li className="review-list" key={review.id}>
            <img className="review-profile-img" src={footImg} alt="Profile" />
            <div className="review-content-box">
              <div>
                <span className="review-username">{review.user.username}</span>
                <span className="review-rate">{review.rate}</span>
              </div>
              <div className="review-content">{review.content}</div>
            </div>
          </li>
        );
      })}
    </ul>
  );
}

export default ReviewList;
