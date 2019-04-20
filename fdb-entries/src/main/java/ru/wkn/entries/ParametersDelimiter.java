package ru.wkn.entries;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ParametersDelimiter {

    RESOURCE_CSV_DELIMITER(";;"), SERVER_PLAIN_TEXT_DELIMITER("::");

    @Getter
    private String parametersDelimiter;
}
