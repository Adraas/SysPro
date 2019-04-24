package ru.wkn.analyzers;

import ru.wkn.analyzers.syntax.CycleWhileWithPreconditionAnalyzer;
import ru.wkn.analyzers.syntax.ExpressionAnalyzer;
import ru.wkn.analyzers.syntax.Launguage;
import ru.wkn.analyzers.syntax.semantics.CSharpeSemanticsAnalyzer;
import ru.wkn.analyzers.syntax.semantics.ISemanticsAnalyzer;

public class ExpressionAnalyzerFactory implements IExpressionAnalyzerFactory {

    @Override
    public ExpressionAnalyzer createExpressionAnalyzer(ExpressionAnalyzerType expressionAnalyzerType,
                                                       Launguage launguage, boolean isSemanticsAnalyzerActivated) {
        return expressionAnalyzerType.equals(ExpressionAnalyzerType.CYCLE_WHILE_WITH_PRECONDITION)
                ? new CycleWhileWithPreconditionAnalyzer(createSemanticsAnalyzer(launguage), isSemanticsAnalyzerActivated)
                : null;
    }

    private ISemanticsAnalyzer createSemanticsAnalyzer(Launguage launguage) {
        return launguage.equals(Launguage.C_SHARPE) ? new CSharpeSemanticsAnalyzer() : null;
    }
}
