package ru.wkn.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Multicaster<T> {

    private Map<OperationType, List<Observer<T>>> typicallyObservers;

    public Multicaster(OperationType... operations) {
        typicallyObservers = new HashMap<>();
        for (OperationType operation : operations) {
            typicallyObservers.put(operation, new ArrayList<>());
        }
    }

    public void subscribe(OperationType operationType, Observer<T> observer) {
        typicallyObservers.get(operationType).add(observer);
    }

    public void unsubscribe(OperationType operationType, Observer<T> observer) {
        typicallyObservers.get(operationType).remove(observer);
    }

    public void notify(OperationType operationType, T dataObject) {
        List<Observer<T>> observers = typicallyObservers.get(operationType);
        for (Observer<T> observer : observers) {
            observer.update(dataObject);
        }
    }
}
