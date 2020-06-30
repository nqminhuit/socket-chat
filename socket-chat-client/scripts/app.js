const sendButton = document.querySelector("#btnSendMsg");
const msgArea = document.querySelector("#msgArea");
const msgToSend = document.querySelector("#msgToSend");

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
