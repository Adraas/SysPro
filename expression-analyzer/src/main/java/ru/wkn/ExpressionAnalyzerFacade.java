package ru.wkn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.wkn.analyzers.IExpressionAnalyzerFactory;
import ru.wkn.analyzers.syntax.ExpressionAnalyzer;

/**
 * The class {@code ExpressionAnalyzerFacade} represents the facade design pattern for expression analyzer.
 *
 * @author Artem Pikalov
 */
@AllArgsConstructor
@Getter
public class ExpressionAnalyzerFacade {

    /**
     * {@code ExpressionAnalyzer} object for expression analyzing.
     */
    private ExpressionAnalyzer expressionAnalyzer;

    /**
     * {@code IExpressionAnalyzerFactory} object for analyzer creating.
     */
    private IExpressionAnalyzerFactory expressionAnalyzerFactory;
}
