import React from "react";
import { render } from "react-dom";
import "../../styles/login-form.css";

export class LoginForm extends React.Component {
  render() {
    return (
      <div className="container bordered">
        <div className="login-form">
          <Input placeholder="Username" />
          <Input type="password" placeholder="Password" />
          <hr />
          <ServerConnection />
          <button className="btn btn-primary" id="btnLogin">
            Login
          </button>
        </div>
      </div>
    );
  }
}

render(<LoginForm />, window.document.querySelector("#rx-login-form"));

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

function Input(props) {
  return (
    <input
      id={props.id}
      className="form-control margin-bot-10px"
      type={props.type ? props.type : "text"}
      placeholder={props.placeholder}
    ></input>
  );
}
