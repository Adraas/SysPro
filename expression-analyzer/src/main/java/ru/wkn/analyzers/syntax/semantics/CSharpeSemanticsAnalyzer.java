package ru.wkn.analyzers.syntax.semantics;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Pattern;

/**
 * The class {@code ICSharpeSemanticsAnalyzer} represents semantics analyzer for C# expressions.
 *
 * @see ICSharpeSemanticsAnalyzer
 * @author Artem Pikalov
 */
public class CSharpeSemanticsAnalyzer implements ICSharpeSemanticsAnalyzer {

    /**
     * @see ICSharpeSemanticsAnalyzer#isByteValueCorrect(String)
     */
    @Override
    public boolean isByteValueCorrect(String byteValueAsString) {
        double value = Double.valueOf(byteValueAsString);
        return value <= 255 && value >= 0 && !byteValueAsString.contains(".");
    }

    /**
     * @see ICSharpeSemanticsAnalyzer#isShortValueCorrect(String)
     */
    @Override
    public boolean isShortValueCorrect(String shortValueAsString) {
        double value = Double.valueOf(shortValueAsString);
        return value <= 32767 && value >= -32768 && !shortValueAsString.contains(".");
    }

    /**
     * @see ICSharpeSemanticsAnalyzer#isIntegerValueCorrect(String)
     */
    @Override
    public boolean isIntegerValueCorrect(String integerValueAsString) {
        double value = Double.valueOf(integerValueAsString);
        return value <= 2147483647 && value >= -2147483648 && !integerValueAsString.contains(".");
    }

    /**
     * @see ICSharpeSemanticsAnalyzer#isLongValueCorrect(String)
     */
    @Override
    public boolean isLongValueCorrect(String longValueAsString) {
        return new BigInteger(longValueAsString.getBytes())
                .compareTo(new BigInteger(String.valueOf(0x7FFFFFFFFFFFFFFFL))) >= 0
                && new BigInteger(longValueAsString.getBytes())
                .compareTo(new BigInteger("9223372036854800000")) <= 0 && !longValueAsString.contains(".");
    }

    /**
     * @see ICSharpeSemanticsAnalyzer#isFloatValueCorrect(String)
     */
    @Override
    public boolean isFloatValueCorrect(String floatValueAsString) {
        double value = Double.valueOf(floatValueAsString);
        return value <= -3.4e+38f && value >= -3.4e+38f;
    }

    /**
     * @see ICSharpeSemanticsAnalyzer#isDoubleValueCorrect(String)
     */
    @Override
    public boolean isDoubleValueCorrect(String doubleValueAsString) {
        return new BigDecimal(doubleValueAsString).compareTo(new BigDecimal(1.7e+308d)) >= 0
                && new BigDecimal(doubleValueAsString).compareTo(new BigDecimal(5e-324d)) <= 0;
    }

    /**
     * @see ICSharpeSemanticsAnalyzer#isDecimalValueCorrect(String)
     */
    @Override
    public boolean isDecimalValueCorrect(String decimalValueAsString) {
        double value = Double.valueOf(decimalValueAsString);
        return value <= 7.9e+28d && value >= 0.00000000000000000000000000001d;
    }

    /**
     * @see ICSharpeSemanticsAnalyzer#isSbyteValueCorrect(String)
     */
    @Override
    public boolean isSbyteValueCorrect(String sbyteValueAsString) {
        double value = Double.valueOf(sbyteValueAsString);
        return value <= 127 && value >= -128 && !sbyteValueAsString.contains(".");
    }

    /**
     * @see ICSharpeSemanticsAnalyzer#isUshortValueCorrect(String)
     */
    @Override
    public boolean isUshortValueCorrect(String ushortValueAsString) {
        double value = Double.valueOf(ushortValueAsString);
        return value <= 65535 && value >= 0 && !ushortValueAsString.contains(".");
    }

    /**
     * @see ICSharpeSemanticsAnalyzer#isUintegerValueCorrect(String)
     */
    @Override
    public boolean isUintegerValueCorrect(String uintegerValueAsString) {
        double value = Double.valueOf(uintegerValueAsString);
        return value <= 4.294967295e+9 && value >= 0 && !uintegerValueAsString.contains(".");
    }

    /**
     * @see ICSharpeSemanticsAnalyzer#isUlongValueCorrect(String)
     */
    @Override
    public boolean isUlongValueCorrect(String ulongValueAsString) {
        double value = Double.valueOf(ulongValueAsString);
        return value <= 1.8446744073709552e+19 && value >= 0 && !ulongValueAsString.contains(".");
    }

    /**
     * @see ICSharpeSemanticsAnalyzer#isCharacterValueCorrect(String)
     */
    @Override
    public boolean isCharacterValueCorrect(String characterValueAsString) {
        double length = characterValueAsString.length();
        return characterValueAsString.startsWith("\"")
                && characterValueAsString.endsWith("\"")
                && (length == 3 || length == 4);
    }

    /**
     * @see ICSharpeSemanticsAnalyzer#isBooleanValueCorrect(String)
     */
    @Override
    public boolean isBooleanValueCorrect(String booleanValueAsString) {
        return booleanValueAsString.equals("true") || booleanValueAsString.equals("false");
    }

    /**
     * @see ICSharpeSemanticsAnalyzer#isStringValueCorrect(String)
     */
    @Override
    public boolean isStringValueCorrect(String stringValueAsString) {
        return stringValueAsString.startsWith("\"")
                && stringValueAsString.endsWith("\"");
    }

    /**
     * @see ICSharpeSemanticsAnalyzer#isVariableNameCorrect(String)
     */
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
