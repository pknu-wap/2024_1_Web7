class MessageParser {
  constructor(handleNewUserMessage) {
    this.handleNewUserMessage = handleNewUserMessage;
  }

  parse = (message) => {
    this.handleNewUserMessage(message);
  };
}

export default MessageParser;
