import React from "react";
import { render } from "react-dom";

class App extends React.Component {
  render() {
    return (
      <div id="chatbox" className="container">
        <div className="input-group msg-show">
          <textarea id="msgArea" disabled className="form-control no-resize"></textarea>
        </div>
        <div className="input-group mb-3">
          <input id="msgToSend" type="text" placeholder="message" className="form-control" />
        </div>
      </div>
    );
  }
}

render(<App />, window.document.getElementById("rx-app"));
