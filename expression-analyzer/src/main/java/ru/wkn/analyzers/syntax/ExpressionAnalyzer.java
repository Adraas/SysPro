package ru.wkn.analyzers.syntax;

import lombok.Getter;
import lombok.Setter;
import ru.wkn.analyzers.exceptions.ExpressionException;
import ru.wkn.analyzers.syntax.semantics.ISemanticsAnalyzer;

@Getter
@Setter
public abstract class ExpressionAnalyzer {

    private ISemanticsAnalyzer iSemanticsAnalyzer;
    private boolean isSemanticsAnalyzerActivated;

    public ExpressionAnalyzer(ISemanticsAnalyzer iSemanticsAnalyzer, boolean isSemanticsAnalyzerActivated) {
        this.iSemanticsAnalyzer = iSemanticsAnalyzer;
        this.isSemanticsAnalyzerActivated = isSemanticsAnalyzerActivated;
    }

    public abstract boolean isSyntaxCorrect(String expression) throws ExpressionException;

    public boolean isSyntaxCorrect(String expression, boolean isSemanticsAnalyzerActivated) throws ExpressionException {
        setSemanticsAnalyzerActivated(isSemanticsAnalyzerActivated);
        return isSyntaxCorrect(expression);
    }
}
