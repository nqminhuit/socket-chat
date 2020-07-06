package websocket;

import static java.lang.System.out;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class WebSocket {

    private int port;

    private ServerSocket server;

    // private OutputStreamSubject osSubject;

    private List<OutputStream> outputStreams;

    public WebSocket(int port) {
        this.port = port;
        // osSubject = new OutputStreamSubject();
        outputStreams = new ArrayList<>();
    }

    public void start() throws IOException {
        server = new ServerSocket(this.port);

        try {
            out.println(
                ">>> Server has started on 127.0.0.1:" + this.port + ", waiting for a connection...");
            while (true) {
                Socket client = server.accept();
                ClientThread clientThread = new ClientThread("id", client);
                outputStreams.add(clientThread.getOutputStream());
                clientThread.registerObservers(outputStreams);
                (new Thread(clientThread)).start();
                // use a hash map to put clients in then broadcast the message to all clients in the map
            }
        }
        catch (Exception e) {
            out.println("Exception: " + e.getMessage());
        }
    }


}
