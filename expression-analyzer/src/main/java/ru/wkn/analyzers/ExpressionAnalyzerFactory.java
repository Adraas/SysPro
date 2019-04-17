package ru.wkn.analyzers;

import ru.wkn.analyzers.syntax.CycleWhileWithPreconditionAnalyzer;
import ru.wkn.analyzers.syntax.IExpressionAnalyzer;
import ru.wkn.analyzers.syntax.semantics.CSharpeSemanticsAnalyzer;
import ru.wkn.analyzers.syntax.semantics.ISemanticsAnalyzer;

public class ExpressionAnalyzerFactory implements IExpressionAnalyzerFactory {

    @Override
    public IExpressionAnalyzer createExpressionAnalyzer(TypeExpressionAnalyzer typeExpressionAnalyzer,
                                                        Launguage launguage, boolean isSemanticsAnalyzerActivated) {
        return typeExpressionAnalyzer.equals(TypeExpressionAnalyzer.CYCLE_WHILE_WITH_PRECONDITION)
                ? new CycleWhileWithPreconditionAnalyzer(createSemanticsAnalyzer(launguage), isSemanticsAnalyzerActivated)
                : null;
    }

    private ISemanticsAnalyzer createSemanticsAnalyzer(Launguage launguage) {
        return launguage.equals(Launguage.C_SHARPE) ? new CSharpeSemanticsAnalyzer() : null;
    }
}
