const sendButton = document.querySelector("#btnSendMsg");
const msgArea = document.querySelector("#msgArea");
const msgToSend = document.querySelector("#msgToSend");

sendButton.addEventListener("click", () => {
  msgArea.value += msgToSend.value + String.fromCharCode(13, 10);
  msgToSend.value = "";
});
