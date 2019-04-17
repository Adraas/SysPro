package ru.wkn.analyzers.syntax.semantics;

public class CSharpeSemanticsAnalyzer implements ISemanticsAnalyzer {

    @Override
    public boolean isNumberLongCorrect(String numberAsString) {
        return false;
    }

    @Override
    public boolean isNumberDoubleCorrect(String numberDoubleAsString) {
        return false;
    }

    @Override
    public boolean isVariableNameCorrect(String variableAsString) {
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
}
