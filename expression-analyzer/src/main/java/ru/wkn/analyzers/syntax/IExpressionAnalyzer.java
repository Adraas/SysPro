package ru.wkn.analyzers.syntax;

import ru.wkn.analyzers.syntax.semantics.ISemanticsAnalyzer;

public abstract class IExpressionAnalyzer {

    private ISemanticsAnalyzer iSemanticsAnalyzer;

    public IExpressionAnalyzer(ISemanticsAnalyzer iSemanticsAnalyzer) {
        this.iSemanticsAnalyzer = iSemanticsAnalyzer;
    }

    public abstract boolean isSyntaxCorrect(String expression);
}
