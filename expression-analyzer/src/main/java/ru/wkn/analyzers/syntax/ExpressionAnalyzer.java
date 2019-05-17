package ru.wkn.analyzers.syntax;

import ru.wkn.analyzers.exceptions.ExpressionException;
import ru.wkn.analyzers.syntax.semantics.ISemanticsAnalyzer;

public abstract class ExpressionAnalyzer {

    private ISemanticsAnalyzer iSemanticsAnalyzer;
    private boolean isSemanticsAnalyzerActivated;

    public ExpressionAnalyzer(ISemanticsAnalyzer iSemanticsAnalyzer, boolean isSemanticsAnalyzerActivated) {
        this.iSemanticsAnalyzer = iSemanticsAnalyzer;
        this.isSemanticsAnalyzerActivated = isSemanticsAnalyzerActivated;
    }

    public abstract boolean isSyntaxCorrect(String expression) throws ExpressionException;

    public abstract boolean isSyntaxCorrect(String expression, boolean isSemanticsAnalyzerActivated) throws ExpressionException;

    public boolean isSemanticsAnalyzerActivated() {
        return isSemanticsAnalyzerActivated;
    }

    public void setSemanticsAnalyzerActivated(boolean semanticsAnalyzerActivated) {
        isSemanticsAnalyzerActivated = semanticsAnalyzerActivated;
    }
}
