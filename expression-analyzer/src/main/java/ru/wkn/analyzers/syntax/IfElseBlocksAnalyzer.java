package ru.wkn.analyzers.syntax;

import ru.wkn.analyzers.exceptions.ExpressionException;
import ru.wkn.analyzers.syntax.semantics.ISemanticsAnalyzer;

public class IfElseBlocksAnalyzer extends ExpressionAnalyzer {

    public IfElseBlocksAnalyzer(ISemanticsAnalyzer iSemanticsAnalyzer, boolean isSemanticsAnalyzerActivated) {
        super(iSemanticsAnalyzer, isSemanticsAnalyzerActivated);
    }

    @Override
    public boolean isSyntaxCorrect(String expression) throws ExpressionException {
        return false;
    }
}
