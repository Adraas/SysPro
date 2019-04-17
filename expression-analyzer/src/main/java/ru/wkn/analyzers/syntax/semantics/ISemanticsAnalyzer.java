package ru.wkn.analyzers.syntax.semantics;

public interface ISemanticsAnalyzer {

    boolean isNumberLongCorrect(String numberLongAsString);

    boolean isNumberDoubleCorrect(String numberDoubleAsString);

    boolean isVariableNameCorrect(String variableAsString);

    boolean isCharValueCorrect(String charValueAsString);

    boolean isBooleanValueCorrect(String booleanValueAsString);
}
