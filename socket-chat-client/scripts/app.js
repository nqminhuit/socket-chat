const sendButton = document.querySelector("#btnSendMsg");
const msgArea = document.querySelector("#msgArea");
const msgToSend = document.querySelector("#msgToSend");
let webSocket = new WebSocket("ws://127.0.0.1:8080");

function sendMessage() {
  const message = msgToSend.value.trim();
  if (message.length === 0) {
    return;
  }
  if (webSocket.readyState !== WebSocket.OPEN) {
    console.error(`WebSocket is not open, readyState is ${webSocket.readyState}`);
  }
  webSocket.send(message);
  msgToSend.value = "";
}

sendButton.addEventListener("click", sendMessage);
msgToSend.addEventListener("keypress", event => {
  if (event.code === "Enter") {
    sendMessage();
  }
});

webSocket.onopen = () => {
  console.log("opened!");
};

webSocket.onclose = () => {
  console.log("closed!");
};

webSocket.onmessage = event => {
  msgArea.value += "your message: " + event.data + String.fromCharCode(13, 10);
};

webSocket.onerror = error => {
  console.log(`error: ${error.message}`);
};
