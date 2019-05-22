package ru.wkn.filerw.readers;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum {@code EntriesDelimiter} represents delimiters between entries in a file.
 *
 * @author Artem Pikalov
 */
@AllArgsConstructor
public enum EntriesDelimiter {

    /**
     * Delimiter for a .csv file.
     */
    CSV_DELIMITER(";\n"),

    /**
     * Delimiter for a PlainText file.
     */
    PLAIN_TEXT_DELIMITER(":\n"),

    /**
     * Delimiter for a .bin file.
     */
    BIN_DELIMITER("#\n");

    /**
     * Delimiter as {@code String} value.
     */
    @Getter
    private String entryDelimiter;
}
