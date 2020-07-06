package observer;

import java.util.List;

public interface Subject {

    void registerObserver(Observer observer);

    void unregisterObserver(String id);

    void informObservers(List<Integer> dataFrameList);
}
