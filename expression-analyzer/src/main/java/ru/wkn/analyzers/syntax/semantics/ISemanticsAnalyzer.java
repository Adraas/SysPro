package ru.wkn.analyzers.syntax.semantics;

public interface ISemanticsAnalyzer {

    boolean isNumberByteCorrect(String numberByteAsString);

    boolean isNumberShortCorrect(String numberShortAsString);

    boolean isNumberIntCorrect(String numberIntAsString);

    boolean isNumberLongCorrect(String numberLongAsString);

    boolean isNumberFloatCorrect(String numberFloatAsString);

    boolean isNumberDoubleCorrect(String numberDoubleAsString);

    boolean isCharValueCorrect(String charValueAsString);

    boolean isBooleanValueCorrect(String booleanValueAsString);

    boolean isVariableNameCorrect(String variableAsString);
}
