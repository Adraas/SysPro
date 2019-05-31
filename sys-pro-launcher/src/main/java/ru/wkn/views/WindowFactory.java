package ru.wkn.views;

import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.wkn.exceptions.WindowTypeException;
import ru.wkn.views.events.SwitchingEvent;

public class WindowFactory implements IWindowFactory {

    @Override
    public IWindow createWindow(WindowType windowType, SwitchingEvent<WindowType> switchingEvent)
            throws WindowTypeException {
        String resource;
        String title;
        int width;
        int height;
        Stage stage = new Stage();
        switch (windowType) {
            case MAIN_WINDOW: {
                resource = "/fxml/main-view.fxml";
                title = "SysPro 1.0 | Main window";
                width = 600;
                height = 400;
                stage.setOnCloseRequest(event -> System.exit(0));
                return new Window(resource, title, width, height, stage);
            }
            case ANALYZER_WINDOW: {
                resource = "/fxml/analyzer-view.fxml";
                title = "SysPro 1.0 | Expression analyzer";
                width = 600;
                height = 422;
                stage.setOnCloseRequest(event -> switchingEvent
                        .executeSwitch(WindowType.ANALYZER_WINDOW, WindowType.MAIN_WINDOW));
                break;
            }
            case ASSEMBLER_MATH_WINDOW: {
                resource = "/fxml/assembler-math-view.fxml";
                title = "SysPro 1.0 | ASMath";
                width = 600;
                height = 400;
                stage.setOnCloseRequest(event -> switchingEvent
                        .executeSwitch(WindowType.ASSEMBLER_MATH_WINDOW, WindowType.MAIN_WINDOW));
                break;
            }
            case FILE_DB_WINDOW: {
                resource = "/fxml/file-db-view.fxml";
                title = "SysPro 1.0 | ERepository";
                width = 600;
                height = 400;
                stage.setOnCloseRequest(event -> switchingEvent
                        .executeSwitch(WindowType.FILE_DB_WINDOW, WindowType.MAIN_WINDOW));
                break;
            }
            case NETWORK_RESOURCE_WINDOW: {
                resource = "/fxml/csv-entry-view.fxml";
                title = "Network resource";
                width = 343;
                height = 200;
                stage.setOnCloseRequest(event -> switchingEvent
                        .executeSwitch(WindowType.NETWORK_RESOURCE_WINDOW, WindowType.FILE_DB_WINDOW));
                stage.initModality(Modality.WINDOW_MODAL);
                break;
            }
            case SERVER_INFORMATION_WINDOW: {
                resource = "/fxml/plain-text-entry-view.fxml";
                title = "Server information";
                width = 343;
                height = 200;
                stage.setOnCloseRequest(event -> switchingEvent
                        .executeSwitch(WindowType.SERVER_INFORMATION_WINDOW, WindowType.FILE_DB_WINDOW));
                stage.initModality(Modality.WINDOW_MODAL);
                break;
            }
            case NETWORK_ACCESS_WINDOW: {
                resource = "/fxml/bin-entry-view.fxml";
                title = "Network access";
                width = 343;
                height = 200;
                stage.setOnCloseRequest(event -> switchingEvent
                        .executeSwitch(WindowType.NETWORK_ACCESS_WINDOW, WindowType.FILE_DB_WINDOW));
                stage.initModality(Modality.WINDOW_MODAL);
                break;
            }
            default: {
                throw new WindowTypeException("unknown window type for application.");
            }
        }
        return new Window(resource, title, width, height, stage);
    }
}
