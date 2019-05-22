package ru.wkn.analyzers.exceptions.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum {@code SemanticsErrorMessages} contains error postfix messages for {@code SemanticsException}.
 *
 * @author Artem Pikalov
 */
@AllArgsConstructor
@Getter
public enum  SemanticsErrorMessages {

    /**
     * Number format error.
     */
    NUMBER_FORMAT_ERROR("number format is incorrect"),

    /**
     * String or char format error.
     */
    STRING_FORMAT_ERROR("string or char format is incorrect"),

    /**
     * Variable name error.
     */
    VARIABLE_NAME_ERROR("variable name is incorrect"),

    /**
     * Initialization error.
     */
    INITIALIZATION_ERROR("initialization is incorrect");

    /**
     * Error message as {@code String } value.
     */
    private String errorMessage;
}
