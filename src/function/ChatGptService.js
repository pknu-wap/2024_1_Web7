const OPENAI_API_KEY = "YOUR_OPENAI_API_KEY";

const ChatGptService = async (message) => {
  try {
    const response = await fetch("http://localhost:8080/api/gpt/question", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${OPENAI_API_KEY}`,
      },
      body: JSON.stringify({
        prompt: message,
        max_tokens: 150,
      }),
      credentials: "include", // 클라이언트와 서버가 통신할때 쿠키 값을 공유하겠다는 설정
    });

    const data = await response.json();
    return data.choices[0].text.trim();
  } catch (error) {
    console.error("OpenAI API Error:", error);
    return "Sorry, I am unable to respond at the moment.";
  }
};

export default ChatGptService;
