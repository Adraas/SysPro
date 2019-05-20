package ru.wkn.analyzers.exceptions;

public class SemanticsException extends Exception {

    public SemanticsException(String message, int line) {
        super("Semantics exception cause: ".concat(message).concat(" after line ").concat(String.valueOf(line)));
    }
}
