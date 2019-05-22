package ru.wkn.analyzers.exceptions;

/**
 * The class {@code LanguageException} represents exception for custom specific conditions for language types.
 *
 * @author Artem Pikalov
 */
public class LanguageException extends Exception {

    /**
     * @see Exception#Exception(String)
     */
    public LanguageException(String message) {
        super("Language exception cause: ".concat(message));
    }
}
