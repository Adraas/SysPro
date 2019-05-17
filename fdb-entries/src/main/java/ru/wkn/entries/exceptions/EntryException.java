package ru.wkn.entries.exceptions;

public class EntryException extends Exception {

    public EntryException(String message) {
        super("Entry exception cause: ".concat(message));
    }
}
