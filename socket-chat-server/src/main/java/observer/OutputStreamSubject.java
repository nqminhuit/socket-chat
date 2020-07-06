package observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OutputStreamSubject implements Subject {

    private List<Observer> observers;

    @Override
    public void registerObserver(Observer observer) {
        if (observers == null) {
            observers = new ArrayList<>();
        }
        if (containsObserver(observer.getId())) {
            return;
        }
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(String id) {
        Observer toBeRemoved = findObserverById(id).get();
        this.observers.remove(toBeRemoved);
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

    private boolean containsObserver(String id) {
        return findObserverById(id).isPresent();
    }

    private Optional<Observer> findObserverById(String id) {
        return this.observers.stream().parallel()
            .filter(observer -> observer.getId().equals(id))
            .findAny();
    }

}
