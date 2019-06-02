package ru.wkn.util;

public interface Notifier<T> {

    void update(OperationType operationType, T dataObject);
}
