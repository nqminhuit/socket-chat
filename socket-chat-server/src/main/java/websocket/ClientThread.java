package websocket;

import static java.lang.System.out;
import static utility.WebSocketConstants.DEFAULT_CHARSET;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import observer.OutputStreamObserver;
import observer.OutputStreamSubject;
import utility.WebsocketUtils;

public class ClientThread implements Runnable {

    private InputStream inputStream;

    private OutputStream outputStream;

    private OutputStreamSubject osSubject;

    public ClientThread(String id, Socket socket) {
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        osSubject = new OutputStreamSubject();
    }

    public OutputStream getOutputStream() {
        return this.outputStream;
    }

    @Override
    public void run() {
        out.println("<<< A client connected, id: " + this.toString());
        try {
            Scanner scanner = new Scanner(inputStream, DEFAULT_CHARSET);
            WebsocketUtils.performHandshake(scanner, outputStream);
            communicate(inputStream, outputStream);
        }
        catch (IOException | NoSuchAlgorithmException e) {
            out.println(e.getMessage());
        }
    }

    private void communicate(InputStream inputStream, OutputStream outputStream)
        throws IOException, NoSuchAlgorithmException {

        out.println("communication has started!");
        List<Integer> dataFrameList = new ArrayList<>();
        while (true) {
            if (inputStream.available() > 0) {
                int read = inputStream.read();
                dataFrameList.add(read);

                if (inputStream.available() == 1) {
                    dataFrameList.add(inputStream.read());
                    osSubject.informObservers(dataFrameList);
                }
            }
            else {
                dataFrameList = new ArrayList<>();
            }
        }
    }

    public void registerObservers(List<OutputStream> outputStreams) {
        outputStreams.forEach(outputStream -> {
            osSubject.registerObserver(new OutputStreamObserver(outputStream.toString(), outputStream));
        });
    }

}
