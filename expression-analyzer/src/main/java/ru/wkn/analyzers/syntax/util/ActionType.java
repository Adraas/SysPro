package ru.wkn.analyzers.syntax.util;

/**
 * The enum {@code ActionType} contains actions for each line in the expression.
 *
 * @author Artem Pikalov
 */
public enum ActionType {

    /**
     * Variable declaration.
     */
    DECLARATION,

    /**
     * Variable initialization.
     */
    INITIALIZATION,

    /**
     * Method or variable invocation.
     */
    INVOCATION,

    /**
     * Variable declaration with initialization.
     */
    DECLARATION_WITH_INITIALIZATION
}
