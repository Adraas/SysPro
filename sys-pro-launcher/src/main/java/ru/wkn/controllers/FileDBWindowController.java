package ru.wkn.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import ru.wkn.views.WindowRepository;

public class FileDBWindowController extends Controller {

    @FXML
    private ChoiceBox choiceBoxVariant;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableView tableView;

    public FileDBWindowController(WindowRepository windowRepository) {
        super(windowRepository);
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
}
