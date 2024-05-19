import { useEffect } from "react";

// function ParentReview() {
//   const [reviews, setReviews] = useState([]);

//   useEffect(() => {
//     const fetchReviews = async (placeId) => {
//       try {
//         const response = await getPlaceInfo({id: placeId});
//         setReviews(response.reviews);
//       } catch (error) {
//         console.error('리뷰 업데이트에 실패했습니다.');
//       }
//     }
//   })
// }

function ReviewList({ reviews, footImg }) {
  return (
    <ul className="review-ul">
      {reviews.map((review) => (
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
      ))}
    </ul>
  );
}

export default ReviewList;
