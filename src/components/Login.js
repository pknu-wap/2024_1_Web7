import { useState } from "react";
import "./Login.css";
import naver_logo from "../img/naver_logo.png";
import google_logo from "../img/google_logo.png";

function Login() {
  const [values, setValues] = useState({
    email: "",
    password: "",
  });

  const onNaverLogin = () => {
    window.location.href = "http://localhost:8080/oauth2/authorization/naver";
  };

  const onGoogleLogin = () => {
    window.location.href = "http://localhost:8080/oauth2/authorization/google";
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setValues((preValues) => ({
      ...preValues,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(values);

    fetch("http://localhost:8080/api/basic/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        email: values.email,
        password: values.password,
      }),
    })
      .then((res) => res.json())
      .then((res) => {
        localStorage.setItem("Authorization", res.access_token);
      });
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

        <hr />

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
