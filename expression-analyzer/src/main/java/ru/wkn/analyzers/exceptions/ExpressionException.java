package ru.wkn.analyzers.exceptions;

public class ExpressionException extends Exception {

    public ExpressionException(String message) {
        super("Expression wrong: ".concat(message));
    }
}
