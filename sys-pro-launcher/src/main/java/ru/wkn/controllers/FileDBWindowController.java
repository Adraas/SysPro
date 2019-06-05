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
import ru.wkn.entries.IEntry;
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
import ru.wkn.util.ObservableType;
import ru.wkn.util.Observer;
import ru.wkn.util.OperationType;
import ru.wkn.views.WindowType;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class FileDBWindowController extends Controller implements Observer<IEntry> {

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
    private RepositoryFacade<ResourceEntry, Long> resourceERepositoryFacade;
    private RepositoryFacade<ServerEntry, Long> serverERepositoryFacade;

    private String charsetName = "windows-1251";
    private IFileFactory<ResourceEntry> resourceEFileFactory;
    private IFileFactory<ServerEntry> serverEFileFactory;
    private FileRWFacade<ResourceEntry> resourceEFileRWFacade;
    private FileRWFacade<ServerEntry> serverEFileRWFacade;

    private IEntryFactory entryFactory;
    private EFile<ResourceEntry> resourceEFile;
    private EFile<ServerEntry> serverEFile;

    private EFileReader<ResourceEntry> resourceEFileReader;
    private EFileWriter<ResourceEntry> resourceEFileWriter;

    private EFileReader<ServerEntry> serverEFileReader;
    private EFileWriter<ServerEntry> serverEFileWriter;

    private boolean isCollectionActivated = false;
    private boolean isConnectedToDatabaseSuccess = false;

    public void initialize() {
        getObservablesRepository().getObservable(ObservableType.OBERVABLE_INTERWINDOW_REPOSITORY)
                .addObserver(OperationType.WAITING_VALUE, this);
        initTablesCellValuesFactories();
        initColumnsEditEventHandlers();
        initChoiceBox();
        initChoiceBoxEventHandlers();

        datasourceType = DatasourceType.FILE;

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
        ObservableList<String> variants = FXCollections.observableArrayList(variantItems);
        choiceBoxVariants.setItems(variants);
        choiceBoxVariants.setValue(variantItems[0]);
    }

    private void initChoiceBoxEventHandlers() {
        ChangeListener<String> changeListener = (observable, oldValue, newValue) -> {
            String variant = choiceBoxVariants.getValue();
            if (variant.equals(choiceBoxVariants.getItems().get(0))) {
                resourceEntryTableView.setVisible(true);
                serverEntryTableView.setVisible(false);
            } else {
                if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                    resourceEntryTableView.setVisible(false);
                    serverEntryTableView.setVisible(true);
                }
            }
            updateButtons();
        };
        choiceBoxVariants.getSelectionModel().selectedItemProperty().addListener(changeListener);
    }

    @FXML
    private void clickOnNewFileTable() {
        Platform.runLater(() -> {
            datasourceType = DatasourceType.FILE;
            String variant = choiceBoxVariants.getValue();
            if (!isCollectionActivated) {
                try {
                    String fileExtensionWithoutFullName;
                    if (variant.equals(choiceBoxVariants.getItems().get(0))) {
                        fileExtensionWithoutFullName = ".csv";
                        initEFileFactory(fileExtensionWithoutFullName);
                        initEntryFile(fileExtensionWithoutFullName);
                    } else {
                        if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                            fileExtensionWithoutFullName = ".txt";
                            initEFileFactory(fileExtensionWithoutFullName);
                            initEntryFile(fileExtensionWithoutFullName);
                        }
                    }
                } catch (IOException | EntryException e) {
                    e.printStackTrace();
                    showInformation(e.getClass().getSimpleName(), e.getMessage(), Alert.AlertType.ERROR);
                }
                initFileRWFacade();
                isCollectionActivated = true;
            }
            clearFileWithoutSaving(variant);
            updateTableView();
            updateButtons();
        });
    }

    @FXML
    private void clickOnNewDatabaseTable() {
        Platform.runLater(() -> {
            datasourceType = DatasourceType.DATABASE;
            String variant = choiceBoxVariants.getValue();
            openDatabase();
            try {
                if (variant.equals(choiceBoxVariants.getItems().get(0))) {
                    resourceERepositoryFacade.getService().deleteAll();
                } else {
                    if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                        serverERepositoryFacade.getService().deleteAll();
                    }
                }
            } catch (PersistenceException e) {
                e.printStackTrace();
                showInformation(e.getClass().getSimpleName(), e.getMessage(), Alert.AlertType.ERROR);
            }
            if (isConnectedToDatabaseSuccess) {
                updateTableView();
                updateButtons();
            }
        });
    }

    @FXML
    private void clickOnOpenFile() {
        Platform.runLater(() -> {
            isCollectionActivated = true;
            datasourceType = DatasourceType.FILE;
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
            openDatabase();
            if (isConnectedToDatabaseSuccess) {
                updateTableView();
                updateButtons();
            }
        });
    }

    @FXML
    private void clickOnSaveToFile() {
        Platform.runLater(() -> {
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                String variant = choiceBoxVariants.getValue();
                try {
                    if (variant.equals(choiceBoxVariants.getItems().get(0))) {
                        resourceEFileRWFacade.getFileWriter().saveFile(file.getAbsolutePath());
                    } else {
                        if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                            serverEFileRWFacade.getFileWriter().saveFile(file.getAbsolutePath());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    showInformation(e.getClass().getSimpleName(), e.getMessage(), Alert.AlertType.ERROR);
                }
            }
            datasourceType = DatasourceType.FILE;
        });
    }

    @FXML
    private void clickOnSaveToDatabase() {
        Platform.runLater(() -> {
            String variant = choiceBoxVariants.getValue();
            try {
                if (variant.equals(choiceBoxVariants.getItems().get(0))) {
                    List<ResourceEntry> entries = resourceEntryTableView.getItems();
                    resourceERepositoryFacade.getService().deleteAll();
                    resourceERepositoryFacade.getService().create(entries);
                } else {
                    if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                        List<ServerEntry> entries = serverEntryTableView.getItems();
                        serverERepositoryFacade.getService().deleteAll();
                        serverERepositoryFacade.getService().create(entries);
                    }
                }
            } catch (PersistenceException e) {
                e.printStackTrace();
                showInformation(e.getClass().getSimpleName(), e.getMessage(), Alert.AlertType.ERROR);
            }
            datasourceType = DatasourceType.FILE;
        });
    }

    @FXML
    private void clickOnAdd() {
        Platform.runLater(() -> {
            String variant = choiceBoxVariants.getValue();
            if (variant.equals(choiceBoxVariants.getItems().get(0))) {
                openNewWindow(WindowType.FILE_DB_WINDOW, WindowType.NETWORK_RESOURCE_WINDOW);
            } else {
                if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                    openNewWindow(WindowType.FILE_DB_WINDOW, WindowType.SERVER_INFORMATION_WINDOW);
                }
            }
        });
    }

    @FXML
    private void clickOnDelete() {
        Platform.runLater(() -> {
            String variant = choiceBoxVariants.getValue();
            switch (datasourceType) {
                case FILE: {
                    if (variant.equals(choiceBoxVariants.getItems().get(0))) {
                        resourceEFileRWFacade.getFileWriter().delete(resourceEntryTableView.getSelectionModel()
                                .getSelectedItem());
                    } else {
                        if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                            serverEFileRWFacade.getFileWriter().delete(serverEntryTableView.getSelectionModel()
                                    .getSelectedItem());
                        }
                    }
                    break;
                }
                case DATABASE: {
                    try {
                        if (variant.equals(choiceBoxVariants.getItems().get(0))) {
                            resourceERepositoryFacade.getService().delete(resourceEntryTableView.getSelectionModel()
                                    .getSelectedItem());
                        } else {
                            if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                                serverERepositoryFacade.getService().delete(serverEntryTableView.getSelectionModel()
                                        .getSelectedItem());
                            }
                        }
                    } catch (PersistenceException e) {
                        e.printStackTrace();
                    }
                }
            }
            updateTableView();
            updateButtons();
        });
    }

    @Override
    public void update(IEntry dataObject) {
        Platform.runLater(() -> {
            String variant = choiceBoxVariants.getValue();
            switch (datasourceType) {
                case FILE: {
                    if (variant.equals(choiceBoxVariants.getItems().get(0))) {
                        resourceEFileRWFacade.getFileWriter().append((ResourceEntry) dataObject);
                    } else {
                        if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                            serverEFileRWFacade.getFileWriter().append((ServerEntry) dataObject);
                        }
                    }
                    break;
                }
                case DATABASE: {
                    try {
                        if (variant.equals(choiceBoxVariants.getItems().get(0))) {
                            resourceERepositoryFacade.getService().create((ResourceEntry) dataObject);
                        } else {
                            if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                                serverERepositoryFacade.getService().create((ServerEntry) dataObject);
                            }
                        }
                    } catch (PersistenceException e) {
                        e.printStackTrace();
                        showInformation(e.getClass().getSimpleName(), e.getMessage(), Alert.AlertType.ERROR);
                    }
                    break;
                }
                default: {
                    showInformation("Datasource error", "This datasource type not found!", Alert.AlertType.ERROR);
                }
            }
            updateTableView();
            updateButtons();
        });
    }

    private void clearFileWithoutSaving(String variant) {
        if (variant.equals(choiceBoxVariants.getItems().get(0))) {
            resourceEFileRWFacade.getFileWriter().deleteSome(0, resourceEFileRWFacade.getFileReader()
                    .getEFile().getEntries().size() - 1);
        } else {
            if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                serverEFileRWFacade.getFileWriter().deleteSome(0, serverEFileRWFacade.getFileReader()
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
            String filename = file.getName();
            initEFileFactory(filename);
            try {
                initEntryFile(file.getAbsolutePath());
            } catch (IOException | EntryException e) {
                e.printStackTrace();
                showInformation(e.getClass().getSimpleName(), e.getMessage(), Alert.AlertType.ERROR);
            }
        }
        return file;
    }

    private void initEFileFactory(String filename) {
        if (filename.endsWith("csv")) {
            choiceBoxVariants.setValue(choiceBoxVariants.getItems().get(0));
            if (resourceEFileFactory == null) {
                resourceEFileFactory = new FileFactory<>();
            }
        } else {
            if (filename.endsWith("txt")) {
                choiceBoxVariants.setValue(choiceBoxVariants.getItems().get(1));
                if (serverEFileFactory == null) {
                    serverEFileFactory = new FileFactory<>();
                }
            }
        }
    }

    private void initEntryFile(String absolutePath) throws IOException, EntryException {
        entryFactory = (entryFactory == null) ? new EntryFactory() : entryFactory;
        String variant = choiceBoxVariants.getValue();
        if (variant.equals(choiceBoxVariants.getItems().get(0))) {
            resourceEFile = (resourceEFile == null)
                    ? resourceEFileFactory.createEFile(absolutePath, charsetName,
                    EntriesDelimiter.CSV_DELIMITER, entryFactory, ParametersDelimiter.RESOURCE_CSV_DELIMITER)
                    : resourceEFile.copyFrom(resourceEFileFactory.createEFile(absolutePath, charsetName,
                    EntriesDelimiter.CSV_DELIMITER, entryFactory, ParametersDelimiter.RESOURCE_CSV_DELIMITER));
        } else {
            if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                serverEFile = (serverEFile == null)
                        ? serverEFileFactory.createEFile(absolutePath, charsetName,
                        EntriesDelimiter.PLAIN_TEXT_DELIMITER, entryFactory,
                        ParametersDelimiter.SERVER_PLAIN_TEXT_DELIMITER)
                        : serverEFile.copyFrom(serverEFileFactory.createEFile(absolutePath, charsetName,
                        EntriesDelimiter.PLAIN_TEXT_DELIMITER, entryFactory,
                        ParametersDelimiter.SERVER_PLAIN_TEXT_DELIMITER));
            }
        }
    }

    // TODO: simplify method, delete duplicates
    private void initFileRWFacade() {
        String variant = choiceBoxVariants.getValue();
        if (variant.equals(choiceBoxVariants.getItems().get(0))) {
            resourceEFileReader = (resourceEFileReader == null) ? new FileReader<>(resourceEFile)
                    : resourceEFileReader;
            resourceEFileWriter = (resourceEFileWriter == null)
                    ? new FileWriter<>(resourceEFile, charsetName) : resourceEFileWriter;
            resourceEFileRWFacade = (resourceEFileRWFacade == null)
                    ? new FileRWFacade<>(resourceEFileReader, resourceEFileWriter) : resourceEFileRWFacade;
        } else {
            if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                serverEFileReader = (serverEFileReader == null) ? new FileReader<>(serverEFile)
                        : serverEFileReader;
                serverEFileWriter = (serverEFileWriter == null)
                        ? new FileWriter<>(serverEFile, charsetName) : serverEFileWriter;
                serverEFileRWFacade = (serverEFileRWFacade == null)
                        ? new FileRWFacade<>(serverEFileReader, serverEFileWriter) : serverEFileRWFacade;
            }
        }
    }

    private void openDatabase() {
        datasourceType = DatasourceType.DATABASE;
        if (!isConnectedToDatabaseSuccess) {
            initRepositoryFacade();
        }
        isCollectionActivated = isConnectedToDatabaseSuccess;
    }

    private void initRepositoryFacade() {
        String variant = choiceBoxVariants.getValue();
        if (variant.equals(choiceBoxVariants.getItems().get(0))) {
            if (resourceERepositoryFacade != null
                    && !resourceERepositoryFacade.getEntityInstance().equals(EntityInstance.NETWORK_RESOURCE)) {
                resourceERepositoryFacade.serviceReinitialize(EntityInstance.NETWORK_RESOURCE);
            } else {
                resourceERepositoryFacade = new RepositoryFacade<>(EntityInstance.NETWORK_RESOURCE);
            }
            isConnectedToDatabaseSuccess = resourceERepositoryFacade != null
                    && resourceERepositoryFacade.getService() != null;
        } else {
            if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                if (serverERepositoryFacade != null
                        && !serverERepositoryFacade.getEntityInstance().equals(EntityInstance.NETWORK_SERVER)) {
                    serverERepositoryFacade.serviceReinitialize(EntityInstance.NETWORK_SERVER);
                } else {
                    serverERepositoryFacade = new RepositoryFacade<>(EntityInstance.NETWORK_SERVER);
                }
                isConnectedToDatabaseSuccess = serverERepositoryFacade != null
                        && serverERepositoryFacade.getService() != null;
            }
        }
    }

    private void updateTableView() {
        Platform.runLater(() -> {
            clearTableView();
            String variant = choiceBoxVariants.getValue();
            switch (datasourceType) {
                case FILE: {
                    if (variant.equals(choiceBoxVariants.getItems().get(0))) {
                        resourceEntryTableView.getItems().addAll(resourceEFileRWFacade.getFileReader().getEFile()
                                .getEntries());
                    } else {
                        if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                            serverEntryTableView.getItems().addAll(serverEFileRWFacade.getFileReader().getEFile()
                                    .getEntries());
                        }
                    }
                    break;
                }
                case DATABASE: {
                    if (variant.equals(choiceBoxVariants.getItems().get(0))) {
                        resourceEntryTableView.getItems().addAll(resourceERepositoryFacade.getService().getAll());
                    } else {
                        if (variant.equals(choiceBoxVariants.getItems().get(1))) {
                            serverEntryTableView.getItems().addAll(serverERepositoryFacade.getService().getAll());
                        }
                    }
                    break;
                }
                default: {
                    showInformation("Datasource error", "This datasource type not found!", Alert.AlertType.ERROR);
                }
            }
        });
    }

    private void updateButtons() {
        Platform.runLater(() -> {
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
            setDisablePropertiesForButtons(!isCollectionActivated, deleteButtonDisable, !isCollectionActivated);
        });
    }

    private void setDisablePropertiesForButtons(boolean addButtonDisable, boolean deleteButtonDisable,
                                                boolean saveMenuButtonDisable) {
        addButton.setDisable(addButtonDisable);
        deleteButton.setDisable(deleteButtonDisable);
        saveMenuButton.setDisable(saveMenuButtonDisable);
    }

    @AllArgsConstructor
    private enum DatasourceType {

        DATABASE,
        FILE
    }
}
