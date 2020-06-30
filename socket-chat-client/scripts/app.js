const sendButton = document.querySelector("#btnSendMsg");
const msgArea = document.querySelector("#msgArea");
const msgToSend = document.querySelector("#msgToSend");
const webSocket = new WebSocket("wss://127.0.0.1:80");

function sendMessage() {
  msgArea.value += msgToSend.value + String.fromCharCode(13, 10);
  msgToSend.value = "";
}

sendButton.addEventListener("click", sendMessage);
msgToSend.addEventListener("keypress", event => {
  if (event.code === "Enter") {
    sendMessage();
  }
});

webSocket.onopen = function (event) {
  console.log(`opened! event = ${event}`);
  webSocket.send("Hello server!");
};
