package ru.wkn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.wkn.analyzers.IExpressionAnalyzerFactory;
import ru.wkn.analyzers.syntax.ExpressionAnalyzer;

@AllArgsConstructor
@Getter
public class ExpressionAnalyzerFacade {

    private ExpressionAnalyzer expressionAnalyzer;
    private IExpressionAnalyzerFactory expressionAnalyzerFactory;
}
