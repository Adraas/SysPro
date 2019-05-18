package ru.wkn.analyzers.syntax.semantics;

public interface ISemanticsAnalyzer {

    boolean isByteValueCorrect(String byteValueAsString);

    boolean isShortValueCorrect(String shortValueAsString);

    boolean isIntegerValueCorrect(String integerValueAsString);

    boolean isLongValueCorrect(String longValueAsString);

    boolean isFloatValueCorrect(String floatValueAsString);

    boolean isDoubleValueCorrect(String doubleValueAsString);

    boolean isDecimalValueCorrect(String decimalValueAsString);

    boolean isSbyteValueCorrect(String sbyteValueAsString);

    boolean isUshortValueCorrect(String ushortValueAsString);

    boolean isUintegerValueCorrect(String uintegerValueAsString);

    boolean isUlongValueCorrect(String ulongValueAsString);

    boolean isCharacterValueCorrect(String characterValueAsString);

    boolean isBooleanValueCorrect(String booleanValueAsString);

    boolean isStringValueCorrect(String stringValueAsString);

    boolean isVariableNameCorrect(String variableAsString);
}