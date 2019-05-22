package ru.wkn.analyzers.exceptions;

/**
 * The class {@code AnalyzerException} represents exception for custom specific conditions for analyzer types.
 *
 * @author Artem Pikalov
 */
public class AnalyzerException extends Exception {

    /**
     * @see Exception#Exception(String)
     */
    public AnalyzerException(String message) {
        super("Analyzer not found for: ".concat(message));
    }
}
