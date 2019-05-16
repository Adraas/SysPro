package ru.wkn.analyzers;

import ru.wkn.analyzers.syntax.CycleWhileWithPreconditionAnalyzer;
import ru.wkn.analyzers.syntax.ExpressionAnalyzer;
import ru.wkn.analyzers.syntax.util.Language;
import ru.wkn.analyzers.syntax.semantics.CSharpeSemanticsAnalyzer;
import ru.wkn.analyzers.syntax.semantics.ISemanticsAnalyzer;

public class ExpressionAnalyzerFactory implements IExpressionAnalyzerFactory {

    @Override
    public ExpressionAnalyzer createExpressionAnalyzer(ExpressionAnalyzerType expressionAnalyzerType,
                                                       Language language, boolean isSemanticsAnalyzerActivated) {
        return expressionAnalyzerType.equals(ExpressionAnalyzerType.CYCLE_WHILE_WITH_PRECONDITION)
                ? new CycleWhileWithPreconditionAnalyzer(createSemanticsAnalyzer(language), isSemanticsAnalyzerActivated)
                : null;
    }

    private ISemanticsAnalyzer createSemanticsAnalyzer(Language language) {
        return language.equals(Language.C_SHARPE) ? new CSharpeSemanticsAnalyzer() : null;
    }
}
