package ru.wkn;

import lombok.Getter;
import org.codehaus.plexus.compiler.Compiler;
import ru.wkn.analyzers.syntax.ExpressionAnalyzer;
import ru.wkn.analyzers.syntax.util.Language;

@Getter
public class ExpressionAnalyzerFacade {

    private Language language;
    private Compiler compiler;
    private ExpressionAnalyzer expressionAnalyzer;

    public ExpressionAnalyzerFacade(Language language, Compiler compiler) {
        this.language = language;
        this.compiler = compiler;
        expressionAnalyzerInit(language, compiler);
    }

    private void expressionAnalyzerInit(Language language, Compiler compiler) {
        expressionAnalyzer = new ExpressionAnalyzer(language, compiler);
    }

    public void setLanguage(Language language) {
        this.language = language;
        expressionAnalyzerInit(language, compiler);
    }

    public void setCompiler(Compiler compiler) {
        this.compiler = compiler;
        expressionAnalyzerInit(language, compiler);
    }
}
