import { BrowserRouter, Routes, Route } from "react-router-dom";
import App from "./components/App";
import Login from "./components/Login";
import SignUp from "./components/SignUp";
import ChatbotHG from "./components/ChatbotHG";
import MainPage from "./pages/MainPage";
import Map from "./components/Map";

function Main() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<App />}>
          <Route index element={<MainPage />} />
          <Route path="map" element={<Map />} />
          <Route path="login" element={<Login />} />
          <Route path="signup" element={<SignUp />} />
          <Route path="chatbot" element={<ChatbotHG />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default Main;
