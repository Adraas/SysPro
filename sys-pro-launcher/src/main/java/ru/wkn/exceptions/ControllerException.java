package ru.wkn.exceptions;

public class ControllerException extends Exception {

    public ControllerException(String message) {
        super("Controller exception cause: ".concat(message));
    }
}
