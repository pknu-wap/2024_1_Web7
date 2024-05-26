import searchImg from "../img/searchBtn.png";
import "./Search.css";

function Search({ handleSearchSubmit }) {
  return (
    <div className="search-box">
      <form className="search-form" onSubmit={handleSearchSubmit}>
        <input
          className="search-input"
          name="search"
          placeholder="상호명을 입력해보세요!"
          autoComplete="off"
        />
        <button className="search-btn" type="submit">
          <img src={searchImg} alt="검색 이미지" />
        </button>
      </form>
    </div>
  );
}

export default Search;
