package utility;

import static utility.WebSocketConstants.MAXIMUM_TEXT_LEN;
import static utility.WebSocketConstants.WHOLE_TEXT_MESSAGE_BYTE;
import java.io.IOException;

public class DataFrame {

    private String message;

    private int messageLen;

    public DataFrame(String message) throws IOException {
        int len = message.length();
        if (len > MAXIMUM_TEXT_LEN) {
            throw new IOException("only accept message with length <= " + MAXIMUM_TEXT_LEN);
        }
        this.message = message;
        this.messageLen = len;
    }

    public byte[] encodeDataFrame() {
        // we use 2 for simplicity: send whole text with maxLength = 127
        byte[] encodedFrame = new byte[2 + messageLen];
        encodedFrame[0] = WHOLE_TEXT_MESSAGE_BYTE;
        encodedFrame[1] = (byte) messageLen;
        byte[] encodedMessage = message.getBytes();
        for (int i = 2; i < encodedFrame.length; ++i) {
            encodedFrame[i] = encodedMessage[i - 2];
        }
        return encodedFrame;
    }

}
