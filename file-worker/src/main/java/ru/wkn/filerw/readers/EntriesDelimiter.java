package ru.wkn.filerw.readers;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EntriesDelimiter {

    CSV_DELIMITER(";\n"), PLAIN_TEXT_DELIMITER(":\n");

    @Getter
    private String entryDelimiter;
}
