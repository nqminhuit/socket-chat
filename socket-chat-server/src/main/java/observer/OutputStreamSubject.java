package observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OutputStreamSubject implements Subject {

    private List<Observer> observers;

    @Override
    public void registerObserver(Observer observer) {
        if (observers == null) {
            observers = new ArrayList<>();
        }
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        // TODO implement

    }

    @Override
    public void informObservers(List<Integer> dataFrameList) {
        observers.forEach(observer -> {
            try {
                observer.inform(dataFrameList);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
