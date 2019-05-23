package ru.wkn.controllers;

import javafx.fxml.FXML;

public class MainWindowController extends Controller {

    public MainWindowController(WindowRepository windowRepository) {
        super(windowRepository);
    }

    @FXML
    private void clickOnFile() {
    }

    @FXML
    private void clickOnAnalyzer() {
    }

    @FXML
    private void clickOnAssembler() {
    }

    @FXML
    private void clickOnAbout() {
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
