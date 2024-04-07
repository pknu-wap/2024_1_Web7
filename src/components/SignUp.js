import React, { useState } from "react";

function SignUp() {
  const [values, setValues] = useState({
    username: "",
    email: "",
    password: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setValues((preValues) => ({
      ...preValues,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    fetch("http://localhost:8080/api/basic/signup", {
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
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>회원가입</h2>
      <ul>
        <li>
          <label htmlFor="email">아이디</label>
          <input
            type="email"
            name="email"
            value={values.email}
            onChange={handleChange}
          />
        </li>
        <li>
          <label htmlFor="password">패스워드</label>
          <input
            type="password"
            name="password"
            value={values.password}
            onChange={handleChange}
          />
        </li>
        <li>
          <label htmlFor="username">닉네임</label>
          <input
            type="text"
            name="username"
            value={values.username}
            onChange={handleChange}
          />
        </li>
        <li>
          <button type="submit">회원가입</button>
        </li>
      </ul>
    </form>
  );
}

export default SignUp;
