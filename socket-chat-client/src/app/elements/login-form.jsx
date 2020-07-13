import React from "react";
import "../../styles/login-form.css";
import { Input } from "./input.jsx";

export class LoginForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
      serverIp: "",
      serverPort: "",
    };

    this.login = this.login.bind(this);
    this.onUsernameChange = this.onUsernameChange.bind(this);
    this.onPasswordChange = this.onPasswordChange.bind(this);
    this.onServerIpChange = this.onServerIpChange.bind(this);
    this.onServerPortChange = this.onServerPortChange.bind(this);
  }

  onUsernameChange(inputUsername) {
    this.setState({ username: inputUsername });
  }

  onPasswordChange(inputPassword) {
    this.setState({ password: inputPassword });
  }

  onServerIpChange(ip) {
    this.setState({ serverIp: ip });
  }

  onServerPortChange(port) {
    this.setState({ serverPort: port });
  }

  login() {
    if (this.state.username.length === 0 || this.state.password.length === 0) {
      alert("Please provide username and password!");
      this.props.handleLogin(false);
      return;
    }
    this.props.handleLogin(true, this.state.username);
  }

  render() {
    return (
      <div className="container bordered">
        <div className="login-form">
          <Input placeholder="Username" onInputChange={this.onUsernameChange} />
          <Input type="password" placeholder="Password" onInputChange={this.onPasswordChange} />
          <hr />
          <ServerConnection
            handleServerAddressInput={this.onServerIpChange}
            handleServerPortInput={this.onServerPortChange}
          />
          <button className="btn btn-primary" id="btnLogin" onClick={this.login}>
            Login
          </button>
        </div>
      </div>
    );
  }
}

function ServerConnection(props) {
  function onServerAddressChange(value) {
    props.handleServerAddressInput(value);
  }

  function onServerPortChange(value) {
    props.handleServerPortInput(value);
  }

  return (
    <div className="form-row">
      <div className="col-8">
        <Input
          id="serverAddress"
          placeholder="Server Ip Address (default: '127.0.0.1')"
          onInputChange={onServerAddressChange}
        />
      </div>
      <div className="col-4">
        <Input
          id="serverPort"
          placeholder="Server Port (default: '8080')"
          onInputChange={onServerPortChange}
        />
      </div>
    </div>
  );
}
