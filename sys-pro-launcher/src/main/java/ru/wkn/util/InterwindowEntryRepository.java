package ru.wkn.util;

public class InterwindowEntryRepository<T> implements Observable<T> {

    private Multicaster<T> multicaster;

    public InterwindowEntryRepository() {
        multicaster = new Multicaster<>(OperationType.WAITING_VALUE);
    }

    @Override
    public void addObserver(OperationType operationType, Observer<T> observer) {
        multicaster.subscribe(operationType, observer);
    }

    @Override
    public void removeObserver(OperationType operationType, Observer<T> observer) {
        multicaster.unsubscribe(operationType, observer);
    }

    @Override
    public void update(OperationType operationType, T dataObject) {
        multicaster.notify(operationType, dataObject);
    }
}
