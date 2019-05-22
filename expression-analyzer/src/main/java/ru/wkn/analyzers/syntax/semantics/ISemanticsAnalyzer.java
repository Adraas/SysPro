package ru.wkn.analyzers.syntax.semantics;

/**
 * The interface {@code ISemanticsAnalyzer} represents abstract semantics analyzer for language's expressions.
 *
 * @author Artem Pikalov
 */
public interface ISemanticsAnalyzer {

    /**
     * The method for the {@code byte} value checking.
     *
     * @param byteValueAsString the {@code byte} value as {@code String} value
     * @return {@code true} if the value is in the interval, else - {@code false}
     */
    boolean isByteValueCorrect(String byteValueAsString);

    /**
     * The method for the {@code short} value checking.
     *
     * @param shortValueAsString the {@code short} value as {@code String} value
     * @return {@code true} if the value is in the interval, else - {@code false}
     */
    boolean isShortValueCorrect(String shortValueAsString);

    /**
     * The method for the {@code int} value checking.
     *
     * @param integerValueAsString the {@code int} value as {@code String} value
     * @return {@code true} if the value is in the interval, else - {@code false}
     */
    boolean isIntegerValueCorrect(String integerValueAsString);

    /**
     * The method for the {@code long} value checking.
     *
     * @param longValueAsString the {@code long} value as {@code String} value
     * @return {@code true} if the value is in the interval, else - {@code false}
     */
    boolean isLongValueCorrect(String longValueAsString);

    /**
     * The method for the {@code float} value checking.
     *
     * @param floatValueAsString the {@code float} value as {@code String} value
     * @return {@code true} if the value is in the interval, else - {@code false}
     */
    boolean isFloatValueCorrect(String floatValueAsString);

    /**
     * The method for the {@code double} value checking.
     *
     * @param doubleValueAsString the {@code double} value as {@code String} value
     * @return {@code true} if the value is in the interval, else - {@code false}
     */
    boolean isDoubleValueCorrect(String doubleValueAsString);

    /**
     * The method for the {@code char} value checking.
     *
     * @param characterValueAsString the {@code char} value as {@code String} value
     * @return {@code true} if the value is correct as character, else - {@code false}
     */
    boolean isCharacterValueCorrect(String characterValueAsString);

    /**
     * The method for the {@code boolean} value checking.
     *
     * @param booleanValueAsString the {@code boolean} value as {@code String} value
     * @return {@code true} if the value is "true" or "false", else - {@code false}
     */
    boolean isBooleanValueCorrect(String booleanValueAsString);

    /**
     * The method for the {@code String} value checking.
     *
     * @param stringValueAsString the {@code String} value
     * @return {@code true} if the value is correct as {@code String}, else - {@code false}
     */
    boolean isStringValueCorrect(String stringValueAsString);

    /**
     * The method for the variable name checking.
     *
     * @param variableAsString the variable name as {@code String} value
     * @return {@code true} if the variable name is correct, else - {@code false}
     */
    boolean isVariableNameCorrect(String variableAsString);
}
