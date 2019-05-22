package ru.wkn.analyzers.exceptions.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum {@code SyntaxErrorMessages} contains error postfix messages for {@code ExpressionException}.
 *
 * @author Artem Pikalov
 */
@AllArgsConstructor
@Getter
public enum SyntaxErrorMessages {

    /**
     * Condition error.
     */
    CONDITION_ERROR("cycle's condition is incorrect"),

    /**
     * Cycle's body error.
     */
    LINE_ERROR("cycle's body is incorrect after line "),

    /**
     * Action type error.
     */
    ACTION_TYPE_ERROR("unknown action type after line "),

    /**
     * Variable declaration error.
     */
    VARIABLE_DECLARATION_ERROR("variable declaration is incorrect after line "),

    /**
     * Variable initialization error.
     */
    INITIALIZATION_ERROR("variable initialization is incorrect after line ");

    /**
     * Error message as {@code String } value.
     */
    private String errorMessage;
}
