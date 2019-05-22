package ru.wkn.analyzers.exceptions;

/**
 * The class {@code SemanticsException} represents exception for custom specific conditions for semantics checking.
 *
 * @author Artem Pikalov
 */
public class SemanticsException extends Exception {

    /**
     * @see Exception#Exception(String)
     */
    public SemanticsException(String message, int line) {
        super("Semantics exception cause: ".concat(message).concat(" after line ").concat(String.valueOf(line)));
    }
}
