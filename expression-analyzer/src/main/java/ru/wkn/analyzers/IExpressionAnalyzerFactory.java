package ru.wkn.analyzers;

import ru.wkn.analyzers.syntax.ExpressionAnalyzer;
import ru.wkn.analyzers.syntax.util.Language;

public interface IExpressionAnalyzerFactory {

    ExpressionAnalyzer createExpressionAnalyzer(ExpressionAnalyzerType expressionAnalyzerType, Language language,
                                                boolean isSemanticsActivated);
}
