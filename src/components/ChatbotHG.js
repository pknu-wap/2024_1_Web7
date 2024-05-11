import React, { useState } from "react";
import imglogo from "../img/dogventure_logo.png";
import imgfoot from "../img/foot_purple.png";
import imghwasalpyo from "../img/hwasalpyo.png";
import imgsend from "../img/send.png";
import "./ChatbotHG.css";

const ChatbotHG = () => {
  const [messages, setMessages] = useState([]);
  const [userInput, setUserInput] = useState("");
  const [loading, setLoading] = useState(false);
  const [showChat, setShowChat] = useState(false);

  const apiEndpoint = `${process.env.REACT_APP_API_URL}api/gpt/question`;

  const addMessage = (sender, message) => {
    setMessages((prevMessages) => [...prevMessages, { sender, message }]);
  };

  const handleSendMessage = async () => {
    const message = userInput.trim();
    if (message.length === 0) return;

    addMessage("user", message);
    setUserInput("");
    setLoading(true);

    try {
      const response = await fetch(apiEndpoint, {
        method: "POST",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("Authorization")}`,
        },
        body: JSON.stringify({ prompt: message }),
      });

      const data = await response.json();
      const aiResponse = data.choices?.[0]?.message?.content || "No response";
      addMessage("bot", aiResponse);
    } catch (error) {
      console.error("오류 발생", error);
      addMessage("system", "오류 발생");
    } finally {
      setLoading(false);
    }
  };

  const handleKeyDown = (event) => {
    if (event.key === "Enter" && !event.nativeEvent.isComposing) {
      handleSendMessage();
    }
  };

  return (
    <div>
      <div id="chatbot-icon" onClick={() => setShowChat(!showChat)}>
        <img src={imgfoot} width="58" height="58" />
      </div>
      {showChat && (
        <div id="chatbot-window">
          <h1>
            {" "}
            <img src={imghwasalpyo} width="10" height="10" />
          </h1>
          <h2>
            <img src={imglogo} width="292" height="111.05" />
          </h2>
          <div className="chatDiv">
            {loading && <span className="messageWait"></span>}
            {messages.map((msg, index) => (
              <div key={index} className={`message ${msg.sender}`}>
                {`${msg.sender === "user" ? "" : ""}  ${msg.message}`}
              </div>
            ))}
          </div>
          <div className="inputDiv">
            <input
              type="text"
              placeholder=""
              value={userInput}
              onChange={(e) => setUserInput(e.target.value)}
              onKeyDown={handleKeyDown}
            />
            <button onClick={handleSendMessage}>
              <img src={imgsend} width="20" height="20" />
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default ChatbotHG;
