package websocket;

import static java.lang.System.out;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebSocket {

    private int port;

    private ServerSocket server;

    public WebSocket(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        server = new ServerSocket(this.port);

        try {
            out.println(
                ">>> Server has started on 127.0.0.1:" + this.port + ", waiting for a connection...");
            while (true) {
                Socket client = server.accept();
                (new Thread(new ClientThread("id", client))).start();
                // use a hash map to put clients in then broadcast the message to all clients in the map
            }
        }
        catch (Exception e) {
            out.println("Exception: " + e.getMessage());
        }
    }


}
