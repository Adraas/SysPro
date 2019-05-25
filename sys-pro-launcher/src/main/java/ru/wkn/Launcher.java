package ru.wkn;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.java.Log;
import ru.wkn.controllers.Controller;
import ru.wkn.exceptions.WindowTypeException;
import ru.wkn.views.IWindow;
import ru.wkn.views.WindowRepository;
import ru.wkn.views.WindowType;
import ru.wkn.views.events.SwitchingEvent;

import java.io.IOException;

@Log
public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        WindowRepository windowRepository = Controller.getWindowRepository();
        IWindow window;
        try {
            SwitchingEvent<WindowType> switchingEvent = Controller.getSwitchingEvent();
            windowRepository.addWindow(WindowType.ANALYZER_WINDOW, switchingEvent);
            windowRepository.addWindow(WindowType.ASSEMBLER_MATH_WINDOW, switchingEvent);
            windowRepository.addWindow(WindowType.FILE_DB_WINDOW, switchingEvent);
            windowRepository.addWindow(WindowType.NETWORK_RESOURCE_WINDOW, switchingEvent);
            windowRepository.addWindow(WindowType.SERVER_INFORMATION_WINDOW, switchingEvent);
            windowRepository.addWindow(WindowType.NETWORK_ACCESS_WINDOW, switchingEvent);
            window = windowRepository.addWindow(WindowType.MAIN_WINDOW, switchingEvent);
            window.show();
        } catch (IOException | WindowTypeException e) {
            log.warning(e.getMessage());
            e.printStackTrace();
        }
    }
}
