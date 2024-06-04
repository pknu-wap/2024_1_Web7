import React, { useState, useEffect, useRef } from "react";
import imglogo from "../img/dogventure_logo.png";
import footblue from "../img/foot_blue.png";
import imgsend from "../img/send.png";
import backbutton from "../img/backbutton.png";
import "./ChatbotHG.css";

// TypingEffect는 데이터 출력을 보여주는 효과? 속도 지정해서 한 글자씩 보이게 함
const TypingEffect = ({ text, speed }) => {
  const [displayedText, setDisplayedText] = useState("");
  useEffect(() => {
    let currentIndex = 0;

    const typeText = async () => {
      while (currentIndex < text.length) {
        await new Promise((resolve) => setTimeout(resolve, speed)); // 글자가 씹혀서 await하고 promise를 건거임
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

    setDisplayedText("");
    typeText();

    return () => {
      setDisplayedText("");
    };
  }, [text, speed]);

  return <div>{displayedText}</div>;
};

const ChatbotHG = () => {
  const [messages, setMessages] = useState([]);
  const [userInput, setUserInput] = useState("");
  const [loading, setLoading] = useState(false);
  const [showChat, setShowChat] = useState(false);
  const [showWelcomeMessage, setShowWelcomeMessage] = useState(true);
  const chatEndRef = useRef(null);

  const apiEndpoint = `${process.env.REACT_APP_API_URL}api/gpt/question`;

  const addMessage = (sender, message) => {
    const currentTime = new Date().toLocaleTimeString(); //현재 시간 출력
    setMessages((prevMessages) => [...prevMessages, { sender, message }]);
  };

  const handleSendMessage = async (message) => {
    if (message.trim().length === 0) return; // 내가 공백일때는 응답을 안 함

    setShowWelcomeMessage(false);
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

      while (true) {
        const { done, value } = await reader.read();
        if (done) break;

        const answer = decoder.decode(value).toString();
        if (!answer.includes("data:done")) {
          // done이 나올 경우에 멈추길 원햇지만 그냥 done일 때 result에 안 받기
          result += answer.replace(/data: |data:/g, ""); // data: 이거 안 보이게 하기
        }
      }
    } catch (e) {
      let cleaedresult = result.replace(/\s+/g, ""); // 공백이랑 줄넘김을 공백으로 대체(없앤다고 생각)
      addMessage("bot", cleaedresult);

      setLoading(false);
    }
  };

  const handleKeyDown = (event) => {
    if (event.key === "Enter" && !event.nativeEvent.isComposing) {
      handleSendMessage(userInput); // 엔터키 눌렀을 때 input
    }
  };

  const handleCloseChat = () => {
    setShowChat(false);
  };

  const handleRecommendedQuestionClick = (question) => {
    handleSendMessage(question); // 추천질문을 클릭했을 때 사용자가 입력된 값으로 들어가도록
  };

  // 처음 안내문이 계속 나오니까 if 중첩문 넣어서 새로고침 할 때나 다시 시작할 때만 안내문 뜨게하기
  useEffect(() => {
    if (showChat) {
      if (!localStorage.getItem("chatbot-welcome-shown")) {
        addMessage(
          "bot",
          "안녕하세요 ◡̈ 당신의 반려견과 함께하는 오늘을 응원하는 도그벤처 챗봇입니다.\n\n궁금한 사항이나 알아보고 싶은 사항들을 검색해주세요! 챗봇이 빠르게 답해드립니다."
        );
        localStorage.setItem("chatbot-welcome-shown", "true");
      }
    }
  }, [showChat]);

  useEffect(() => {
    chatEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [messages]); // 스크롤 자동으로 내려가게

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
              <div key={index} className={`message-wrapper ${msg.sender}`}>
                {msg.sender === "bot" && (
                  <img
                    src={footblue}
                    alt="Bot Profile"
                    className="bot-profile"
                  />
                )}
                <div className={`message ${msg.sender}`}>
                  {msg.sender === "bot" && msg.message ? (
                    <TypingEffect text={msg.message} speed={100} />
                  ) : (
                    <div>{msg.message}</div>
                  )}
                  <div className="message-time">{msg.time}</div>
                </div>
              </div>
            ))}
            {showWelcomeMessage && (
              <div className="recommended-questions">
                <button
                  onClick={() =>
                    handleRecommendedQuestionClick("오늘 날씨 어때?")
                  }
                >
                  오늘 날씨 어때?
                </button>
                <button
                  onClick={() =>
                    handleRecommendedQuestionClick("부산 여행 추천")
                  }
                >
                  부산 여행 추천
                </button>
                <button
                  onClick={() =>
                    handleRecommendedQuestionClick(
                      "오늘 반려견과 떠나기 좋은 장소를 추천해주세요!"
                    )
                  }
                >
                  오늘 반려견과 떠나기 좋은 장소를 추천해주세요!
                </button>
              </div>
            )}
            <div ref={chatEndRef} />
          </div>

          <div className="inputDiv">
            <input
              type="text"
              placeholder=""
              value={userInput}
              onChange={(e) => setUserInput(e.target.value)}
              onKeyDown={handleKeyDown}
            />
            <button
              onClick={() => {
                handleSendMessage(userInput);
              }}
            >
              <img src={imgsend} alt="Send" className="send-icon" />
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default ChatbotHG;
