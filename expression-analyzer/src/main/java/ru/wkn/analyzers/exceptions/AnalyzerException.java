package ru.wkn.analyzers.exceptions;

public class AnalyzerException extends Exception {

    public AnalyzerException(String message) {
        super("Analyzer not found for: ".concat(message));
    }
}
