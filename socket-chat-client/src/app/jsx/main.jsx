import "bootstrap/dist/css/bootstrap.min.css";
import React from "react";
import { render } from "react-dom";
import { ChatBox } from "./chat-box.jsx";
import { LoginForm } from "./login-form.jsx";

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      loginSuccess: false,
    };

    this.handleLogin = this.handleLogin.bind(this);
  }

  handleLogin(success) {
    if (success) {
      alert("login success!");
    } else {
      alert("FAILED!!!!!!!!!!!!!!!!!!");
    }
    this.setState({ loginSuccess: success });
  }

  render() {
    return !this.state.loginSuccess ? <LoginForm handleLogin={this.handleLogin} /> : <ChatBox />;
  }
}

render(<App />, window.document.querySelector("#rx-app"));
