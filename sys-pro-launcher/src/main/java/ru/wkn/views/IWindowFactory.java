package ru.wkn.views;

import ru.wkn.exceptions.WindowTypeException;

public interface IWindowFactory {

    IWindow createWindow(WindowType windowType) throws WindowTypeException;
}
