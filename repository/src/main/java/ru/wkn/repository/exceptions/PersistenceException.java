package ru.wkn.repository.exceptions;

/**
 * The class {@code PersistenceException} represents exception for custom specific conditions for ORM model.
 *
 * @author Artem Pikalov
 */
public class PersistenceException extends Exception {

    /**
     * @see Exception#Exception(String, Throwable)
     */
    public PersistenceException(String message, Throwable cause) {
        super("Persistence exception cause: ".concat(message), cause);
    }
}
