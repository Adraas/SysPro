package ru.wkn.controllers;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import lombok.AllArgsConstructor;
import ru.wkn.FileRWFacade;
import ru.wkn.entries.EntryFactory;
import ru.wkn.entries.IEntryFactory;
import ru.wkn.entries.ParametersDelimiter;
import ru.wkn.entries.exceptions.EntryException;
import ru.wkn.entries.resource.csv.ResourceEntry;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// TODO: complete this class
public class FileDBWindowController extends Controller {

    @FXML
    private ChoiceBox<String> choiceBoxVariants;
    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableView<ResourceEntry> resourceEntryTableView;
    @FXML
    private TableView<ServerEntry> serverEntryTableView;
    private FileChooser fileChooser;

    private DatasourceType datasourceType;
    private RepositoryFacade<ResourceEntry, Long> resourceEntryRepositoryFacade;
    private RepositoryFacade<ServerEntry, Long> serverEntryRepositoryFacade;

    private String charsetName = "windows-1251";
    private IFileFactory<ResourceEntry> resourceEntryIFileFactory;
    private IFileFactory<ServerEntry> serverEntryIFileFactory;
    private FileRWFacade<ResourceEntry> resourceEntryFileRWFacade;
    private FileRWFacade<ServerEntry> serverEntryFileRWFacade;

    private EFileReader<ResourceEntry> resourceEntryEFileReader;
    private EFileWriter<ServerEntry> serverEntryEFileWriter;

    private EFileReader<ServerEntry> serverEntryEFileReader;
    private EFileWriter<ResourceEntry> resourceEntryEFileWriter;

    private EFile<ResourceEntry> resourceEntryEFile;
    private EFile<ServerEntry> serverEntryEFile;

    private List<ResourceEntry> resourceEntries;
    private List<ServerEntry> serverEntries;

    public void initialize() {
        String[] variantItems = new String[]{"CSV: Network resource", "TXT: Network server"};
        ObservableList<String> variants =
                FXCollections.observableArrayList(variantItems);
        choiceBoxVariants.setItems(variants);
        choiceBoxVariants.setValue(variantItems[0]);

        // TODO: fix setting visible property for tables
        ChangeListener<String> changeListener = (observable, oldValue, newValue) -> {
            String variant = choiceBoxVariants.getValue();
            if (variant.equals(choiceBoxVariants.getItems().get(0))) {
                resourceEntryTableView.setVisible(true);
                serverEntryTableView.setVisible(false);
                setDisablePropertiesForButtons(false, true, true);
            }
            if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                resourceEntryTableView.setVisible(false);
                serverEntryTableView.setVisible(true);
                setDisablePropertiesForButtons(false, true, true);
            }
            updateTableView();
        };
        choiceBoxVariants.getSelectionModel().selectedItemProperty().addListener(changeListener);

