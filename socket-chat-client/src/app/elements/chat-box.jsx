import React from "react";
import "../../styles/chat-box.css";
import { WebSocketHandler } from "../handlers/websocket-handler.js";
import { Input } from "./input.jsx";

export class ChatBox extends React.Component {
  constructor() {
    super();
    this.state = {
      message: "",
    };
    this.sendMessage = this.sendMessage.bind(this);
    this.handleInputMessage = this.handleInputMessage.bind(this);
    this.handleOnKeyPressInput = this.handleOnKeyPressInput.bind(this);
  }

  componentDidMount() {
    console.log("component did mount....");
    this.webSocket = new WebSocketHandler();
    this.webSocket.addEventListener("message-received", event => {
      window.document.querySelector("#msgArea").value += event.detail.message;
    });
  }

  componentWillUnmount() {
    this.webSocket = null;
  }

  sendMessage() {
    const procMsg = this.state.message.trim();
    if (procMsg.length === 0) {
      return;
    }
    this.webSocket.sendMessage(`${this.props.username}: ${procMsg}`);
    window.document.querySelector("#msgToSend").value = "";
  }

  handleOnKeyPressInput(key) {
    if (key === "Enter") {
      this.sendMessage();
    }
  }

  handleInputMessage(msg) {
    this.setState({ message: msg });
  }

  render() {
    return (
      <div id="chatbox" className="container">
        <div className="input-group msg-show">
          <textarea id="msgArea" disabled className="form-control no-resize"></textarea>
        </div>
        <div className="input-group mb-3">
          <Input
            id="msgToSend"
            placeholder="Message"
            onInputChange={this.handleInputMessage}
            onKeyPressInput={this.handleOnKeyPressInput}
          />
          <div className="input-group-append">
            <button
              id="btnSendMsg"
              className="btn btn-outline-secondary"
              onClick={this.sendMessage}
            >
              Send
            </button>
          </div>
        </div>
      </div>
    );
  }
}
