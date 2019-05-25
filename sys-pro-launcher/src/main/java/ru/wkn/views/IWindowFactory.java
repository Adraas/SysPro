package ru.wkn.views;

import ru.wkn.exceptions.WindowTypeException;
import ru.wkn.views.events.SwitchingEvent;

public interface IWindowFactory {

    IWindow createWindow(WindowType windowType, SwitchingEvent<WindowType> switchingEvent) throws WindowTypeException;
}
