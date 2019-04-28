package ru.wkn.analyzers;

import ru.wkn.analyzers.syntax.ExpressionAnalyzer;
import ru.wkn.analyzers.syntax.util.Launguage;

public interface IExpressionAnalyzerFactory {

    ExpressionAnalyzer createExpressionAnalyzer(ExpressionAnalyzerType expressionAnalyzerType, Launguage launguage,
                                                boolean isSemanticsActivated);
}
