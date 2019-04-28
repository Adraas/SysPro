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
        Pattern pattern = Pattern.compile("(abstract)|(as)|(base)|(bool)|(break)|(byte)|(case)|(catch)|(char)|(checked)"
                .concat("|(class)|(const)|(continue)|(decimal)|(default)|(delegate)|(do)|(double)|(else)|(enum)")
                .concat("|(event)|(explicit)|(extern)|(false)|(finally)|(fixed)|(float)|(for)|(foreach)|(goto)")
                .concat("|(if)|(implicit)|(in)|(int)|(interface)|(internal)|(is)|(lock)|(long)|(namespace)|(new)")
                .concat("|(null)|(object)|(operator)|(out)|(override)|(params)|(private)|(protected)|(public)")
                .concat("|(readonly)|(ref)|(return)|(sbyte)|(sealed)|(short)|(sizeof)|(stackalloc)|(static)")
                .concat("|(string)|(struct)|(switch)|(this)|(throw)|(true)|(try)|(typeof)|(uint)|(ulong)")
                .concat("|(unchecked)|(unsafe)|(ushort)|(using)|(virtual)|(void)|(volatile)|(while)"));
        return !pattern.matcher(variableAsString).matches();
    }
}
