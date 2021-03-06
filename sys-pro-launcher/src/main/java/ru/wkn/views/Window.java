package ru.wkn.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Window implements IWindow {

    private String resource;
    private String title;
    private int width;
    private int height;
    private Stage stage = new Stage();
    private boolean isInitialized = false;

    public Window(String resource, String title, int width, int height) {
        this.resource = resource;
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public Window(String resource, String title, int width, int height, Stage stage) {
        this.resource = resource;
        this.title = title;
        this.width = width;
        this.height = height;
        this.stage = stage;
    }

    @Override
    public void show() throws IOException {
        if (!isInitialized) {
            init();
            isInitialized = true;
        }
        stage.show();
    }

    @Override
    public void hide() {
        stage.hide();
    }

    @Override
    public void close() {
        hide();
        isInitialized = false;
    }

    private void init() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(resource));
        stage.setScene(new Scene(anchorPane, width, height));
        stage.setResizable(false);
        stage.setTitle(title);
    }

    @Override
    protected void finalize() {
        close();
        stage = null;
    }
}
