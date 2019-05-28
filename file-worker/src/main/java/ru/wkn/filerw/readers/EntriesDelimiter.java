package ru.wkn.filerw.readers;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum {@code EntriesDelimiter} contains delimiters between entries in a file.
 *
 * @author Artem Pikalov
 */
@AllArgsConstructor
public enum EntriesDelimiter {

    /**
     * Delimiter for a .csv file.
     */
    CSV_DELIMITER(";\r\n"),

    /**
     * Delimiter for a PlainText file.
     */
    PLAIN_TEXT_DELIMITER(":\r\n"),

    /**
     * Delimiter for a .bin file.
     */
    BIN_DELIMITER("#\r\n");

    /**
     * Delimiter as {@code String} value.
     */
    @Getter
    private String entryDelimiter;
}
