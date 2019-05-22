package ru.wkn.analyzers.exceptions;

/**
 * The class {@code ExpressionException} represents exception for custom specific conditions for expression types.
 *
 * @author Artem Pikalov
 */
public class ExpressionException extends Exception {

    /**
     * @see Exception#Exception(String)
     */
    public ExpressionException(String message) {
        super("Expression wrong: ".concat(message));
    }
}
