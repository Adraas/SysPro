package ru.wkn;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.java.Log;
import ru.wkn.exceptions.WindowTypeException;
import ru.wkn.views.IWindow;
import ru.wkn.views.IWindowFactory;
import ru.wkn.views.WindowFactory;
import ru.wkn.views.WindowRepository;
import ru.wkn.views.WindowType;

import java.io.IOException;

@Log
public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        IWindowFactory windowFactory = new WindowFactory();
        WindowRepository windowRepository = new WindowRepository(windowFactory);
        IWindow window;
        try {
            window = windowRepository.addWindow(WindowType.MAIN_WINDOW);
            window.show();
        } catch (IOException | WindowTypeException e) {
            log.warning(e.getMessage());
            e.printStackTrace();
        }
    }
}
