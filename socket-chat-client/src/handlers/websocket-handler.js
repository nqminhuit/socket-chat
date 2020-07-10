export class WebSocketHandler extends EventTarget {
  constructor(ip = "127.0.0.1", port = "8080") {
    super();
    this.webSocket = new WebSocket(`ws://${ip}:${port}`);

    this.webSocket.onmessage = event => {
      this.dispatchEvent(
        new CustomEvent("message-received", {
          detail: { message: event.data + String.fromCharCode(13, 10) },
        })
      );
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
