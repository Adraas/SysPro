package ru.wkn.analyzers.syntax;

import ru.wkn.analyzers.syntax.semantics.ISemanticsAnalyzer;

public class CycleWhileWithPreconditionAnalyzer extends IExpressionAnalyzer {

    private ISemanticsAnalyzer iSemanticsAnalyzer;

    public CycleWhileWithPreconditionAnalyzer(ISemanticsAnalyzer iSemanticsAnalyzer) {
        super(iSemanticsAnalyzer);
    }

    @Override
    public boolean isSyntaxCorrect(String expression) {
        return false;
    }
}
