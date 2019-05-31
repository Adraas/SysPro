package ru.wkn.controllers;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import lombok.AllArgsConstructor;
import ru.wkn.FileRWFacade;
import ru.wkn.entries.EntryFactory;
import ru.wkn.entries.IEntryFactory;
import ru.wkn.entries.ParametersDelimiter;
import ru.wkn.entries.exceptions.EntryException;
import ru.wkn.entries.resource.csv.AccessMode;
import ru.wkn.entries.resource.csv.ResourceEntry;
import ru.wkn.entries.server.plaintext.ProtocolType;
import ru.wkn.entries.server.plaintext.ServerEntry;
import ru.wkn.filerw.EFileReader;
import ru.wkn.filerw.EFileWriter;
import ru.wkn.filerw.files.EFile;
import ru.wkn.filerw.files.FileFactory;
import ru.wkn.filerw.files.IFileFactory;
import ru.wkn.filerw.readers.EntriesDelimiter;
import ru.wkn.filerw.readers.FileReader;
import ru.wkn.filerw.writers.FileWriter;
import ru.wkn.repository.RepositoryFacade;
import ru.wkn.repository.dao.EntityInstance;
import ru.wkn.repository.exceptions.PersistenceException;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

// TODO: complete this class
public class FileDBWindowController extends Controller {

    @FXML
    private ChoiceBox<String> choiceBoxVariants;
    @FXML
    private MenuItem saveMenuButton;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;

    @FXML
    private TableView<ResourceEntry> resourceEntryTableView;
    @FXML
    private TableColumn<ResourceEntry, String> resourceEntryUrlColumn;
    @FXML
    private TableColumn<ResourceEntry, AccessMode> resourceEntryAccessModeColumn;
    @FXML
    private TableColumn<ResourceEntry, Date> resourceEntryAccessDateColumn;

    @FXML
    private TableView<ServerEntry> serverEntryTableView;
    @FXML
    private TableColumn<ServerEntry, String> serverEntryUrlColumn;
    @FXML
    private TableColumn<ServerEntry, String> serverEntryPortColumn;
    @FXML
    private TableColumn<ServerEntry, ProtocolType> serverEntryProtocolTypeColumn;

    private FileChooser fileChooser;

    private DatasourceType datasourceType;
    private RepositoryFacade<ResourceEntry, Long> resourceEntryRepositoryFacade;
    private RepositoryFacade<ServerEntry, Long> serverEntryRepositoryFacade;

    private String charsetName = "windows-1251";
    private IFileFactory<ResourceEntry> resourceEntryIFileFactory;
    private IFileFactory<ServerEntry> serverEntryIFileFactory;
    private FileRWFacade<ResourceEntry> resourceEntryFileRWFacade;
    private FileRWFacade<ServerEntry> serverEntryFileRWFacade;

    private IEntryFactory entryFactory;
    private EFile<ResourceEntry> resourceEntryEFile;
    private EFile<ServerEntry> serverEntryEFile;

    private EFileReader<ResourceEntry> resourceEntryEFileReader;
    private EFileWriter<ResourceEntry> resourceEntryEFileWriter;

    private EFileReader<ServerEntry> serverEntryEFileReader;
    private EFileWriter<ServerEntry> serverEntryEFileWriter;

    private boolean isCollectionActivated = false;

