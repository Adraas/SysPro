package ru.wkn.views;

import ru.wkn.exceptions.WindowTypeException;

public class WindowFactory implements IWindowFactory {

    @Override
    public IWindow createWindow(WindowType windowType) throws WindowTypeException {
        String resource;
        String title;
        int width;
        int height;
        switch (windowType) {
            case MAIN_WINDOW: {
                resource = "/fxml/main-view.fxml";
                title = "SysPro 1.0 | Main window";
                width = 600;
                height = 400;
                break;
            }
            case ANALYZER_WINDOW: {
                resource = "/fxml/analyzer-view.fxml";
                title = "SysPro 1.0 | Expression analyzer";
                width = 600;
                height = 422;
                break;
            }
            case ASSEMBLER_MATH_WINDOW: {
                resource = "/fxml/assembler-math-view.fxml";
                title = "SysPro 1.0 | ASMath";
                width = 600;
                height = 400;
                break;
            }
            case FILE_DB_WINDOW: {
                resource = "/fxml/file-db-view.fxml";
                title = "SysPro 1.0 | ERepository";
                width = 600;
                height = 400;
                break;
            }
            case NETWORK_RESOURCE_WINDOW: {
                resource = "/fxml/csv-entry-view.fxml";
                title = "Network resource";
                width = 343;
                height = 200;
                break;
            }
            case SERVER_INFORMATION_WINDOW: {
                resource = "/fxml/plain-text-entry-view.fxml";
                title = "Server information";
                width = 343;
                height = 200;
                break;
            }
            default: {
                throw new WindowTypeException("unknown window type for application.");
            }
        }
        return new Window(resource, title, width, height);
    }
}
