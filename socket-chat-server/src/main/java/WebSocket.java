import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebSocket {

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final String REQUEST_DELIMITER = "\\r\\n\\r\\n";

    private static final String DIGEST_ALGORITHM = "SHA-1";

    private static final String MAGIC_STRING = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";

    private int port;

    private ServerSocket server;

    public WebSocket(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        server = new ServerSocket(this.port);

        try {
            System.out.println(
                ">>> Server has started on 127.0.0.1:" + this.port + ", waiting for a connection...");
            while (true) {
                Socket client = server.accept();
                System.out.println("<<< A client connected");
                InputStream in = client.getInputStream();
                OutputStream out = client.getOutputStream();
                Scanner scan = new Scanner(in, DEFAULT_CHARSET);
                performHandshake(scan, out);
                communicate(in, out);
            }
        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    private void performHandshake(Scanner scan, OutputStream out)
        throws NoSuchAlgorithmException, IOException {

        String data = scan.useDelimiter(REQUEST_DELIMITER).next();
        System.out.println("<<< data = " + data);
        Matcher get = Pattern.compile("^GET").matcher(data);
        if (get.find()) {
            byte[] response = buildResponseHandshake(data);
            out.write(response, 0, response.length);
            System.out.println("\r\n>>> server response: " + new String(response, DEFAULT_CHARSET));
        }
    }

    private String encode(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return Base64.getEncoder().encodeToString(MessageDigest.getInstance(DIGEST_ALGORITHM)
            .digest((data + MAGIC_STRING).getBytes(DEFAULT_CHARSET)));
    }

    private byte[] buildResponseHandshake(String data)
        throws UnsupportedEncodingException, NoSuchAlgorithmException {

        Matcher match = Pattern.compile("Sec-WebSocket-Key: (.*)").matcher(data);
        match.find(); ///////////////////// ?
        return ("HTTP/1.1 101 Switching Protocols\r\n"
            + "Connection: Upgrade\r\n"
            + "Upgrade: websocket\r\n"
            + "Sec-WebSocket-Accept: "
            + encode(match.group(1)) + "\r\n\r\n").getBytes(DEFAULT_CHARSET);
    }

    private void communicate(InputStream in, OutputStream out) throws IOException {
        System.out.print("- communicate: ");
        List<Integer> dataFrameList = new ArrayList<>();
        while (true) {
            if (in.available() > 0) {
                int read = in.read();
                dataFrameList.add(read);
                System.out.print(read + " ");

                if (in.available() == 1) {
                    dataFrameList.add(in.read());
                    byte[] dataFrame = convertToByteArray(dataFrameList);
                    out.write(decodeMessage(dataFrame).getBytes(DEFAULT_CHARSET));
                    // System.out.println("message = " + decodeMessage(dataFrame));
                }
            }
            else {
                dataFrameList = new ArrayList<>();
            }
        }
    }

    private byte[] convertToByteArray(List<Integer> source) {
        int size = source.size();
        byte[] result = new byte[size];
        for (int i = 0; i < size; ++i) {
            result[i] = source.get(i).byteValue();
        }
        return result;
    }

    private String decodeMessage(byte[] dataFrame) throws UnsupportedEncodingException {
        int frameLength = dataFrame.length;
        byte[] decodedMsg = new byte[frameLength - 6];
        byte[] encodedMsg = Arrays.copyOfRange(dataFrame, 6, frameLength);
        byte[] key = Arrays.copyOfRange(dataFrame, 2, 6);

        for (int i = 0; i < encodedMsg.length; i++) {
            decodedMsg[i] = (byte) (encodedMsg[i] ^ key[i & 0x3]);
        }

        return new String(decodedMsg, DEFAULT_CHARSET);
    }
}
