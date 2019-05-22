package ru.wkn.analyzers.syntax.semantics;

/**
 * The interface {@code ICSharpeSemanticsAnalyzer} represents abstract semantics analyzer for C# expressions.
 *
 * @author Artem Pikalov
 */
public interface ICSharpeSemanticsAnalyzer extends ISemanticsAnalyzer {

    /**
     * The method for the decimal value checking.
     *
     * @param decimalValueAsString the decimal value as {@code String} value
     * @return {@code true} if the value is in the interval, else - {@code false}
     */
    boolean isDecimalValueCorrect(String decimalValueAsString);

    /**
     * The method for the sbyte value checking.
     *
     * @param sbyteValueAsString the sbyte value as {@code String} value
     * @return {@code true} if the value is in the interval, else - {@code false}
     */
    boolean isSbyteValueCorrect(String sbyteValueAsString);

    /**
     * The method for the ushort value checking.
     *
     * @param ushortValueAsString the ushort value as {@code String} value
     * @return {@code true} if the value is in the interval, else - {@code false}
     */
    boolean isUshortValueCorrect(String ushortValueAsString);

    /**
     * The method for the uinteger value checking.
     *
     * @param uintegerValueAsString the uinteger value as {@code String} value
     * @return {@code true} if the value is in the interval, else - {@code false}
     */
    boolean isUintegerValueCorrect(String uintegerValueAsString);

    /**
     * The method for the ulong value checking.
     *
     * @param ulongValueAsString the ulong value as {@code String} value
     * @return {@code true} if the value is in the interval, else - {@code false}
     */
    boolean isUlongValueCorrect(String ulongValueAsString);
}
