import { useEffect, useState } from "react";
import "./Login.css";
import naver_logo from "../img/naver_logo.png";
import google_logo from "../img/google_logo.png";
import { useNavigate } from "react-router-dom";

const onNaverLogin = () => {
  window.location.href = `${process.env.REACT_APP_API_URL}oauth2/authorization/naver`;
};

const onGoogleLogin = () => {
  window.location.href = `${process.env.REACT_APP_API_URL}oauth2/authorization/google`;
};

// const saveTokenToLocalStorage = () => {
//   const cookieString = document.cookie;
//   const cookies = cookieString.split("; ");

//   for (let cookie of cookies) {
//     const [name, value] = cookie.split("=");
//     if (name === "Authorization") {
//       localStorage.setItem("Authorization", value);
//     }
//   }
// };

function Login() {
  const navigate = useNavigate();
  const [values, setValues] = useState({
    email: "",
    password: "",
  });

  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    const token = params.get("token");
    if (token) {
      localStorage.setItem("Authorization", token);
      navigate("/");
    }
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setValues((preValues) => ({
      ...preValues,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(values);

    try {
      const response = await fetch(
        `${process.env.REACT_APP_API_URL}api/basic/login`,
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            email: values.email,
            password: values.password,
          }),
        }
      );

      if (!response.ok) {
        alert("로그인에 실패했습니다.");
      }

      const data = await response.json();

      if (data.token) {
        localStorage.setItem("Authorization", data.token);
        navigate("/");
      } else {
        alert("유효한 토큰을 받지 못했습니다.");
      }
    } catch (error) {}
  };

  return (
    <div className="login-form">
      <form onSubmit={handleSubmit}>
        <h2>로그인</h2>
        <div className="login-box">
          <div className="input-box">
            <input
              placeholder="아이디 (이메일)"
              type="email"
              name="email"
              value={values.email}
              onChange={handleChange}
            />

            <input
              placeholder="비밀번호"
              type="password"
              name="password"
              value={values.password}
              onChange={handleChange}
            />
          </div>
          <li className="login-btn-box">
            <button type="submit" className="login-btn">
              로그인
            </button>
          </li>

          <hr className="login-line" />
        </div>
      </form>

      <div className="social-login-box">
        <button className="social-btn google-btn" onClick={onGoogleLogin}>
          <img src={google_logo} />
          <span>구글 로그인</span>
        </button>
        <button className="social-btn" onClick={onNaverLogin}>
          <img src={naver_logo} />
          <span>네이버 로그인</span>
        </button>
      </div>
    </div>
  );
}

export default Login;
