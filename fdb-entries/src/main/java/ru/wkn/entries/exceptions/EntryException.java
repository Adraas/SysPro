package ru.wkn.entries.exceptions;

/**
 * The class {@code EntryException} represents exception for custom specific conditions for entry types.
 *
 * @author Artem Pikalov
 */
public class EntryException extends Exception {

    /**
     * @see Exception#Exception(String)
     */
    public EntryException(String message) {
        super("Entry exception cause: ".concat(message));
    }
}
