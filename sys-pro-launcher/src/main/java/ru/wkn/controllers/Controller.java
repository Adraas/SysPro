package ru.wkn.controllers;

import javafx.scene.control.Alert;
import lombok.Getter;
import ru.wkn.entries.IEntry;
import ru.wkn.util.Observable;
import ru.wkn.util.ObservableType;
import ru.wkn.util.ObservablesRepository;
import ru.wkn.util.OperationType;
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
    @Getter
    public static ObservablesRepository<IEntry> observablesRepository;

    static {
        IWindowFactory windowFactory = new WindowFactory();
        windowRepository = new WindowRepository(windowFactory);
        observablesRepository = new ObservablesRepository<>();
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
            showInformation(e.getClass().getSimpleName(), e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    protected static void hideCurrentWindow(WindowType currentWindowType) {
        windowRepository.getWindow(currentWindowType).hide();
    }

    protected void updateEntriesTable(IEntry entry) {
        Observable<IEntry> observable = getObservablesRepository()
                .getObservable(ObservableType.OBSERVABLE_INTERWINDOW_REPOSITORY);
        openNewWindow(WindowType.NETWORK_RESOURCE_WINDOW, WindowType.FILE_DB_WINDOW);
        observable.update(OperationType.WAITING_VALUE, entry);
    }
}
