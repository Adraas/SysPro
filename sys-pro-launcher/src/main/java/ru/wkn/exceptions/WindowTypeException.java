package ru.wkn.exceptions;

public class WindowTypeException extends Exception {

    public WindowTypeException(String message) {
        super("Window type exception cause: ".concat(message));
    }
}
