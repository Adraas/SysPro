package ru.wkn.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import ru.wkn.entries.exceptions.EntryException;
import ru.wkn.entries.server.plaintext.ProtocolType;
import ru.wkn.entries.server.plaintext.ServerEntry;

public class PlainTextEntryWindowController extends Controller {

    @FXML
    private TextField urlTextField;
    @FXML
    private TextField portTextField;
    @FXML
    private TextField protocolTypeTextField;

    @FXML
    private void clickOnAccept() {
        Platform.runLater(() -> {
            ServerEntry serverEntry;
            try {
                serverEntry = new ServerEntry(urlTextField.getText(),
                        Integer.valueOf(portTextField.getText()),
                        ProtocolType.getInstance(protocolTypeTextField.getText().toLowerCase()));
                urlTextField.clear();
                portTextField.clear();
                protocolTypeTextField.clear();
                updateEntriesTable(serverEntry);
            } catch (EntryException e) {
                e.printStackTrace();
                showInformation(e.getClass().getSimpleName(), e.getMessage(), Alert.AlertType.ERROR);
            }
        });
    }
}
