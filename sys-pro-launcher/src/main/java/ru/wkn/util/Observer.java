package ru.wkn.util;

public interface Observer<T> {

    void update(T dataObject);
}
