package ru.wkn.controllers;

import javafx.scene.control.Alert;
import lombok.Getter;
import ru.wkn.views.IWindow;
import ru.wkn.views.IWindowFactory;
import ru.wkn.views.WindowFactory;
import ru.wkn.views.WindowRepository;
import ru.wkn.views.WindowType;
import ru.wkn.views.events.SwitchingEvent;

import java.io.IOException;

public abstract class Controller {

    @Getter
    private static WindowRepository windowRepository;
    @Getter
    private static SwitchingEvent<WindowType> switchingEvent = Controller::openNewWindow;

    static {
        IWindowFactory windowFactory = new WindowFactory();
        windowRepository = new WindowRepository(windowFactory);
    }

    protected static void showInformation(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    protected static void openNewWindow(WindowType currentWindowType, WindowType newWindowType) {
        try {
            windowRepository.getWindow(currentWindowType).hide();
            IWindow newWindow = windowRepository.getWindow(newWindowType);
            newWindow.show();
        } catch (IOException e) {
            showInformation("Error", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    protected static void hideCurrentWindow(WindowType currentWindowType) {
        windowRepository.getWindow(currentWindowType).hide();
    }
}
