import searchImg from "../img/searchBtn.png";
import "./MapSearch.css";

function MapSearch({ handleSearchSubmit }) {
  return (
    <div className="MapSearch-container">
      <div className="MapSearch-box">
        <div className="MapSearch-text">
          당신의 반려견과 함께하는 오늘, <br />
          어디로 떠나볼까요?
        </div>
        <form className="MapSearch-form" onSubmit={handleSearchSubmit}>
          <input
            className="MapSearch-input"
            name="search"
            placeholder="상호명을 입력해보세요!"
            autoComplete="off"
          />
          <button className="MapSearch-btn" type="submit">
            <img src={searchImg} alt="검색 이미지" />
          </button>
        </form>
      </div>
    </div>
  );
}

export default MapSearch;
