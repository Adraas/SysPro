package ru.wkn.analyzers;

import ru.wkn.analyzers.exceptions.AnalyzerException;
import ru.wkn.analyzers.exceptions.LanguageException;
import ru.wkn.analyzers.syntax.CycleWhileWithPreconditionAnalyzer;
import ru.wkn.analyzers.syntax.ExpressionAnalyzer;
import ru.wkn.analyzers.syntax.semantics.ICSharpeSemanticsAnalyzer;
import ru.wkn.analyzers.syntax.util.Language;
import ru.wkn.analyzers.syntax.semantics.CSharpeSemanticsAnalyzer;
import ru.wkn.analyzers.syntax.semantics.ISemanticsAnalyzer;

/**
 * The class {@code ExpressionAnalyzerFactory} contains factory method for the {@code ExpressionAnalyzer}
 * objects creating.
 *
 * @author Artem Pikalov
 */
public class ExpressionAnalyzerFactory implements IExpressionAnalyzerFactory {

    /**
     * @see IExpressionAnalyzerFactory#createExpressionAnalyzer(ExpressionAnalyzerType, Language, boolean)
     */
    @Override
    public ExpressionAnalyzer createExpressionAnalyzer(ExpressionAnalyzerType expressionAnalyzerType,
                                                       Language language, boolean isSemanticsAnalyzerActivated)
            throws LanguageException, AnalyzerException {
        ExpressionAnalyzer expressionAnalyzer = expressionAnalyzerType
                .equals(ExpressionAnalyzerType.CYCLE_WHILE_WITH_PRECONDITION)
                ? new CycleWhileWithPreconditionAnalyzer((ICSharpeSemanticsAnalyzer) createSemanticsAnalyzer(language),
                isSemanticsAnalyzerActivated)
                : null;
        if (expressionAnalyzer == null) {
            throw new AnalyzerException(expressionAnalyzerType.name());
        }
        return expressionAnalyzer;
    }

    private ISemanticsAnalyzer createSemanticsAnalyzer(Language language) throws LanguageException {
        ISemanticsAnalyzer semanticsAnalyzer = language.equals(Language.C_SHARPE) ? new CSharpeSemanticsAnalyzer()
                : null;
        if (semanticsAnalyzer == null) {
            throw new LanguageException("semantics analyzer for ".concat(language.name()).concat(" not found"));
        }
        return semanticsAnalyzer;
    }
}
