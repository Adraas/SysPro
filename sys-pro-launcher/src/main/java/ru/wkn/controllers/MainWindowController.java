package ru.wkn.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import ru.wkn.views.WindowType;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

public class MainWindowController extends Controller {

    @FXML
    private void clickOnFile() {
        openNewWindow(WindowType.MAIN_WINDOW, WindowType.FILE_DB_WINDOW);
    }

    @FXML
    private void clickOnAnalyzer() {
        openNewWindow(WindowType.MAIN_WINDOW, WindowType.ANALYZER_WINDOW);
    }

    @FXML
    private void clickOnAssembler() {
        openNewWindow(WindowType.MAIN_WINDOW, WindowType.ASSEMBLER_MATH_WINDOW);
    }

    @FXML
    private void clickOnAbout() {
        Properties properties = new Properties();
        Reader reader =
                new InputStreamReader(getClass().getResourceAsStream("/information/info.properties"));
        try {
            properties.load(reader);
        } catch (IOException e) {
            showInformation(e.getClass().getSimpleName(), e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
        showInformation(properties.getProperty("title"), properties.getProperty("message"),
                Alert.AlertType.INFORMATION);
    }

    @FXML
    private void clickOnFileWorking() {
        clickOnFile();
    }

    @FXML
    private void clickOnExpressionAnalyzer() {
        clickOnAnalyzer();
    }

    @FXML
    private void clickOnAssemblerIncludes() {
        clickOnAssembler();
    }
}
