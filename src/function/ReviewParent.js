// import { useEffect } from "react";
// import { createReview, getPlaceInfo } from "../api";

// function ReviewParent() {
//   const [reviews, setReviews] = useState([]);
//   const [selectedId, setSelectedId] = useState(null);

//   useEffect(() => {
//     if (selectedId !== null) {
//       const fetchReviews = async (selectedId) => {
//         const response = await getPlaceInfo({ id: selectedId });
//         const fetchedReviews = await response.json();
//         setReviews(fetchedReviews);
//       };
//       fetchReviews();
//     }

//   }, [selectedId]);

//   const addReview = async (newReview, token) => {
//     try {
//       const response = await createReview(newReview, token);
//       if (response.ok) {
//         const addedReview = await response.json();
//         setReviews((prevReviews) => [...prevReviews, addedReview]);
//       } else {
//         console.error("리뷰 추가 실패");
//       }
//     } catch (error) {
//       console.error("리뷰 추가 중 에러 발생", error);
//     }
//   };

//   return (
//     <div>
//       <ReviewForm onReviewSubmit={addReview} />
//       <Map reviews={reviews} id={selectedId}  />
//     </div>
//   );
// }

// export default ReviewParent;
