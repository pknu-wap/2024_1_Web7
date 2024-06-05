import { useState } from "react";
import "./Login.css";
import naver_logo from "../img/naver_logo.png";
import google_logo from "../img/google_logo.png";
import { useNavigate } from "react-router-dom";

function Login() {
  const navigate = useNavigate();
  const [values, setValues] = useState({
    email: "",
    password: "",
  });

  const onNaverLogin = async (e) => {
    e.preventDefault();
    try {
      window.location.href = `${process.env.REACT_APP_API_URL}oauth2/authorization/naver`;
    } finally {
      navigate("/");
    }

    try {
      const socialToken = await fetch(
        `${process.env.REACT_APP_API_URL}api/basic/login`
      );
      localStorage.setItem("Authorization", socialToken);
    } catch (error) {
      console.error("네이버 로그인 실패");
    }
  };

  const onGoogleLogin = () => {
    window.location.href = `${process.env.REACT_APP_API_URL}oauth2/authorization/google`;
  };

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
      // if (!response.ok) {
      //   alert("로그인에 실패했습니다.");
      // }

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
    <form className="login-form" onSubmit={handleSubmit}>
      <h2>로그인</h2>
      <div>
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
    </form>
  );
}

export default Login;
