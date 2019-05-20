package ru.wkn.analyzers.exceptions.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum  SemanticsErrorMessages {

    NUMBER_FORMAT_ERROR("number format is incorrect"),
    STRING_FORMAT_ERROR("string or char format is incorrect"),
    VARIABLE_NAME_ERROR("variable name is incorrect"),
    INITIALIZATION_ERROR("initialization is incorrect");

    private String errorMessage;
}
