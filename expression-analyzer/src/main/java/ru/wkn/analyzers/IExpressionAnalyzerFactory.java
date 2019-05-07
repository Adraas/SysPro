package ru.wkn.analyzers;

import ru.wkn.analyzers.syntax.ExpressionAnalyzer;
import ru.wkn.analyzers.syntax.util.Language;
import ru.wkn.analyzers.util.ExpressionAnalyzerType;

public interface IExpressionAnalyzerFactory {

    ExpressionAnalyzer createExpressionAnalyzer(ExpressionAnalyzerType expressionAnalyzerType, Language language);
}
