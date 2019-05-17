package ru.wkn.analyzers.exceptions;

public class LanguageException extends Exception {

    public LanguageException(String message) {
        super("Language exception cause: ".concat(message));
    }
}
