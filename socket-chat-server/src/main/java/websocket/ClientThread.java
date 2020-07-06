package websocket;

import static java.lang.System.out;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import observer.OutputStreamObserver;
import observer.OutputStreamSubject;

public class ClientThread implements Runnable {

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final String REQUEST_DELIMITER = "\\r\\n\\r\\n";

    private static final String DIGEST_ALGORITHM = "SHA-1";

    private static final String MAGIC_STRING = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";

    private Socket socket;

    private InputStream inputStream;

    private OutputStream outputStream;

    private OutputStreamSubject osSubject;

    // private String id;

    public ClientThread(String id, Socket socket) {
        // this.id = id;
        this.socket = socket;
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
            performHandshake(scanner, outputStream);
            communicate(inputStream, outputStream);
        }
        catch (IOException | NoSuchAlgorithmException e) {
            out.println(e.getMessage());
        }
    }

    private void performHandshake(Scanner scan, OutputStream outputStream)
        throws NoSuchAlgorithmException, IOException {

        String data = scan.useDelimiter(REQUEST_DELIMITER).next();
        out.println("<<< Received request:\n" + data + "<<<");
        Matcher get = Pattern.compile("^GET").matcher(data);
        if (get.find()) {
            byte[] response = buildResponseHandshake(data);
            outputStream.write(response);
            out.println("\r\n>>> server responsed:\n" + new String(response, DEFAULT_CHARSET) + ">>>\n");
        }
    }

    private String encode(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return Base64.getEncoder().encodeToString(MessageDigest.getInstance(DIGEST_ALGORITHM)
            .digest((data + MAGIC_STRING).getBytes(DEFAULT_CHARSET)));
    }

    private byte[] buildResponseHandshake(String data)
        throws UnsupportedEncodingException, NoSuchAlgorithmException {

        Matcher match = Pattern.compile("Sec-WebSocket-Key: (.*)").matcher(data);
        return match.find()
            ? ("HTTP/1.1 101 Switching Protocols\r\n"
                + "Connection: Upgrade\r\n"
                + "Upgrade: websocket\r\n"
                + "Sec-WebSocket-Accept: "
                + encode(match.group(1)) + "\r\n\r\n").getBytes(DEFAULT_CHARSET)
            : new byte[0];
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
                    // DataFrame dataFrame = new DataFrame(decodeMessage(convertToByteArray(dataFrameList)));
                    // outputStream.write(dataFrame.encodeDataFrame());
                }
            }
            else {
                dataFrameList = new ArrayList<>();
            }
        }
    }

    // private byte[] convertToByteArray(List<Integer> source) {
    //     int size = source.size();
    //     byte[] result = new byte[size];
    //     for (int i = 0; i < size; ++i) {
    //         result[i] = source.get(i).byteValue();
    //     }
    //     return result;
    // }

    // private String decodeMessage(byte[] dataFrame) throws UnsupportedEncodingException {
    //     int frameLength = dataFrame.length;
    //     byte[] decodedMsg = new byte[frameLength - 6];
    //     byte[] encodedMsg = Arrays.copyOfRange(dataFrame, 6, frameLength);
    //     byte[] key = Arrays.copyOfRange(dataFrame, 2, 6);

    //     for (int i = 0; i < encodedMsg.length; i++) {
    //         decodedMsg[i] = (byte) (encodedMsg[i] ^ key[i & 0x3]);
    //     }

    //     return new String(decodedMsg, DEFAULT_CHARSET);
    // }

    public void registerObservers(List<OutputStream> outputStreams) {
        outputStreams.forEach(outputStream -> {
            osSubject.registerObserver(new OutputStreamObserver(outputStream));
        });
    }

}
