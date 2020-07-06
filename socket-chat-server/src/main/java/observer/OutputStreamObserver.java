package observer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class OutputStreamObserver implements Observer {

    private static final String DEFAULT_CHARSET = "UTF-8";

    private String id;

    private OutputStream outputStream;

    public OutputStreamObserver(String id, OutputStream outputStream) {
        this.id = id;
        this.outputStream = outputStream;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void inform(List<Integer> dataFrameList) throws UnsupportedEncodingException, IOException {
        DataFrame dataFrame = new DataFrame(decodeMessage(convertToByteArray(dataFrameList)));
        outputStream.write(dataFrame.encodeDataFrame());
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
