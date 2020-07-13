import React from "react";
import { Input } from "./input.jsx";
import "../../styles/chat-box.css";

export class ChatBox extends React.Component {
  constructor() {
    super();
    this.state = {
      message: "",
    };
    this.handleInputMessage = this.handleInputMessage.bind(this);
    this.handleOnKeyPressInput = this.handleOnKeyPressInput.bind(this);
  }

  sendMessage(msg) {
    console.log(msg);
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
