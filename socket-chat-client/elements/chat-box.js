export class ChatBox extends HTMLElement {
  constructor(webSocketHandler) {
    super();

    const textArea = document.createElement("textarea");
    textArea.setAttribute("id", "msgArea");
    textArea.setAttribute("aria-label", "With textarea");
    textArea.disabled = true;
    textArea.classList.add("form-control");
    textArea.classList.add("no-resize");
    const divTextArea = document.createElement("div");
    divTextArea.classList.add("input-group");
    divTextArea.classList.add("msg-show");
    divTextArea.appendChild(textArea);

    this.msgInput = document.createElement("input");
    this.msgInput.setAttribute("id", "msgToSend");
    this.msgInput.setAttribute("type", "text");
    this.msgInput.setAttribute("placeholder", "message");
    this.msgInput.setAttribute("aria-label", "message");
    this.msgInput.setAttribute("aria-describedby", "button-addon2");
    this.msgInput.classList.add("form-control");
    this.msgInput.addEventListener("keypress", event => {
      if (event.code === "Enter") {
        this.sendMessage();
      }
    });

    const btnSend = document.createElement("button");
    btnSend.setAttribute("id", "btnSendMsg");
    btnSend.classList.add("btn");
    btnSend.classList.add("btn-outline-secondary");
    btnSend.innerHTML = "Send";
    btnSend.addEventListener("click", this.sendMessage);
    const divButtonAppend = document.createElement("div");
    divButtonAppend.classList.add("input-group-append");
    divButtonAppend.appendChild(btnSend);

    const divMsgInput = document.createElement("div");
    divMsgInput.classList.add("input-group");
    divMsgInput.classList.add("mb-3");
    divMsgInput.appendChild(this.msgInput);
    divMsgInput.append(divButtonAppend);

    const divChatboxContainer = document.createElement("div");
    divChatboxContainer.setAttribute("id", "chatbox");
    divChatboxContainer.classList.add("container");
    divChatboxContainer.appendChild(divTextArea);
    divChatboxContainer.appendChild(divMsgInput);

    const style = document.createElement("style");
    style.textContent = "@import url('./styles/chat-box.css')";

    const shadow = this.attachShadow({ mode: "open" });
    shadow.appendChild(divChatboxContainer);
    shadow.appendChild(style);
    this.webSocket = webSocketHandler;
  }

  sendMessage() {
    const message = this.msgInput.value.trim();
    if (message.length === 0) {
      return;
    }
    this.webSocket.sendMessage(message);
    this.msgInput.value = "";
  }
}

window.customElements.define("chat-box", ChatBox);
