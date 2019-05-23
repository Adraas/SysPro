package ru.wkn.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import ru.wkn.views.WindowRepository;

public class AnalyzerWindowController extends Controller {

    @FXML
    private TextArea inputArea;
    @FXML
    private TextArea messageLogArea;
    @FXML
    private ChoiceBox variantChoseBox;

    public AnalyzerWindowController(WindowRepository windowRepository) {
        super(windowRepository);
    }

    @FXML
    private void onClickRun() {
    }
}
