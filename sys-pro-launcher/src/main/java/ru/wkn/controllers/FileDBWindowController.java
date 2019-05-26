package ru.wkn.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ru.wkn.entries.resource.csv.AccessMode;
import ru.wkn.entries.resource.csv.ResourceEntry;
import ru.wkn.entries.server.plaintext.ProtocolType;
import ru.wkn.entries.server.plaintext.ServerEntry;
import ru.wkn.exceptions.ControllerException;
import ru.wkn.repository.RepositoryFacade;
import ru.wkn.repository.dao.EntityInstance;

import java.sql.Date;
import java.util.List;

// TODO: complete this class
public class FileDBWindowController extends Controller {

    @FXML
    private ChoiceBox choiceBoxVariants;
    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableView tableView;

    private DatasourceType datasourceType;
    private RepositoryFacade repositoryFacade;

    public void initialize() {
        String[] variantItems = new String[]{"CSV: Network resource", "TXT: Network server"};
        ObservableList<String> variants =
                FXCollections.observableArrayList(variantItems);
        choiceBoxVariants.setItems(variants);
        choiceBoxVariants.setValue(variantItems[0]);
    }

    @FXML
    private void clickOnNewFile() {
    }

    @FXML
    private void clickOnOpenFile() {
    }

    @FXML
    private void clickOnOpenDatabase() {
        initializeRepositoryFacade();
        datasourceType = DatasourceType.DATABASE;
    }

    @FXML
    private void clickOnSaveAsFile() {
    }

    @FXML
    private void clickOnSaveToDatabase() {
    }

    @FXML
    private void clickOnAdd() {
    }

    @FXML
    private void clickOnEdit() {
    }

    @FXML
    private void clickOnDelete() {
    }

    @FXML
    private void clickOnUpdate() {
        boolean addButtonVisible;
        boolean editAndDeleteButtonsVisible;
        try {
            updateTableView();
            addButtonVisible = true;
            editAndDeleteButtonsVisible = tableView.getItems().size() > 0;
        } catch (ControllerException e) {
            addButtonVisible = false;
            editAndDeleteButtonsVisible = false;
        }
        setVisibleProperties(addButtonVisible, editAndDeleteButtonsVisible, editAndDeleteButtonsVisible);
    }

    private void setVisibleProperties(boolean addButtonVisible, boolean editButtonVisible, boolean deleteButtonVisible) {
        addButton.setVisible(addButtonVisible);
        editButton.setVisible(editButtonVisible);
        deleteButton.setVisible(deleteButtonVisible);
    }

    private void clearTableView() {
        tableView.getItems().clear();
        tableView.getColumns().clear();
    }

    // TODO: complete this method
    private void updateTableView() throws ControllerException {
        clearTableView();
        String variant = (String) choiceBoxVariants.getValue();
        if (variant.equals(choiceBoxVariants.getItems().get(0))) {
            TableColumn<ResourceEntry, Long> idColumn = new TableColumn<>("ID");
            TableColumn<ResourceEntry, String> urlColumn = new TableColumn<>("URL");
            TableColumn<ResourceEntry, AccessMode> accessModeColumn = new TableColumn<>("Access mode");
            TableColumn<ResourceEntry, Date> dateAccessColumn = new TableColumn<>("Date access");
            tableView.getColumns().addAll(idColumn, urlColumn, accessModeColumn, dateAccessColumn);

            List<ResourceEntry> resourceEntries;
            switch (datasourceType) {
                case FILE: {
                }
                case DATABASE: {
                    tableView.getItems().addAll(repositoryFacade.getService().getAll());
                }
                default: {
                    showInformation("Error", "This datasource type not found!", Alert.AlertType.ERROR);
                    throw new ControllerException("datasource not found");
                }
            }
        } else {
            if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                TableColumn<ServerEntry, Long> idColumn = new TableColumn<>("ID");
                TableColumn<ServerEntry, String> urlColumn = new TableColumn<>("URL");
                TableColumn<ServerEntry, Integer> portColumn = new TableColumn<>("Port");
                TableColumn<ServerEntry, ProtocolType> protocolTypeColumn = new TableColumn<>("Protocol type");
                tableView.getColumns().addAll(idColumn, urlColumn, portColumn, protocolTypeColumn);
            }
        }
    }

    private void initializeRepositoryFacade() {
        String variant = (String) choiceBoxVariants.getValue();
        if (variant.equals(choiceBoxVariants.getItems().get(0))
                && !repositoryFacade.getEntityInstance().equals(EntityInstance.NETWORK_RESOURCE)) {
            repositoryFacade = new RepositoryFacade<>(EntityInstance.NETWORK_RESOURCE);
        } else {
            if (variant.equals(choiceBoxVariants.getItems().get(1))
                    && !repositoryFacade.getEntityInstance().equals(EntityInstance.NETWORK_SERVER)) {
                repositoryFacade = new RepositoryFacade<>(EntityInstance.NETWORK_SERVER);
            }
        }
    }

    private enum DatasourceType {

        DATABASE, FILE
    }
}
