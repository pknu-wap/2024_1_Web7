import { Link, NavLink, useNavigate } from "react-router-dom";
import Container from "./Container";
import "./Nav.css";
import nav_logo from "../img/dogventure_logo_white.png";

function Nav() {
  const token = localStorage.getItem("Authorization");
  const navigate = useNavigate();

  // 로그아웃 함수 정의
  const handleLogout = () => {
    localStorage.removeItem("Authorization");
    navigate("/");
    window.location.reload();
  };

  return (
    <div className="nav-box">
      <Container className="container">
        <Link to="/">
          <img src={nav_logo} alt="Dogventure Logo" />
        </Link>
        <ul className="menu-box">
          {token ? (
            <li>
              <button className="logout-btn" onClick={handleLogout}>
                로그아웃
              </button>
            </li>
          ) : (
            <div className="login-signup-box">
              <li>
                <NavLink className="NavLink-style" to="login">
                  로그인
                </NavLink>
              </li>
              <span>/</span>
              <li>
                <NavLink to="signup">회원가입</NavLink>
              </li>
            </div>
          )}
          {/* <li>
            <NavLink to="chatbot">챗봇</NavLink>
          </li> */}
        </ul>
      </Container>
    </div>
  );
}

export default Nav;
