import React from "react";
import "../../styles/login-form.css";
import { Input } from "./input.jsx";

export class LoginForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
    };

    this.login = this.login.bind(this);
    this.onUsernameChange = this.onUsernameChange.bind(this);
    this.onPasswordChange = this.onPasswordChange.bind(this);
  }

  onUsernameChange(inputUsername) {
    this.setState({ username: inputUsername });
  }

  onPasswordChange(inputPassword) {
    this.setState({ password: inputPassword });
  }

  login() {
    if (this.state.username.length === 0 || this.state.password.length === 0) {
      alert("Please provide username and password!");
      return;
    }
  }

  render() {
    return (
      <div className="container bordered">
        <div className="login-form">
          <Input placeholder="Username" onInputChange={this.onUsernameChange} />
          <Input type="password" placeholder="Password" onInputChange={this.onPasswordChange} />
          <hr />
          <ServerConnection />
          <button className="btn btn-primary" id="btnLogin" onClick={this.login}>
            Login
          </button>
        </div>
      </div>
    );
  }
}

function ServerConnection() {
  return (
    <div className="form-row">
      <div className="col-8">
        <Input id="serverAddress" placeholder="Server Ip Address" />
      </div>
      <div className="col-4">
        <Input id="serverPort" placeholder="Server Port" />
      </div>
    </div>
  );
}
