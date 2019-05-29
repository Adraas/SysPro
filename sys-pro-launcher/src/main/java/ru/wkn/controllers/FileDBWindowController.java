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
import javafx.scene.control.cell.PropertyValueFactory;
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
import ru.wkn.repository.exceptions.PersistenceException;

import java.io.File;
import java.io.IOException;

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

    private EFile<ResourceEntry> resourceEntryEFile;
    private EFile<ServerEntry> serverEntryEFile;

    private EFileReader<ResourceEntry> resourceEntryEFileReader;
    private EFileWriter<ResourceEntry> resourceEntryEFileWriter;

    private EFileReader<ServerEntry> serverEntryEFileReader;
    private EFileWriter<ServerEntry> serverEntryEFileWriter;

    public void initialize() {
        String[] variantItems = new String[]{"CSV: Network resource", "TXT: Network server"};
        ObservableList<String> variants =
                FXCollections.observableArrayList(variantItems);
        choiceBoxVariants.setItems(variants);
        choiceBoxVariants.setValue(variantItems[0]);

        resourceEntryTableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("url"));
        resourceEntryTableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("accessMode"));
        resourceEntryTableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("accessDate"));

        serverEntryTableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("url"));
        serverEntryTableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("port"));
        serverEntryTableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("protocolType"));

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

        datasourceType = DatasourceType.NONE;

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("Plain Text", "*.txt"));
    }

    @FXML
    private void clickOnNewList() {
        String variant = choiceBoxVariants.getValue();
        switch (datasourceType) {
            case FILE: {
                if (variant.equals(choiceBoxVariants.getItems().get(0))) {
                    resourceEntryFileRWFacade.getFileWriter().deleteSome(0, resourceEntryFileRWFacade.getFileReader()
                            .getEFile().getEntries().size() - 1);
                } else {
                    if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                        serverEntryFileRWFacade.getFileWriter().deleteSome(0, serverEntryFileRWFacade.getFileReader()
                                .getEFile().getEntries().size() - 1);
                    }
                }
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
            IEntryFactory entryFactory = new EntryFactory();
            String variant = choiceBoxVariants.getValue();
            try {
                if (variant.equals(choiceBoxVariants.getItems().get(0))) {
                    resourceEntryEFile = resourceEntryIFileFactory.createEFile(file.getAbsolutePath(), charsetName,
                            EntriesDelimiter.CSV_DELIMITER, entryFactory, ParametersDelimiter.RESOURCE_CSV_DELIMITER);
                } else {
                    if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                        serverEntryEFile = serverEntryIFileFactory.createEFile(file.getAbsolutePath(), charsetName,
                                EntriesDelimiter.PLAIN_TEXT_DELIMITER, entryFactory,
                                ParametersDelimiter.SERVER_PLAIN_TEXT_DELIMITER);
                    }
                }
            } catch (IOException | EntryException e) {
                e.printStackTrace();
                showInformation("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
        return file;
    }

    private void initFileRWFacade() {
        resourceEntryEFileReader = (resourceEntryEFileReader == null) ? new FileReader<>(resourceEntryEFile)
                : resourceEntryEFileReader;
        resourceEntryEFileWriter = (resourceEntryEFileWriter == null)
                ? new FileWriter<>(resourceEntryEFile, charsetName) : resourceEntryEFileWriter;
        serverEntryEFileReader = (serverEntryEFileReader == null) ? new FileReader<>(serverEntryEFile)
                : serverEntryEFileReader;
        serverEntryEFileWriter = (serverEntryEFileWriter == null)
                ? new FileWriter<>(serverEntryEFile, charsetName) : serverEntryEFileWriter;
        String variant = choiceBoxVariants.getValue();
        if (variant.equals(choiceBoxVariants.getItems().get(0))) {
            resourceEntryFileRWFacade = (resourceEntryFileRWFacade == null)
                    ? new FileRWFacade<>(resourceEntryEFileReader, resourceEntryEFileWriter) : resourceEntryFileRWFacade;
        } else {
            if (variant.equals(choiceBoxVariants.getItems().get(1))) {
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
        boolean editAndDeleteButtonsVisible = true;
        String variant = choiceBoxVariants.getValue();
        if (variant.equals(choiceBoxVariants.getItems().get(0))) {
            editAndDeleteButtonsVisible = !(resourceEntryTableView.getItems().size() > 0);
            resourceEntryTableView.getSelectionModel().select(0);
        } else {
            if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                editAndDeleteButtonsVisible = !(serverEntryTableView.getItems().size() > 0);
                serverEntryTableView.getSelectionModel().select(0);
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
