package ru.wkn.controllers;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import ru.wkn.ExpressionAnalyzerFacade;
import ru.wkn.analyzers.ExpressionAnalyzerFactory;
import ru.wkn.analyzers.ExpressionAnalyzerType;
import ru.wkn.analyzers.IExpressionAnalyzerFactory;
import ru.wkn.analyzers.exceptions.AnalyzerException;
import ru.wkn.analyzers.exceptions.ExpressionException;
import ru.wkn.analyzers.exceptions.LanguageException;
import ru.wkn.analyzers.exceptions.SemanticsException;
import ru.wkn.analyzers.syntax.ExpressionAnalyzer;
import ru.wkn.analyzers.syntax.util.Language;

// TODO: add choice language and construction types
public class AnalyzerWindowController extends Controller {

    @FXML
    private ChoiceBox<String> choiceBoxVariants;
    @FXML
    private TextArea inputExpressionField;
    @FXML
    private CheckBox isSemanticsActivatedCheckBox;
    @FXML
    private TextArea expressionAnalyzerMessageLogArea;

    private IExpressionAnalyzerFactory expressionAnalyzerFactory;
    private ExpressionAnalyzerFacade expressionAnalyzerFacade;

    public void initialize() {
        initChoiceBox();
        initChoiceBoxEventHandlers();
        initCheckBoxEventHandlers();
        expressionAnalyzerFactory = new ExpressionAnalyzerFactory();
        ExpressionAnalyzer expressionAnalyzer = null;
        try {
            expressionAnalyzer = expressionAnalyzerFactory
                    .createExpressionAnalyzer(ExpressionAnalyzerType.CYCLE_WHILE_WITH_PRECONDITION,
                            Language.C_SHARPE, isSemanticsActivatedCheckBox.isSelected());
        } catch (LanguageException | AnalyzerException e) {
            informAboutExceptionCause(e);
        }
        expressionAnalyzerFacade = new ExpressionAnalyzerFacade(expressionAnalyzer,
                expressionAnalyzerFactory);
    }

    private void initChoiceBox() {
        String[] variantItems = new String[]{"Cycle \"while\" with precondition"};
        ObservableList<String> variants = FXCollections.observableArrayList(variantItems);
        choiceBoxVariants.setItems(variants);
        choiceBoxVariants.setValue(variantItems[0]);
    }

    private void initChoiceBoxEventHandlers() {
        ChangeListener<String> changeListener = (observable, oldValue, newValue) -> {
            String variant = choiceBoxVariants.getValue();
            if (variant.equals(choiceBoxVariants.getItems().get(0))) {
                try {
                    ExpressionAnalyzer expressionAnalyzer = expressionAnalyzerFactory
                            .createExpressionAnalyzer(ExpressionAnalyzerType.CYCLE_WHILE_WITH_PRECONDITION,
                                    Language.C_SHARPE, isSemanticsActivatedCheckBox.isSelected());
                    expressionAnalyzerFacade.setExpressionAnalyzer(expressionAnalyzer);
                } catch (LanguageException | AnalyzerException e) {
                    informAboutExceptionCause(e);
                }
            }
        };
        choiceBoxVariants.getSelectionModel().selectedItemProperty().addListener(changeListener);
    }

    private void initCheckBoxEventHandlers() {
        ChangeListener<Boolean> changeListener = ((observable, oldValue, newValue) -> {
            Boolean isSemanticsActivated = isSemanticsActivatedCheckBox.isSelected();
            expressionAnalyzerFacade.getExpressionAnalyzer().setSemanticsAnalyzerActivated(isSemanticsActivated);
        });
        isSemanticsActivatedCheckBox.selectedProperty().addListener(changeListener);
    }

    @FXML
    private void onClickRun() {
        Platform.runLater(() -> {
            String variant = choiceBoxVariants.getValue();
            if (variant.equals(choiceBoxVariants.getItems().get(0))) {
                try {
                    expressionAnalyzerMessageLogArea.setText(expressionAnalyzerMessageLogArea.getText()
                            .concat("\nExpression is correct: ")
                            .concat(String.valueOf(expressionAnalyzerFacade
                                    .getExpressionAnalyzer().isSyntaxCorrect(inputExpressionField.getText())))
                            .concat("\nExpression feasibility: ")
                            .concat(String.valueOf(expressionAnalyzerFacade
                                    .getExpressionAnalyzer().expressionIsSolved(inputExpressionField.getText())))
                            .concat("\n"));
                } catch (ExpressionException | SemanticsException e) {
                    informAboutExceptionCause(e);
                }
            }
        });
    }

    private void informAboutExceptionCause(Exception e) {
        e.printStackTrace();
        String message = e.getMessage();
        expressionAnalyzerMessageLogArea.setText(expressionAnalyzerMessageLogArea.getText()
                .concat("\n").concat(message));
        showInformation(e.getClass().getSimpleName(), message, Alert.AlertType.ERROR);
    }
}
