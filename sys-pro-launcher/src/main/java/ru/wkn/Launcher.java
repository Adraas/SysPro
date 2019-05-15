package ru.wkn;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.wkn.views.IWindow;
import ru.wkn.views.IWindowFactory;
import ru.wkn.views.WindowFactory;
import ru.wkn.views.WindowRepository;
import ru.wkn.views.WindowType;

import java.io.IOException;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        IWindowFactory windowFactory = new WindowFactory();
        WindowRepository windowRepository = new WindowRepository(windowFactory);
        IWindow IWindow = windowRepository.addWindow(WindowType.MAIN_WINDOW);
        try {
            IWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
