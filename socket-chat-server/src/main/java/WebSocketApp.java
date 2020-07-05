import java.io.IOException;
import websocket.WebSocket;

public class WebSocketApp {

    public static void main(String[] args) throws IOException {
        WebSocket webSocket = new WebSocket(8080);
        webSocket.start();
    }

}
