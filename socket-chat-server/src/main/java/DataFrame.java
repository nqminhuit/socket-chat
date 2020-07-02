import java.io.IOException;

public class DataFrame {

    private static final byte WHOLE_TEXT_MESSAGE_BYTE = (byte) 129;

    private String message;

    private int messageLen;

    public DataFrame(String message) throws IOException {
        int len = message.length();
        if (len > 127) {
            throw new IOException("only accept message with length <= 127");
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
