package ru.wkn.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import ru.wkn.entries.exceptions.EntryException;
import ru.wkn.entries.resource.csv.AccessMode;
import ru.wkn.entries.resource.csv.ResourceEntry;

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
            ResourceEntry resourceEntry;
            try {
                resourceEntry = new ResourceEntry(urlTextField.getText(),
                        AccessMode.getInstance(accessModeTextField.getText().toLowerCase()),
                        Date.valueOf(dateTextField.getText()));
                urlTextField.clear();
                accessModeTextField.clear();
                dateTextField.clear();
                updateEntriesTable(resourceEntry);
            } catch (EntryException e) {
                e.printStackTrace();
                showInformation(e.getClass().getSimpleName(), e.getMessage(), Alert.AlertType.ERROR);
            }
        });
    }
}
