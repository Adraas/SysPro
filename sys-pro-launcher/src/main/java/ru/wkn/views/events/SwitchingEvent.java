package ru.wkn.views.events;

public interface SwitchingEvent<T> {

    void executeSwitch(T currentValue, T newValue);
}
