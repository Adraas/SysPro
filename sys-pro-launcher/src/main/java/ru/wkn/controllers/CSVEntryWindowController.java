package ru.wkn.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import ru.wkn.entries.IEntry;
import ru.wkn.entries.exceptions.EntryException;
import ru.wkn.entries.resource.csv.AccessMode;
import ru.wkn.entries.resource.csv.ResourceEntry;
import ru.wkn.util.Observable;
import ru.wkn.util.ObservableType;
import ru.wkn.util.OperationType;
import ru.wkn.views.WindowType;

import java.sql.Date;

public class CSVEntryWindowController extends Controller {

    @FXML
    private TextField urlTextField;
    @FXML
    private TextField accessModeTextField;
    @FXML
    private TextField dateTextField;

    @FXML
    private void onClickAccept() {
        Platform.runLater(() -> {
            ResourceEntry resourceEntry = null;
            try {
                resourceEntry = new ResourceEntry(urlTextField.getText(),
                        AccessMode.getInstance(accessModeTextField.getText().toLowerCase()),
                        Date.valueOf(dateTextField.getText()));
                urlTextField.clear();
                accessModeTextField.clear();
                dateTextField.clear();
            } catch (EntryException e) {
                e.printStackTrace();
                showInformation(e.getClass().getSimpleName(), e.getMessage(), Alert.AlertType.ERROR);
            }
            Observable<IEntry> observable = getObservablesRepository()
                    .getObservable(ObservableType.OBERVABLE_INTERWINDOW_REPOSITORY);
            openNewWindow(WindowType.NETWORK_RESOURCE_WINDOW, WindowType.FILE_DB_WINDOW);
            observable.update(OperationType.WAITING_VALUE, resourceEntry);
        });
    }
}
