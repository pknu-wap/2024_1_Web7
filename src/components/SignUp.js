import React, { useEffect, useState } from "react";
import "./SignUp.css";
import dogventure_logo from "../img/dogventure_logo.png";
import { useNavigate } from "react-router-dom";

const validate = (input) => {
  const { username, email, password } = input;
  const errors = {};

  if (email === "") {
    errors.email = "아이디가 입력되지 않았습니다.";
  } else if (!/^[a-z0-9%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/i.test(email)) {
    errors.email = "입력된 아이디가 유효하지 않습니다.";
  }

  if (!password) {
    errors.password = "비밀번호가 입력되지 않았습니다.";
  } else if (password.length < 8) {
    errors.password = "8자 이상의 비밀번호를 사용해야 합니다.";
  }

  if (!username) {
    errors.username = "닉네임이 입력되지 않았습니다.";
  }

  return errors;
};

function SignUp() {
  const navigate = useNavigate();

  const [values, setValues] = useState({
    username: "",
    email: "",
    password: "",
  });
  const [submit, setSubmit] = useState(false);
  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    const { name, value } = e.target;
    setValues((preValues) => ({
      ...preValues,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setSubmit(true);

    fetch(`${process.env.REACT_APP_API_URL}api/basic/signup`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        username: values.username,
        email: values.email,
        password: values.password,
      }),
    })
      .then((res) => console.log(res))
      .catch((error) => console.log(error));

    const input = {
      email: values.email,
      password: values.password,
      username: values.username,
    };

    setErrors(validate(input));
  };

  useEffect(() => {
    if (submit) {
      if (Object.keys(errors).length === 0) {
        alert("회원가입이 완료되었습니다.");
        navigate("/login");
      } else {
        alert("회원가입에 실패하였습니다.");
      }
      setSubmit(false);
    }
  }, [errors]);

  return (
    <>
      <div className="logo-box">
        <img className="logo" src={dogventure_logo} />
      </div>
      <form className="sign-form" onSubmit={handleSubmit}>
        <h2>회원가입</h2>
        <div className="input-box">
          <span className="input-box">
            <input
              placeholder="아이디 (이메일)"
              type="email"
              name="email"
              value={values.email}
              onChange={handleChange}
            />
            {errors.email && <span className="errorMsg">{errors.email}</span>}
          </span>

          <span className="input-box">
            <input
              placeholder="비밀번호"
              type="password"
              name="password"
              value={values.password}
              onChange={handleChange}
            />
            {errors.password && (
              <span className="errorMsg">{errors.password}</span>
            )}
          </span>

          <span className="input-box">
            <input
              placeholder="닉네임"
              type="text"
              name="username"
              value={values.username}
              onChange={handleChange}
            />
            {errors.username && (
              <span className="errorMsg">{errors.username}</span>
            )}
          </span>
        </div>

        <div className="signup-btn-box">
          <button type="submit">회원가입</button>
        </div>
      </form>
    </>
  );
}

export default SignUp;
