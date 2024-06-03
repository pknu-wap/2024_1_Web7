import React, { useState, useEffect, useRef } from "react";
import imglogo from "../img/dogventure_logo.png";
import footblue from "../img/foot_blue.png";
import imgsend from "../img/send.png";
import backbutton from "../img/backbutton.png";
import "./ChatbotHG.css";

const TypingEffect = ({ text, speed }) => {
  const [displayedText, setDisplayedText] = useState('');
  useEffect(() => {
    let currentIndex = 0;

    const typeText = async () => {
      while (currentIndex < text.length) {
        await new Promise((resolve) => setTimeout(resolve, speed));
        setDisplayedText((prev) => {
          if (currentIndex < text.length) {
            const updatedText = prev + text[currentIndex];
            currentIndex++; 
            return updatedText;
          }
          return prev;
        });
      }
    };

    setDisplayedText('');
    typeText();

    return () => {
      setDisplayedText('');
    };
  }, [text, speed]);

  return <div>{displayedText}</div>;
};


const ChatbotHG = () => {
  /*const [responseText, setResponseText] = useState('');*/
  const [messages, setMessages] = useState([]);
  const [userInput, setUserInput] = useState("");
  const [loading, setLoading] = useState(false);
  const [showChat, setShowChat] = useState(false);

  const apiEndpoint = `${process.env.REACT_APP_API_URL}api/gpt/question`;

  const addMessage = (sender, message) => {
    const currentTime = new Date().toLocaleTimeString(); //현재 시간 출력
    setMessages((prevMessages) => [...prevMessages, { sender, message }]);
  };

  const handleSendMessage = async () => {
    const message = userInput.trim();
    if (message.length === 0) return;

    addMessage("user", message);
    setUserInput(""); // 입력 필드를 비움
    setLoading(true); // 로딩 상태 true 설정
    const response = await fetch(apiEndpoint, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${localStorage.getItem("Authorization")}`,
      },
      body: JSON.stringify({ prompt: message }),
    });
    let result = "";

    try {
      const bodyData = await response.body;
      const reader = await bodyData.getReader();
      const decoder = new TextDecoder("utf-8");
      // let result = "";

      while (true) {
        const { done, value } = await reader.read();
        if (done) break;

        const answer = decoder.decode(value).toString();
        result += answer.replace(/data: |data:/g, "");
       
      }
      
    } catch (e) {
      let cleaedresult = result.replace(/\s+/g, '');
      // console.log(result)
      addMessage("bot", cleaedresult);

      setLoading(false);
    }
  };

  const handleKeyDown = (event) => {
    if (event.key === "Enter" && !event.nativeEvent.isComposing) {
      handleSendMessage();
    }
  };

  const handleCloseChat = () => {
    setShowChat(false);
  };

  return (
    <div>
      <div id="chatbot-icon" onClick={() => setShowChat(!showChat)}>
        <img src={footblue} alt="Chatbot Icon" className="icon" />
      </div>
      {showChat && (
        <div id="chatbot-window">
          <div className="header">
            <img
              src={backbutton}
              alt="Back"
              className="back-icon"
              onClick={handleCloseChat}
            />
            <div className="chatbot-logo-text">
              <img
                src={footblue}
                alt="SmallLogo"
                className="chatbot-small-logo"
              />
              <div className="chatbot-small-text">
                <p className="title">Dogventures</p>
                <p className="subtitle">30분 내 답변 받으실 수 있어요.</p>
              </div>
            </div>
            <img src={imglogo} alt="Logo" className="logo" />
            <div className="header-text">
              <p>도그벤처 챗봇에 문의하기 </p>
            </div>
          </div>
          <div className="chatDiv">
            {loading && <span className="messageWait"></span>}
            {messages.map((msg, index) => (
              <div key={index} className={`message ${msg.sender}`}>
                {msg.sender === "bot" && (
                  <img
                    src={footblue}
                    alt="Bot Profile"
                    className="bot-profile"
                  />
                )}
                <div>
                {msg.sender === "bot" && msg.message ?
                (<div><TypingEffect text={msg.message} speed={100} /></div>)
                :
                 (<div> {msg.message} </div>)
                  }
                  <div className="message-time">{msg.time}</div>{" "}
                  {/* 시간 표시 */}
                </div>
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
              <img src={imgsend} alt="Send" className="send-icon" />
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default ChatbotHG;