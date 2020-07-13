import React from "react";
import { Input } from "./input.jsx";
import "../../styles/chat-box.css";
import { WebSocketHandler } from "../handlers/websocket-handler.js";

export class ChatBox extends React.Component {
  constructor() {
    super();
    this.state = {
      message: "",
    };
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

  sendMessage(msg) {
    const procMsg = msg.trim();
    if (procMsg.length === 0) {
      return;
    }
    this.webSocket.sendMessage(`${this.props.username}: ${procMsg}`);
  }

  handleOnKeyPressInput(key) {
    if (key === "Enter") {
      this.sendMessage(this.state.message);
      window.document.querySelector("#msgToSend").value = "";
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
            <button id="btnSendMsg" className="btn btn-outline-secondary">
              Send
            </button>
          </div>
        </div>
      </div>
    );
  }
}
