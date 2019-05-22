package ru.wkn.entries.exceptions;

/**
 * The class {@code EntryException} represents exception for custom specific conditions
 * for entries.
 */
public class EntryException extends Exception {

    public EntryException(String message) {
        super("Entry exception cause: ".concat(message));
    }
}
