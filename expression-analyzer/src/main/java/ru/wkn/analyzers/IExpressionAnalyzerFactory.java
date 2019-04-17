package ru.wkn.analyzers;

import ru.wkn.analyzers.syntax.IExpressionAnalyzer;

public interface IExpressionAnalyzerFactory {

    IExpressionAnalyzer createExpressionAnalyzer(TypeExpressionAnalyzer typeExpressionAnalyzer, Launguage launguage,
                                                 boolean isSemanticsActivated);
}
