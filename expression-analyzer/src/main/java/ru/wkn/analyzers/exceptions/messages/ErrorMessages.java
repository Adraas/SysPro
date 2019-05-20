package ru.wkn.analyzers.exceptions.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessages {

    CONDITION_ERROR("cycle's condition is incorrect"),
    LINE_ERROR("cycle's body is incorrect after line "),
    ACTION_TYPE_ERROR("unknown action type after line "),
    DATA_TYPE_ERROR("unknown data type after line "),
    VARIABLE_DECLARATION_ERROR("variable declaration is incorrect after line "),
    INITIALIZATION_ERROR("variable initialization is incorrect after line ");

    private String errorMessage;
}
