package observer;

import java.util.List;

public interface Subject {

    void registerObserver(Observer observer);

    void unregisterObserver(Observer observer);

    void informObservers(List<Integer> dataFrameList);
}
