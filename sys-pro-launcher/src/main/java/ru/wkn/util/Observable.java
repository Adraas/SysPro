package ru.wkn.util;

public interface Observable<T> extends Notifier<T> {

    void addObserver(OperationType operationType, Observer<T> observer);

    void removeObserver(OperationType operationType, Observer<T> observer);
}
