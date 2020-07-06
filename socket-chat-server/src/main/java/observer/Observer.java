package observer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface Observer {

    String getId();

    void inform(List<Integer> dataFrameList) throws UnsupportedEncodingException, IOException;
}