        datasourceType = DatasourceType.NONE;
        resourceEntries = new ArrayList<>();
        serverEntries = new ArrayList<>();

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("Plain Text", "*.txt"));
    }

    @FXML
    private void clickOnNewList() {
        clearTableView();
        datasourceType = DatasourceType.NONE;
    }

    @FXML
    private void clickOnOpenFile() {
        Platform.runLater(() -> {
            openFile();
            initFileRWFacade();
            updateTableView();
        });
    }

    @FXML
    private void clickOnOpenDatabase() {
        Platform.runLater(() -> {
            datasourceType = DatasourceType.DATABASE;
            updateTableView();
        });
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

    private void clearTableView() {
        resourceEntryTableView.getItems().clear();
        serverEntryTableView.getColumns().clear();
    }

    private void openFile() {
        datasourceType = DatasourceType.FILE;
        File file = fileChooser.showOpenDialog(null);
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
        IEntryFactory entryFactory = new EntryFactory();
        String variant = choiceBoxVariants.getValue();
        try {
            if (variant.equals(choiceBoxVariants.getItems().get(0))) {
                resourceEntryEFile = resourceEntryIFileFactory.createEFile(file.getAbsolutePath(), charsetName,
                        EntriesDelimiter.CSV_DELIMITER, entryFactory, ParametersDelimiter.RESOURCE_CSV_DELIMITER);
                resourceEntries = resourceEntryEFile.getEntries();
            } else {
                if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                    serverEntryEFile = serverEntryIFileFactory.createEFile(file.getAbsolutePath(), charsetName,
                            EntriesDelimiter.PLAIN_TEXT_DELIMITER, entryFactory,
                            ParametersDelimiter.SERVER_PLAIN_TEXT_DELIMITER);
                    serverEntries = serverEntryEFile.getEntries();
                }
            }
        } catch (IOException | EntryException e) {
            e.printStackTrace();
            showInformation("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // TODO: simplify method, combine duplicates
    private void initFileRWFacade() {
        String variant = choiceBoxVariants.getValue();
        if (variant.equals(choiceBoxVariants.getItems().get(0))) {
            if (resourceEntryEFileReader == null) {
                resourceEntryEFileReader = new FileReader<>(resourceEntryEFile);
            }
            if (resourceEntryEFileWriter == null) {
                resourceEntryEFileWriter = new FileWriter<>(resourceEntryEFile, charsetName);
            }
            if (resourceEntryFileRWFacade == null) {
                resourceEntryFileRWFacade = new FileRWFacade<>(resourceEntryEFileReader, resourceEntryEFileWriter);
            }
        } else {
            if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                if (serverEntryEFileReader == null) {
                    serverEntryEFileReader = new FileReader<>(serverEntryEFile);
                }
                if (serverEntryEFileWriter == null) {
                    serverEntryEFileWriter = new FileWriter<>(serverEntryEFile, charsetName);
                }
                if (serverEntryFileRWFacade == null) {
                    serverEntryFileRWFacade = new FileRWFacade<>(serverEntryEFileReader, serverEntryEFileWriter);
                }
            }
        }
    }

    // TODO: simplify method, combine duplicates
    private void updateTableView() {
        clearTableView();
        String variant = choiceBoxVariants.getValue();
        if (variant.equals(choiceBoxVariants.getItems().get(0))) {
            switch (datasourceType) {
                case FILE: {
                    resourceEntryTableView.getItems().addAll(resourceEntries);
                    break;
                }
                case DATABASE: {
                    initRepositoryFacade();
                    resourceEntries = resourceEntryRepositoryFacade.getService().getAll();
                    resourceEntryTableView.getItems().addAll(resourceEntries);
                    break;
                }
                case NONE: {
                    break;
                }
                default: {
                    showInformation("Error", "This datasource type not found!", Alert.AlertType.ERROR);
                }
            }
        } else {
            if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                switch (datasourceType) {
                    case FILE: {
                        serverEntryTableView.getItems().addAll(serverEntries);
                        break;
                    }
                    case DATABASE: {
                        initRepositoryFacade();
                        serverEntries = serverEntryRepositoryFacade.getService().getAll();
                        serverEntryTableView.getItems().addAll(serverEntries);
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
        }
        updateButtons();
    }

    private void updateButtons() {
        boolean editAndDeleteButtonsVisible = true;
        String variant = choiceBoxVariants.getValue();
        if (variant.equals(choiceBoxVariants.getItems().get(0))) {
            editAndDeleteButtonsVisible = !(resourceEntryTableView.getItems().size() > 0);
            resourceEntryTableView.getSelectionModel().select(1);
        } else {
            if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                editAndDeleteButtonsVisible = !(serverEntryTableView.getItems().size() > 0);
                serverEntryTableView.getSelectionModel().select(1);
            }
        }
        setDisablePropertiesForButtons(false, editAndDeleteButtonsVisible, editAndDeleteButtonsVisible);
    }

    private void setDisablePropertiesForButtons(boolean addButtonDisable, boolean editButtonDisable,
                                                boolean deleteButtonDisable) {
        addButton.setDisable(addButtonDisable);
        editButton.setDisable(editButtonDisable);
        deleteButton.setDisable(deleteButtonDisable);
    }

    private void initRepositoryFacade() {
        String variant = choiceBoxVariants.getValue();
        if (variant.equals(choiceBoxVariants.getItems().get(1))) {
            if (resourceEntryRepositoryFacade != null
                    && !resourceEntryRepositoryFacade.getEntityInstance().equals(EntityInstance.NETWORK_RESOURCE)) {
                resourceEntryRepositoryFacade.serviceReinitialize(EntityInstance.NETWORK_RESOURCE);
            } else {
                resourceEntryRepositoryFacade = new RepositoryFacade<>(EntityInstance.NETWORK_RESOURCE);
            }
        } else {
            if (variant.equals(choiceBoxVariants.getItems().get(2))) {
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
