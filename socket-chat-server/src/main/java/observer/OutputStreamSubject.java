package observer;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OutputStreamSubject implements Subject {

    private List<Observer> observers;

    public OutputStreamSubject() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
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
        List<String> brokenStreamIds = new ArrayList<>();
        observers.forEach(observer -> {
            try {
                observer.inform(dataFrameList);
            }
            catch (IOException e) {
                if (e instanceof SocketException && e.getMessage().equals("Broken pipe (Write failed)")) {
                    brokenStreamIds.add(observer.getId());
                }
                else {
                    e.printStackTrace();
                }
            }
        });
        brokenStreamIds.forEach(stream -> this.unregisterObserver(stream));
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
