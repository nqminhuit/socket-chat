import { WebSocketHandler } from "../scripts/websocket-handler.js";
import { ChatBox } from "./chat-box.js";

window.customElements.define(
  "login-form",
  class extends HTMLElement {
    constructor() {
      super();

      const divServerAddressInput = document.createElement("div");
      divServerAddressInput.classList.add("col-8");
      divServerAddressInput.appendChild(
        this.createInputText("text", "Server Ip Address", "serverAddress")
      );

      const divPortInput = document.createElement("div");
      divPortInput.classList.add("col-4");
      divPortInput.appendChild(this.createInputText("text", "Server Port", "serverPort"));

      const divServerInfo = document.createElement("div");
      divServerInfo.classList.add("form-row");
      divServerInfo.appendChild(divServerAddressInput);
      divServerInfo.appendChild(divPortInput);

      const btnLogin = document.createElement("button");
      btnLogin.classList.add("btn");
      btnLogin.classList.add("btn-primary");
      btnLogin.setAttribute("id", "btnLogin");
      btnLogin.innerHTML = "Login";
      btnLogin.addEventListener("click", () => {
        const loginForm = document.querySelector("login-form");
        const chatBox = new ChatBox(new WebSocketHandler("127.0.0.1", "8080"));
        loginForm.parentElement.appendChild(chatBox);
        loginForm.remove();
      });

      const divLoginInput = document.createElement("div");
      divLoginInput.classList.add("login-form");
      divLoginInput.appendChild(this.createInputText("input", "Username"));
      divLoginInput.appendChild(this.createInputText("password", "Password"));
      divLoginInput.appendChild(document.createElement("hr"));
      divLoginInput.appendChild(divServerInfo);
      divLoginInput.appendChild(btnLogin);

      const divLoginFormContainer = document.createElement("div");
      divLoginFormContainer.classList.add("container");
      divLoginFormContainer.classList.add("bordered");

      const style = document.createElement("style");
      style.textContent = "@import url('./styles/login-form.css')";

      divLoginFormContainer.appendChild(divLoginInput);
      const shadow = this.attachShadow({ mode: "open" });
      shadow.appendChild(divLoginFormContainer);
      shadow.appendChild(style);
    }

    createInputText(type, placeholder, id) {
      const txtInput = document.createElement("input");
      txtInput.classList.add("form-control");
      txtInput.classList.add("margin-bot-10px");
      txtInput.setAttribute("type", type);
      txtInput.setAttribute("placeholder", placeholder);
      if (id) {
        txtInput.setAttribute("id", id);
      }
      return txtInput;
    }
  }
);
