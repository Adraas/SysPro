package ru.wkn.analyzers;

import ru.wkn.analyzers.syntax.IExpressionAnalyzer;

public interface IExpressionAnalyzerFactory {

    IExpressionAnalyzer createExpressionAnalyzer(ExpressionAnalyzerType expressionAnalyzerType, Launguage launguage,
                                                 boolean isSemanticsActivated);
}
