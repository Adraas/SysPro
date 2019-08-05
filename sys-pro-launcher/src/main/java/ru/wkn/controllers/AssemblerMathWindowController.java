package ru.wkn.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.wkn.jni.AssemblerMath;
import ru.wkn.jni.functions.AssemblerFunctions;
import ru.wkn.jni.functions.IAssemblerFunctions;

public class AssemblerMathWindowController extends Controller {

    @FXML
    private ChoiceBox<String> choiceBoxVariants;
    @FXML
    private TextField inputAField;
    @FXML
    private TextField inputBField;
    @FXML
    private TextArea assemblerMessageLogArea;

    private AssemblerMath assemblerMath;

    public void initialize() {
        initChoiceBox();
        IAssemblerFunctions assemblerFunctions = new AssemblerFunctions();
        assemblerMath = new AssemblerMath(assemblerFunctions);
    }

    private void initChoiceBox() {
        String[] variantItems = new String[]{"Dividing", "XOR"};
        ObservableList<String> variants = FXCollections.observableArrayList(variantItems);
        choiceBoxVariants.setItems(variants);
        choiceBoxVariants.setValue(variantItems[0]);
    }

    @FXML
    private void onClickRun() {
        String variant = choiceBoxVariants.getValue();
        try {
            if (variant.equals(choiceBoxVariants.getItems().get(0))) {
                double a = Double.parseDouble(inputAField.getText());
                double b = Double.parseDouble(inputBField.getText());
                assemblerMessageLogArea.setText(assemblerMessageLogArea.getText()
                        .concat("\nAnswer of dividing operation: ")
                        .concat(String.valueOf(assemblerMath.getAssemblerFunctions().dividingFunction(a, b))));
            } else {
                if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                    int a = Integer.parseInt(inputAField.getText());
                    int b = Integer.parseInt(inputBField.getText());
                    assemblerMessageLogArea.setText(assemblerMessageLogArea.getText()
                            .concat("\nAnswer of XOR operation: ")
                            .concat(String.valueOf(assemblerMath.getAssemblerFunctions().xorFunction(a, b))));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getClass().getSimpleName().concat(": ").concat(e.getMessage());
            assemblerMessageLogArea.setText(assemblerMessageLogArea.getText().concat("\n").concat(message));
            showInformation(e.getCause().getClass().getSimpleName(), message, Alert.AlertType.ERROR);
        }
    }
}
