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
      username: "",
    };

    this.handleLogin = this.handleLogin.bind(this);
  }

  handleLogin(success, usernameInfo) {
    if (success) {
      this.setState({ loginSuccess: success, username: usernameInfo });
    }
  }

  render() {
    return !this.state.loginSuccess ? (
      <LoginForm handleLogin={this.handleLogin} />
    ) : (
      <ChatBox username={this.state.username} />
    );
  }
}

render(<App />, window.document.querySelector("#rx-app"));
