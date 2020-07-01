const sendButton = document.querySelector("#btnSendMsg");
const msgArea = document.querySelector("#msgArea");
const msgToSend = document.querySelector("#msgToSend");
const webSocket = new WebSocket("ws://127.0.0.1:8080");

function sendMessage() {
  const message = msgToSend.value.trim();
  if (message.length === 0) {
    return;
  }
  webSocket.send(message);
  msgArea.value += message + String.fromCharCode(13, 10);
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
  console.log(`message sent: ${event.data}`);
};

webSocket.onerror = error => {
  console.log(`error: ${error.message}`);
};