    public void initialize() {
        initTablesCellValuesFactories();
        initColumnsEditEventHandlers();
        initChoiceBox();
        initChoiceBoxEventHandlers();

        datasourceType = DatasourceType.NONE;

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("Plain Text", "*.txt"));
    }

    private void initTablesCellValuesFactories() {
        resourceEntryUrlColumn.setCellValueFactory(new PropertyValueFactory<>("url"));
        resourceEntryAccessModeColumn.setCellValueFactory(new PropertyValueFactory<>("accessMode"));
        resourceEntryAccessDateColumn.setCellValueFactory(new PropertyValueFactory<>("accessDate"));

        serverEntryUrlColumn.setCellValueFactory(new PropertyValueFactory<>("url"));
        serverEntryPortColumn.setCellValueFactory(new PropertyValueFactory<>("port"));
        serverEntryProtocolTypeColumn.setCellValueFactory(new PropertyValueFactory<>("protocolType"));
    }

    private void initColumnsEditEventHandlers() {
        resourceEntryUrlColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        resourceEntryUrlColumn.setOnEditCommit(event -> {
            String url = event.getNewValue();
            int row = event.getTablePosition().getRow();
            ResourceEntry resourceEntry = event.getTableView().getItems().get(row);
            resourceEntry.setUrl(url);
        });

        ObservableList<AccessMode> accessModes = FXCollections.observableArrayList(AccessMode.values());
        resourceEntryAccessModeColumn.setCellFactory(ComboBoxTableCell.forTableColumn(accessModes));
        resourceEntryAccessModeColumn.setOnEditCommit((TableColumn.CellEditEvent<ResourceEntry, AccessMode> event) -> {
            AccessMode newAccessMode = event.getNewValue();
            int row = event.getTablePosition().getRow();
            ResourceEntry resourceEntry = event.getTableView().getItems().get(row);
            resourceEntry.setAccessMode(newAccessMode);
        });

        serverEntryUrlColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        serverEntryUrlColumn.setOnEditCommit(event -> {
            String url = event.getNewValue();
            int row = event.getTablePosition().getRow();
            ServerEntry serverEntry = event.getTableView().getItems().get(row);
            serverEntry.setUrl(url);
        });

        ObservableList<ProtocolType> protocolTypes = FXCollections.observableArrayList(ProtocolType.values());
        serverEntryProtocolTypeColumn.setCellFactory(ComboBoxTableCell.forTableColumn(protocolTypes));
        serverEntryProtocolTypeColumn.setOnEditCommit((TableColumn.CellEditEvent<ServerEntry, ProtocolType> event) -> {
            ProtocolType newProtocolType = event.getNewValue();
            int row = event.getTablePosition().getRow();
            ServerEntry serverEntry = event.getTableView().getItems().get(row);
            serverEntry.setProtocolType(newProtocolType);
        });
    }

    private void initChoiceBox() {
        String[] variantItems = new String[]{"CSV: Network resource", "TXT: Network server"};
        ObservableList<String> variants =
                FXCollections.observableArrayList(variantItems);
        choiceBoxVariants.setItems(variants);
        choiceBoxVariants.setValue(variantItems[0]);
    }

    private void initChoiceBoxEventHandlers() {
        ChangeListener<String> changeListener = (observable, oldValue, newValue) -> {
            String variant = choiceBoxVariants.getValue();
            if (variant.equals(choiceBoxVariants.getItems().get(0))) {
                resourceEntryTableView.setVisible(true);
                serverEntryTableView.setVisible(false);
            }
            if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                resourceEntryTableView.setVisible(false);
                serverEntryTableView.setVisible(true);
            }
            updateButtons();
        };
        choiceBoxVariants.getSelectionModel().selectedItemProperty().addListener(changeListener);
    }

    @FXML
    private void clickOnNewList() {
        isCollectionActivated = true;
        String variant = choiceBoxVariants.getValue();
        switch (datasourceType) {
            case FILE: {
                clearFileWithoutSaving(variant);
                break;
            }
            case DATABASE: {
                try {
                    if (variant.equals(choiceBoxVariants.getItems().get(0))) {
                        resourceEntryRepositoryFacade.getService().deleteAll();
                    } else {
                        if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                            serverEntryRepositoryFacade.getService().deleteAll();
                        }
                    }
                } catch (PersistenceException e) {
                    e.printStackTrace();
                    showInformation("Error", e.getMessage(), Alert.AlertType.ERROR);
                }
                break;
            }
            case NONE: {
                break;
            }
            default: {
                showInformation("Error", "This datasource type not found!", Alert.AlertType.ERROR);
            }
        }
        updateTableView();
        updateButtons();
        datasourceType = DatasourceType.NONE;
    }

    @FXML
    private void clickOnOpenFile() {
        Platform.runLater(() -> {
            isCollectionActivated = true;
            File file = openFile();
            if (file != null) {
                initFileRWFacade();
                updateTableView();
                updateButtons();
            }
        });
    }

    @FXML
    private void clickOnOpenDatabase() {
        Platform.runLater(() -> {
            isCollectionActivated = true;
            datasourceType = DatasourceType.DATABASE;
            updateTableView();
            updateButtons();
        });
    }

    @FXML
    private void clickOnSaveAsFile() {
        Platform.runLater(() -> {
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                String variant = choiceBoxVariants.getValue();
                try {
                    if (variant.equals(choiceBoxVariants.getItems().get(0))) {
                        resourceEntryFileRWFacade.getFileWriter().saveFile(file.getAbsolutePath());
                    } else {
                        if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                            serverEntryFileRWFacade.getFileWriter().saveFile(file.getAbsolutePath());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    showInformation("Error", e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });
    }

    @FXML
    private void clickOnAdd() {
    }

    @FXML
    private void clickOnDelete() {
        String variant = choiceBoxVariants.getValue();
        if (variant.equals(choiceBoxVariants.getItems().get(0))) {
            resourceEntryFileRWFacade.getFileWriter().delete(resourceEntryTableView.getSelectionModel()
                    .getSelectedItem());
        } else {
            if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                serverEntryFileRWFacade.getFileWriter().delete(serverEntryTableView.getSelectionModel()
                        .getSelectedItem());
            }
        }
        updateTableView();
        updateButtons();
    }

    private void clearFileWithoutSaving(String variant) {
        if (variant.equals(choiceBoxVariants.getItems().get(0))) {
            resourceEntryFileRWFacade.getFileWriter().deleteSome(0, resourceEntryFileRWFacade.getFileReader()
                    .getEFile().getEntries().size() - 1);
        } else {
            if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                serverEntryFileRWFacade.getFileWriter().deleteSome(0, serverEntryFileRWFacade.getFileReader()
                        .getEFile().getEntries().size() - 1);
            }
        }
    }

    private void clearTableView() {
        String variant = choiceBoxVariants.getValue();
        if (variant.equals(choiceBoxVariants.getItems().get(0))) {
            resourceEntryTableView.getItems().clear();
        } else {
            if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                serverEntryTableView.getItems().clear();
            }
        }
    }

    private File openFile() {
        datasourceType = DatasourceType.FILE;
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String fileName = file.getName();
            if (fileName.endsWith("csv")) {
                choiceBoxVariants.setValue(choiceBoxVariants.getItems().get(0));
                if (resourceEntryIFileFactory == null) {
                    resourceEntryIFileFactory = new FileFactory<>();
                }
            } else {
                if (fileName.endsWith("txt")) {
                    choiceBoxVariants.setValue(choiceBoxVariants.getItems().get(1));
                    if (serverEntryIFileFactory == null) {
                        serverEntryIFileFactory = new FileFactory<>();
                    }
                }
            }
            try {
                initEntryFile(file.getAbsolutePath());
            } catch (IOException | EntryException e) {
                e.printStackTrace();
                showInformation("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
        return file;
    }

    private void initEntryFile(String absolutePath) throws IOException, EntryException {
        entryFactory = (entryFactory == null) ? new EntryFactory() : entryFactory;
        String variant = choiceBoxVariants.getValue();
        if (variant.equals(choiceBoxVariants.getItems().get(0))) {
            resourceEntryEFile = (resourceEntryEFile == null)
                    ? resourceEntryIFileFactory.createEFile(absolutePath, charsetName,
                    EntriesDelimiter.CSV_DELIMITER, entryFactory, ParametersDelimiter.RESOURCE_CSV_DELIMITER)
                    : resourceEntryEFile.copyFrom(resourceEntryIFileFactory.createEFile(absolutePath, charsetName,
                    EntriesDelimiter.CSV_DELIMITER, entryFactory, ParametersDelimiter.RESOURCE_CSV_DELIMITER));
        } else {
            if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                serverEntryEFile = (serverEntryEFile == null)
                        ? serverEntryIFileFactory.createEFile(absolutePath, charsetName,
                        EntriesDelimiter.PLAIN_TEXT_DELIMITER, entryFactory,
                        ParametersDelimiter.SERVER_PLAIN_TEXT_DELIMITER)
                        : serverEntryEFile.copyFrom(serverEntryIFileFactory.createEFile(absolutePath, charsetName,
                        EntriesDelimiter.PLAIN_TEXT_DELIMITER, entryFactory,
                        ParametersDelimiter.SERVER_PLAIN_TEXT_DELIMITER));
            }
        }
    }

    // TODO: simplify method, delete duplicates
    private void initFileRWFacade() {
        String variant = choiceBoxVariants.getValue();
        if (variant.equals(choiceBoxVariants.getItems().get(0))) {
            resourceEntryEFileReader = (resourceEntryEFileReader == null) ? new FileReader<>(resourceEntryEFile)
                    : resourceEntryEFileReader;
            resourceEntryEFileWriter = (resourceEntryEFileWriter == null)
                    ? new FileWriter<>(resourceEntryEFile, charsetName) : resourceEntryEFileWriter;
            resourceEntryFileRWFacade = (resourceEntryFileRWFacade == null)
                    ? new FileRWFacade<>(resourceEntryEFileReader, resourceEntryEFileWriter) : resourceEntryFileRWFacade;
        } else {
            if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                serverEntryEFileReader = (serverEntryEFileReader == null) ? new FileReader<>(serverEntryEFile)
                        : serverEntryEFileReader;
                serverEntryEFileWriter = (serverEntryEFileWriter == null)
                        ? new FileWriter<>(serverEntryEFile, charsetName) : serverEntryEFileWriter;
                serverEntryFileRWFacade = (serverEntryFileRWFacade == null)
                        ? new FileRWFacade<>(serverEntryEFileReader, serverEntryEFileWriter) : serverEntryFileRWFacade;
            }
        }
    }

    private void updateTableView() {
        clearTableView();
        String variant = choiceBoxVariants.getValue();
        switch (datasourceType) {
            case FILE: {
                if (variant.equals(choiceBoxVariants.getItems().get(0))) {
                    resourceEntryTableView.getItems().addAll(resourceEntryFileRWFacade.getFileReader().getEFile()
                            .getEntries());
                } else {
                    if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                        serverEntryTableView.getItems().addAll(serverEntryFileRWFacade.getFileReader().getEFile()
                                .getEntries());
                    }
                }
                break;
            }
            case DATABASE: {
                initRepositoryFacade();
                if (variant.equals(choiceBoxVariants.getItems().get(0))) {
                    resourceEntryTableView.getItems().addAll(resourceEntryRepositoryFacade.getService().getAll());
                } else {
                    if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                        serverEntryTableView.getItems().addAll(serverEntryRepositoryFacade.getService().getAll());
                    }
                }
                break;
            }
            case NONE: {
                break;
            }
            default: {
                showInformation("Error", "This datasource type not found!", Alert.AlertType.ERROR);
            }
        }
    }

    private void updateButtons() {
        boolean deleteButtonDisable = true;
        String variant = choiceBoxVariants.getValue();
        if (variant.equals(choiceBoxVariants.getItems().get(0))) {
            deleteButtonDisable = !(resourceEntryTableView.getItems().size() > 0);
            resourceEntryTableView.getSelectionModel().select(0);
        } else {
            if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                deleteButtonDisable = !(serverEntryTableView.getItems().size() > 0);
                serverEntryTableView.getSelectionModel().select(0);
            }
        }
        setDisablePropertiesForButtons(!isCollectionActivated, deleteButtonDisable,
                !isCollectionActivated);
    }

    private void setDisablePropertiesForButtons(boolean addButtonDisable, boolean deleteButtonDisable,
                                                boolean saveMenuButtonDisable) {
        addButton.setDisable(addButtonDisable);
        deleteButton.setDisable(deleteButtonDisable);
        saveMenuButton.setDisable(saveMenuButtonDisable);
    }

    private void initRepositoryFacade() {
        String variant = choiceBoxVariants.getValue();
        if (variant.equals(choiceBoxVariants.getItems().get(0))) {
            if (resourceEntryRepositoryFacade != null
                    && !resourceEntryRepositoryFacade.getEntityInstance().equals(EntityInstance.NETWORK_RESOURCE)) {
                resourceEntryRepositoryFacade.serviceReinitialize(EntityInstance.NETWORK_RESOURCE);
            } else {
                resourceEntryRepositoryFacade = new RepositoryFacade<>(EntityInstance.NETWORK_RESOURCE);
            }
        } else {
            if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                if (serverEntryRepositoryFacade != null
                        && !serverEntryRepositoryFacade.getEntityInstance().equals(EntityInstance.NETWORK_SERVER)) {
                    serverEntryRepositoryFacade.serviceReinitialize(EntityInstance.NETWORK_SERVER);
                } else {
                    serverEntryRepositoryFacade = new RepositoryFacade<>(EntityInstance.NETWORK_SERVER);
                }
            }
        }
    }

    @AllArgsConstructor
    private enum DatasourceType {

        NONE,
        DATABASE,
        FILE
    }
}
