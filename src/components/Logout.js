import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

function Logout() {
  const navigate = useNavigate();

  useEffect(() => {
    // 로그아웃 처리
    localStorage.removeItem("Authorization");
    alert("로그아웃 되었습니다.");
    // 홈페이지로 리다이렉트
    navigate("/");
  }, [navigate]);

  return (
    <div>
      <p>로그아웃되었습니다.</p>
      {/* 여기에 필요한 로그아웃 UI를 추가할 수도 있습니다. */}
    </div>
  );
}

export default Logout;
