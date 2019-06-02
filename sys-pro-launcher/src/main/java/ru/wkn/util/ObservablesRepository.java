package ru.wkn.util;

import java.util.HashMap;
import java.util.Map;

public class ObservablesRepository<T> {

    private Map<ObservableType, Observable<T>> observables;

    public ObservablesRepository() {
        observables = new HashMap<>();
    }

    public void addObservable(ObservableType observableType, Observable<T> observable) {
        observables.put(observableType, observable);
    }

    public void removeObservable(ObservableType observableType) {
        observables.remove(observableType);
    }

    public Observable<T> getObservable(ObservableType observableType) {
        return observables.get(observableType);
    }
}
