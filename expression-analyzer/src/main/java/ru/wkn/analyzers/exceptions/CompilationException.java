package ru.wkn.analyzers.exceptions;

public class CompilationException extends Exception {

    public CompilationException(String message, Throwable cause) {
        super("Compilation exception cause: ".concat(message), cause);
    }
}
