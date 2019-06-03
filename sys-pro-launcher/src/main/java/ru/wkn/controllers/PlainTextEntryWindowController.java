package ru.wkn.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import ru.wkn.entries.IEntry;
import ru.wkn.entries.exceptions.EntryException;
import ru.wkn.entries.server.plaintext.ProtocolType;
import ru.wkn.entries.server.plaintext.ServerEntry;
import ru.wkn.util.Observable;
import ru.wkn.util.ObservableType;
import ru.wkn.util.OperationType;
import ru.wkn.views.WindowType;

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
            ServerEntry serverEntry = null;
            try {
                serverEntry = new ServerEntry(urlTextField.getText(),
                        Integer.valueOf(portTextField.getText()),
                        ProtocolType.getInstance(protocolTypeTextField.getText().toLowerCase()));
                urlTextField.clear();
                portTextField.clear();
                protocolTypeTextField.clear();
            } catch (EntryException e) {
                e.printStackTrace();
                showInformation(e.getClass().getSimpleName(), e.getMessage(), Alert.AlertType.ERROR);
            }
            Observable<IEntry> observable = getObservablesRepository()
                    .getObservable(ObservableType.OBERVABLE_INTERWINDOW_REPOSITORY);
            openNewWindow(WindowType.NETWORK_RESOURCE_WINDOW, WindowType.FILE_DB_WINDOW);
            observable.update(OperationType.WAITING_VALUE, serverEntry);
        });
    }
}
