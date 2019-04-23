package ru.wkn.analyzers.syntax.semantics;

import java.util.regex.Pattern;

public class CSharpeSemanticsAnalyzer implements ISemanticsAnalyzer {

    @Override
    public boolean isByteValueCorrect(String byteValueAsString) {
        double value = Byte.valueOf(byteValueAsString);
        return value <= Byte.MAX_VALUE && value >= Byte.MIN_VALUE;
    }

    @Override
    public boolean isShortValueCorrect(String shortValueAsString) {
        double value = Short.valueOf(shortValueAsString);
        return value <= Short.MAX_VALUE && value >= Short.MIN_VALUE;
    }

    @Override
    public boolean isIntegerValueCorrect(String integerValueAsString) {
        double value = Integer.valueOf(integerValueAsString);
        return value <= Integer.MAX_VALUE && value >= Integer.MIN_VALUE;
    }

    @Override
    public boolean isLongValueCorrect(String longValueAsString) {
        double value = Long.valueOf(longValueAsString);
        return value <= Long.MAX_VALUE && value >= Long.MIN_VALUE;
    }

    @Override
    public boolean isFloatValueCorrect(String floatValueAsString) {
        double value = Float.valueOf(floatValueAsString);
        return value <= Float.MAX_VALUE && value >= Float.MIN_VALUE;
    }

    @Override
    public boolean isDoubleValueCorrect(String doubleValueAsString) {
        double value = Double.valueOf(doubleValueAsString);
        return value <= Double.MAX_VALUE && value >= Double.MIN_VALUE;
    }

    @Override
    public boolean isCharacterValueCorrect(String characterValueAsString) {
        return false;
    }

    @Override
    public boolean isBooleanValueCorrect(String booleanValueAsString) {
        return booleanValueAsString.equals("true") || booleanValueAsString.equals("false");
    }

    @Override
    public boolean isStringValueCorrect(String stringValueAsString) {
        return stringValueAsString.startsWith("\"")
                && stringValueAsString.endsWith("\"");
    }

    @Override
    public boolean isVariableNameCorrect(String variableAsString) {
        String allSpacesRegex = "[\\s\\n\\r\\t]*";
        String serviceCharactersRegex = "\\(\\)\\{\\{\\+=-\\[\\]\\|\\\\/&\\?\\^%#@\\$\\*:;\"\'!\\.";
        String anyNameCharSequenceRegex = "(_*[A-Za-z]+.*(^"
                .concat(allSpacesRegex)
                .concat(")([^")
                .concat(serviceCharactersRegex)
                .concat("])");
        return Pattern.compile(anyNameCharSequenceRegex).matcher(variableAsString).lookingAt();
    }
}
