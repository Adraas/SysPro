package ru.wkn.analyzers.syntax;

import lombok.Getter;
import lombok.Setter;
import ru.wkn.analyzers.exceptions.CompilationException;
import ru.wkn.analyzers.exceptions.ExpressionException;
import ru.wkn.analyzers.exceptions.SemanticsException;
import ru.wkn.analyzers.syntax.semantics.ISemanticsAnalyzer;

/**
 * The abstract class {@code ExpressionAnalyzer} represents abstract analyzer for programming language's expressions.
 *
 * @author Artem Pikalov
 */
@Getter
@Setter
public abstract class ExpressionAnalyzer {

    /**
     * {@code ISemanticsAnalyzer} object for the code semantics analyzing.
     */
    private ISemanticsAnalyzer iSemanticsAnalyzer;

    /**
     * {@code boolean} value indicates will whether the code semantics check be activated
     */
    private boolean isSemanticsAnalyzerActivated;

    /**
     * Initializes a newly created {@code ExpressionAnalyzer} object with given {@code ISemanticsAnalyzer}
     * object ({@link #iSemanticsAnalyzer}) and ({@link #isSemanticsAnalyzerActivated}) value.
     *
     * @param iSemanticsAnalyzer {@link ##iSemanticsAnalyzer}
     * @param isSemanticsAnalyzerActivated {@link #isSemanticsAnalyzerActivated}
     */
    public ExpressionAnalyzer(ISemanticsAnalyzer iSemanticsAnalyzer, boolean isSemanticsAnalyzerActivated) {
        this.iSemanticsAnalyzer = iSemanticsAnalyzer;
        this.isSemanticsAnalyzerActivated = isSemanticsAnalyzerActivated;
    }

    /**
     * The method for the checking to solvability the expression part (typically condition).
     *
     * @param expression the source expression one of the programming languages
     * @return {@code true} if expression is solvability, else - {@code false}
     * @throws CompilationException thrown if some problems with specific expression part
     */
    public abstract boolean expressionIsSolved(String expression) throws CompilationException;

    /**
     * The method for the syntax checking to correct.
     *
     * @param expression the source expression one of the programming languages
     * @return {@code true} if expression is correct, else - {@code false}
     * @throws ExpressionException thrown if some problems with specific expression part
     * @throws SemanticsException thrown if some problems with specific expression's semantics part
     */
    public abstract boolean isSyntaxCorrect(String expression) throws ExpressionException, SemanticsException;

    /**
     * The method for the syntax checking to correct with newly {@link #isSemanticsAnalyzerActivated} value.
     *
     * @param expression the source expression one of the programming languages
     * @param isSemanticsAnalyzerActivated {@link #isSemanticsAnalyzerActivated}
     * @return {@code true} if expression is correct, else - {@code false}
     * @throws ExpressionException thrown if some problems with specific expression part
     * @throws SemanticsException thrown if some problems with specific expression's semantics part
     */
    public boolean isSyntaxCorrect(String expression, boolean isSemanticsAnalyzerActivated) throws ExpressionException,
            SemanticsException {
        setSemanticsAnalyzerActivated(isSemanticsAnalyzerActivated);
        return isSyntaxCorrect(expression);
    }
}
