package ru.wkn.analyzers;

import org.codehaus.plexus.compiler.AbstractCompiler;
import org.codehaus.plexus.compiler.csharp.CSharpCompiler;
import ru.wkn.analyzers.syntax.CycleWhileWithPreconditionAnalyzer;
import ru.wkn.analyzers.syntax.ExpressionAnalyzer;
import ru.wkn.analyzers.syntax.util.Language;
import ru.wkn.analyzers.util.ExpressionAnalyzerType;

public class ExpressionAnalyzerFactory implements IExpressionAnalyzerFactory {

    @Override
    public ExpressionAnalyzer createExpressionAnalyzer(ExpressionAnalyzerType expressionAnalyzerType,
                                                       Language language) {
        return expressionAnalyzerType.equals(ExpressionAnalyzerType.CYCLE_WHILE_WITH_PRECONDITION)
                ? new CycleWhileWithPreconditionAnalyzer(language, createPlexusCompiler(language))
                : null;
    }

    private AbstractCompiler createPlexusCompiler(Language language) {
        return language.equals(Language.C_SHARPE) ? new CSharpCompiler() : null;
    }
}
