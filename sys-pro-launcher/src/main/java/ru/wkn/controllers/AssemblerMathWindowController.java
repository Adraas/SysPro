package ru.wkn.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.wkn.views.WindowRepository;

public class AssemblerMathWindowController extends Controller {

    @FXML
    private ChoiceBox assemblerVariantChoiceBox;
    @FXML
    private TextField inputAField;
    @FXML
    private TextField inputBField;
    @FXML
    private TextArea assemblerMessageLog;

    public AssemblerMathWindowController(WindowRepository windowRepository) {
        super(windowRepository);
    }

    @FXML
    private void onClickRun() {
    }
}
