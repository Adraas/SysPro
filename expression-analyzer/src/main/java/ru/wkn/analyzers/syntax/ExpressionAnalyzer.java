package ru.wkn.analyzers.syntax;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.codehaus.plexus.compiler.AbstractCompiler;
import org.codehaus.plexus.compiler.Compiler;
import ru.wkn.analyzers.exceptions.CompilationException;
import ru.wkn.analyzers.exceptions.LanguageException;
import ru.wkn.analyzers.syntax.util.Language;

import java.util.List;

@AllArgsConstructor
@Getter
public abstract class ExpressionAnalyzer {

    private Language language;
    private Compiler compiler;

    public ExpressionAnalyzer(Language language, AbstractCompiler compiler) {
        this.language = language;
        this.compiler = compiler;
    }

    public abstract List<String> getCompilerMessages(String cycleWhileWithPreconditionExpression,
                                                     String tempSourcePathname, String outputPathname)
            throws CompilationException, LanguageException;
}
