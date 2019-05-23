package ru.wkn.views;

import java.io.IOException;

public interface IWindow {

    void show() throws IOException;

    void hide();

    void close();
}
