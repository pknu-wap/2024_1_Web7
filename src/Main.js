import { BrowserRouter, Routes, Route } from "react-router-dom";
import App from "./components/App";
import Login from "./components/Login";
import SignUp from "./components/SignUp";
import ChatbotApp from "./components/ChatbotApp";
import MainPage from "./pages/MainPage";

function Main() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<App />}>
          <Route index element={<MainPage />} />
          <Route path="login" element={<Login />} />
          <Route path="signup" element={<SignUp />} />
          <Route path="chatbot" element={<ChatbotApp />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default Main;
