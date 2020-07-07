export class WebSocketHandler {
  constructor(ip, port, htmlElement) {
    // TODO: decouple this htmlElement
    this.webSocket = new WebSocket(`ws://${ip}:${port}`);
    this.htmlElement = htmlElement;

    this.webSocket.onmessage = event => {
      this.htmlElement.value += "your message: " + event.data + String.fromCharCode(13, 10);
    };

    this.webSocket.onopen = () => {
      console.log("websocket has opened!");
    };

    this.webSocket.onclose = () => {
      console.log("websocket has closed!");
    };

    this.webSocket.onerror = error => {
      console.log(`error: ${error.message}`);
    };
  }

  sendMessage(message) {
    if (this.webSocket.readyState !== WebSocket.OPEN) {
      console.error(`WebSocket is not open, readyState is ${this.webSocket.readyState}`);
      return;
    }

    this.webSocket.send(message);
  }
}
