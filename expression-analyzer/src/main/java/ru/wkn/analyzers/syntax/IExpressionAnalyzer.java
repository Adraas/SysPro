package ru.wkn.analyzers.syntax;

import ru.wkn.analyzers.syntax.semantics.ISemanticsAnalyzer;

public abstract class IExpressionAnalyzer {

    private ISemanticsAnalyzer iSemanticsAnalyzer;
    private boolean isSemanticsAnalyzerActivated;

    public IExpressionAnalyzer(ISemanticsAnalyzer iSemanticsAnalyzer, boolean isSemanticsAnalyzerActivated) {
        this.iSemanticsAnalyzer = iSemanticsAnalyzer;
        this.isSemanticsAnalyzerActivated = isSemanticsAnalyzerActivated;
    }

    public abstract boolean isSyntaxCorrect(String expression);

    public abstract boolean isSyntaxCorrect(String expression, boolean isSemanticsAnalyzerActivated);

    public boolean isSemanticsAnalyzerActivated() {
        return isSemanticsAnalyzerActivated;
    }

    public void setSemanticsAnalyzerActivated(boolean semanticsAnalyzerActivated) {
        isSemanticsAnalyzerActivated = semanticsAnalyzerActivated;
    }
}
