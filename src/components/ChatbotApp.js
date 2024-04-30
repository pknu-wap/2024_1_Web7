import { useState } from "react";
import Chatbot from "react-chatbot-kit";
import ActionProvider from "../function/ActionProvider";
import MessageParser from "../function/MessageParser";
import ChatGptService from "../function/ChatGptService";
import "./ChatbotApp.css";

function ChatbotApp() {
  const [messages, setMessages] = useState([
    {
      author: "bot",
      message: "Hi! How can I assist you today?",
    },
  ]);

  const handleNewUserMessage = async (newMessage) => {
    const response = await ChatGptService(newMessage);
    const newMessages = [...messages, { author: "bot", message: response }];
    setMessages(newMessages);
  };

  return (
    <div className="chatbot-container">
      <Chatbot
        config={{
          botName: "ChatGPT",
          initialMessages: messages,
        }}
        actionProvider={new ActionProvider(setMessages)}
        messageParser={new MessageParser(handleNewUserMessage)}
      />
    </div>
  );
}

export default ChatbotApp;
