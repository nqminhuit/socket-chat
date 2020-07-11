import "bootstrap/dist/css/bootstrap.min.css";
import React from "react";
import { render } from "react-dom";
import { ChatBox } from "./chat-box.jsx";
import { LoginForm } from "./login-form.jsx";

render(<LoginForm />, window.document.querySelector("#rx-login-form"));
render(<ChatBox />, window.document.querySelector("#rx-chat-box"));
