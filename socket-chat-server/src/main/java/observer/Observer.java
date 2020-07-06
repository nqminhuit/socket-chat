package observer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface Observer {

    void inform(List<Integer> dataFrameList) throws UnsupportedEncodingException, IOException;
}
