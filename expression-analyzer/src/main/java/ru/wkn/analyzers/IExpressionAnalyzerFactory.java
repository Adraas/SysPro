package ru.wkn.analyzers;

import ru.wkn.analyzers.exceptions.AnalyzerException;
import ru.wkn.analyzers.exceptions.LanguageException;
import ru.wkn.analyzers.syntax.ExpressionAnalyzer;
import ru.wkn.analyzers.syntax.util.Language;

/**
 * The interface {@code IExpressionAnalyzerFactory} contains factory method for the {@code ExpressionAnalyzer}
 * objects creating.
 *
 * @author Artem Pikalov
 */
public interface IExpressionAnalyzerFactory {

    /**
     * The abstract factory method for the {@code ExpressionAnalyzer} object creating.
     *
     * @param expressionAnalyzerType type of the expression analyzer as enum value
     * @param language programming language for analyzing as enum value
     * @param isSemanticsActivated will whether the code semantics check be activated
     * @return {@code ExpressionAnalyzer} object
     * @throws LanguageException thrown if some problems with indicated language type
     * @throws AnalyzerException thrown if some problems with indicated analyzer type
     */
    ExpressionAnalyzer createExpressionAnalyzer(ExpressionAnalyzerType expressionAnalyzerType, Language language,
                                                boolean isSemanticsActivated) throws LanguageException, AnalyzerException;
}
