package utility;

import static java.lang.System.out;
import static utility.WebSocketConstants.DEFAULT_CHARSET;
import static utility.WebSocketConstants.DIGEST_ALGORITHM;
import static utility.WebSocketConstants.MAGIC_STRING;
import static utility.WebSocketConstants.REQUEST_DELIMITER;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebsocketUtils {

    public static void performHandshake(Scanner scan, OutputStream outputStream)
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

    private static String encode(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return Base64.getEncoder().encodeToString(MessageDigest.getInstance(DIGEST_ALGORITHM)
            .digest((data + MAGIC_STRING).getBytes(DEFAULT_CHARSET)));
    }

    private static byte[] buildResponseHandshake(String data)
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

    private static byte[] convertToByteArray(List<Integer> source) {
        int size = source.size();
        byte[] result = new byte[size];
        for (int i = 0; i < size; ++i) {
            result[i] = source.get(i).byteValue();
        }
        return result;
    }

    private static String decodeMessage(byte[] dataFrame) throws UnsupportedEncodingException {
        int frameLength = dataFrame.length;
        byte[] decodedMsg = new byte[frameLength - 6];
        byte[] encodedMsg = Arrays.copyOfRange(dataFrame, 6, frameLength);
        byte[] key = Arrays.copyOfRange(dataFrame, 2, 6);

        for (int i = 0; i < encodedMsg.length; i++) {
            decodedMsg[i] = (byte) (encodedMsg[i] ^ key[i & 0x3]);
        }

        return new String(decodedMsg, DEFAULT_CHARSET);
    }

    public static DataFrame buildDataFrame(List<Integer> dataFrameList)
        throws UnsupportedEncodingException, IOException {

        return new DataFrame(WebsocketUtils.decodeMessage(WebsocketUtils.convertToByteArray(dataFrameList)));
    }

}
