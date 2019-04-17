package ru.wkn.analyzers.syntax.semantics;

public class CSharpeSemanticsAnalyzer implements ISemanticsAnalyzer {

    @Override
    public boolean isNumberByteCorrect(String numberByteAsString) {
        return false;
    }

    @Override
    public boolean isNumberShortCorrect(String numberShortAsString) {
        return false;
    }

    @Override
    public boolean isNumberIntCorrect(String numberIntAsString) {
        return false;
    }

    @Override
    public boolean isNumberLongCorrect(String numberLongAsString) {
        return false;
    }

    @Override
    public boolean isNumberFloatCorrect(String numberFloatAsString) {
        return false;
    }

    @Override
    public boolean isNumberDoubleCorrect(String numberDoubleAsString) {
        return false;
    }

    @Override
    public boolean isCharValueCorrect(String charValueAsString) {
        return false;
    }

    @Override
    public boolean isBooleanValueCorrect(String booleanValueAsString) {
        return false;
    }

    @Override
    public boolean isVariableNameCorrect(String variableAsString) {
        return false;
    }
}
