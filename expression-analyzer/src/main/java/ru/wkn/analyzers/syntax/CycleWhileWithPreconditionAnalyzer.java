package ru.wkn.analyzers.syntax;

import lombok.Getter;
import org.codehaus.plexus.compiler.AbstractCompiler;
import org.codehaus.plexus.compiler.Compiler;
import org.codehaus.plexus.compiler.CompilerConfiguration;
import org.codehaus.plexus.compiler.CompilerException;
import org.codehaus.plexus.compiler.CompilerMessage;
import org.codehaus.plexus.compiler.CompilerResult;
import ru.wkn.analyzers.exceptions.CompilationException;
import ru.wkn.analyzers.exceptions.LanguageException;
import ru.wkn.analyzers.syntax.util.ExpressionCompiler;
import ru.wkn.analyzers.syntax.util.Language;
import ru.wkn.analyzers.util.CompilerStatus;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class CycleWhileWithPreconditionAnalyzer extends ExpressionAnalyzer {

    private Language language;
    private Compiler compiler;

    public CycleWhileWithPreconditionAnalyzer(Language language, AbstractCompiler compiler) {
        super(language, compiler);
        this.language = language;
        this.compiler = compiler;
    }

    @Override
    public List<String> getCompilerMessages(String cycleWhileWithPreconditionExpression, String tempSourcePathname,
                                            String outputPathname) throws CompilationException, LanguageException {
        String resultSource = ExpressionCompiler.compileExpression(cycleWhileWithPreconditionExpression, language);
        Set<File> sources = prepareSourceFiles(resultSource, tempSourcePathname);
        CompilerConfiguration compilerConfiguration = prepareCompileConfiguration(sources, outputPathname);
        CompilerResult compilerResult;
        try {
            compilerResult = compiler.performCompile(compilerConfiguration);
            if (compilerResult.isSuccess()) {
                List<String> compilerStringMessages = new ArrayList<>();
                compilerStringMessages.add(CompilerStatus.COMPILE_SUCCESS.getCompilerMessage());
                return compilerStringMessages;
            } else {
                List<String> compilerStringMessages = new ArrayList<>();
                List<CompilerMessage> compilerMessages = compilerResult.getCompilerMessages();
                for (CompilerMessage compilerMessage : compilerMessages) {
                    compilerStringMessages.add(compilerMessage.getMessage());
                }
                return compilerStringMessages;
            }
        } catch (CompilerException e) {
            throw new CompilationException(e.getMessage(), e);
        }
    }

    private Set<File> prepareSourceFiles(String resultSource, String tempSourcePathname) throws CompilationException {
        Path path = Paths.get(tempSourcePathname);
        BufferedWriter bufferedWriter;
        try {
            bufferedWriter = Files.newBufferedWriter(path, Charset.forName("UTF-8"));
            bufferedWriter.write(resultSource);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new CompilationException(e.getMessage(), e);
        }
        Set<File> sources = new HashSet<>();
        sources.add(new File(tempSourcePathname));
        return sources;
    }

    private CompilerConfiguration prepareCompileConfiguration(Set<File> sources, String outputPathname) {
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setSourceFiles(sources);
        compilerConfiguration.setOutputLocation(outputPathname);
        return compilerConfiguration;
    }
}
