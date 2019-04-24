package ru.wkn.entries;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum with typical parameter-delimiters.
 *
 * @author Artem Pikalov
 */
@AllArgsConstructor
public enum ParametersDelimiter {

    /**
     * Delimiter for the .csv files.
     */
    RESOURCE_CSV_DELIMITER(";;"),

    /**
     * Delimiter for the PlainText files.
     */
    SERVER_PLAIN_TEXT_DELIMITER("::");

    /**
     * Delimiter as {@code String} line.
     */
    @Getter
    private String parametersDelimiter;
}
