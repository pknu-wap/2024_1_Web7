class ActionProvider {
  constructor(setMessages) {
    this.setMessages = setMessages;
  }

  handleDefault = () => {
    const message = {
      author: "bot",
      message: "Sorry, I did not understand that. Can you please rephrase?",
    };
    this.setMessages((prevMessages) => [...prevMessages, message]);
  };
}

export default ActionProvider;
