package ru.wkn.analyzers.exceptions;

/**
 * The class {@code CompilationException} represents exception for a pre-compilation state warning.
 *
 * @author Artem Pikalov
 */
public class CompilationException extends Exception {

    /**
     * @see Exception#Exception(String)
     */
    public CompilationException(String message) {
        super("Pre-compilation state warning: ".concat(message));
    }
}
