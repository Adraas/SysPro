package ru.wkn.controllers;

import javafx.scene.control.Alert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.wkn.exceptions.WindowTypeException;
import ru.wkn.views.IWindow;
import ru.wkn.views.WindowRepository;
import ru.wkn.views.WindowType;

import java.io.IOException;

@AllArgsConstructor
@Getter
public abstract class Controller {

    private WindowRepository windowRepository;

    public void showInformation(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    public void openNewWindow(WindowType currentWindowType, WindowType newWindowType) {
        try {
            getWindowRepository().getWindow(currentWindowType).hide();
            IWindow newWindow = getWindowRepository().addWindow(newWindowType);
            newWindow.show();
        } catch (WindowTypeException | IOException e) {
            showInformation("Error", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
}
