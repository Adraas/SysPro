package ru.wkn.analyzers.syntax;

import ru.wkn.analyzers.exceptions.ExpressionException;
import ru.wkn.analyzers.syntax.semantics.ISemanticsAnalyzer;

public class IfElseBlocksAnalyzer extends ExpressionAnalyzer {

    private final String singleBooleanExpressionRegex =
            "^\\s*([0-9a-zA-Z\\)\\(\\+\\-\\/\\*]*)\\s*(==|!=|>=|<=|<|>)\\s*([0-9a-zA-Z\\)\\(\\+\\-\\/\\*]*)\\s*";
    private final String variableDeclarationAndAssignmentRegex =
            "\\s*([a-zA-Z][0-9a-zA-Z]*)\\s*(=|\\+=|\\-=|\\*=|\\/=)\\s*([0-9a-zA-z\\-\\+\\*\\/\\s\\(\\)]*)\\s*;";
    private final String variableDeclarationRegex = "([a-zA-Z][0-9a-zA-Z]*)\\s*;";
    private final String variableAssignmentRegex =
            "^\\s*([a-zA-Z][0-9a-zA-Z]*)\\s*(=|\\+=|\\-=|\\*=|\\/=)\\s*([0-9a-zA-z\\-\\+\\*\\/\\s\\(\\)]*)\\s*;";
    private final String emptyInstructionRegex = "^\\s*";
    private final String variableNameRegex = "([a-zA-Z][0-9a-zA-Z]*)";
    private final String expressionRegex = "";
    private final String ifBlockRegex =
            "^\\s*if\\s*\\(([^\\(\\)]*)\\)\\s*{([^{}]*)}\\s*(else\\s*{([^{})]*)})?";

    public IfElseBlocksAnalyzer(ISemanticsAnalyzer iSemanticsAnalyzer, boolean isSemanticsAnalyzerActivated) {
        super(iSemanticsAnalyzer, isSemanticsAnalyzerActivated);
    }

    @Override
    public boolean expressionIsSolved(String expression) throws ExpressionException {
        return false;
    }

    @Override
    public boolean isSyntaxCorrect(String expression) throws ExpressionException {
        return false;
    }
}
