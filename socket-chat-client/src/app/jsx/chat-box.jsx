import React from "react";
import { render } from "react-dom";
import "../../styles/chat-box.css";

export class ChatBox extends React.Component {
  render() {
    return (
      <div id="chatbox" className="container">
        <div className="input-group msg-show">
          <textarea id="msgArea" disabled className="form-control no-resize"></textarea>
        </div>
        <div className="input-group mb-3">
          <input id="msgToSend" type="text" placeholder="message" className="form-control" />
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

render(<ChatBox />, window.document.querySelector("#rx-chat-box"));
