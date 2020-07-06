package observer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import utility.DataFrame;
import utility.WebsocketUtils;

public class OutputStreamObserver implements Observer {

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
        DataFrame dataFrame = WebsocketUtils.buildDataFrame(dataFrameList);
        outputStream.write(dataFrame.encodeDataFrame());
    }

}
