package ru.wkn.analyzers.syntax;

import org.codehaus.plexus.compiler.csharp.CSharpCompiler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.wkn.analyzers.exceptions.CompilationException;
import ru.wkn.analyzers.exceptions.LanguageException;
import ru.wkn.analyzers.syntax.util.Language;
import ru.wkn.analyzers.util.CompilerStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestCycleWhileWithPreconditionAnalyzer {

    private static CycleWhileWithPreconditionAnalyzer cycleWhileWithPreconditionAnalyzer;
    private static String correctExpressionForAnalysis = "";
    private static String incorrectExpressionForAnalysis = "";

    @BeforeAll
    static void initFields() throws URISyntaxException, IOException {
        ClassLoader classLoader = TestCycleWhileWithPreconditionAnalyzer.class.getClassLoader();
        cycleWhileWithPreconditionAnalyzer = new CycleWhileWithPreconditionAnalyzer(Language.C_SHARPE,
                new CSharpCompiler());
        if (correctExpressionForAnalysis.trim().isEmpty()) {
            correctExpressionForAnalysis
                    = initExpressionForAnalysis(correctExpressionForAnalysis,
                    "expressions/correct_while_expression.txt",
                    classLoader);
        }
        if (incorrectExpressionForAnalysis.trim().isEmpty()) {
            incorrectExpressionForAnalysis
                    = initExpressionForAnalysis(incorrectExpressionForAnalysis,
                    "expressions/incorrect_while_expression.txt",
                    classLoader);
        }
    }

    static String initExpressionForAnalysis(String expressionForAnalysis, String path, ClassLoader classLoader)
            throws URISyntaxException, IOException {
        Stream<String> stream = Files.lines(Paths.get(Objects.requireNonNull(classLoader
                .getResource(path)).toURI()));
        Iterator<String> iterator = stream.iterator();
        while (iterator.hasNext()) {
            expressionForAnalysis = expressionForAnalysis.concat(iterator.next());
        }
        return expressionForAnalysis;
    }

    @Test
    void checkSyntaxToCorrect() throws IOException, CompilationException, LanguageException {
        String outputPathname = "correct";
        String sourceFile = "correct.cs";
        assertEquals(CompilerStatus.COMPILE_SUCCESS.getCompilerMessage(), cycleWhileWithPreconditionAnalyzer
                .getCompilerMessages(correctExpressionForAnalysis, sourceFile, outputPathname).get(0));
        updateOutputCatalog(outputPathname);
        updateOutputCatalog(sourceFile);
    }

    @Test
    void checkSyntaxToIncorrect() throws IOException, CompilationException, LanguageException {
        String outputPathname = "incorrect";
        String sourceFile = "incorrect.cs";
        assertNotEquals(CompilerStatus.COMPILE_SUCCESS.getCompilerMessage(), cycleWhileWithPreconditionAnalyzer
                .getCompilerMessages(incorrectExpressionForAnalysis, sourceFile, outputPathname).get(0));
        updateOutputCatalog(outputPathname);
        updateOutputCatalog(sourceFile);
    }

    private void updateOutputCatalog(String outputPathname) throws IOException {
        Path path = Paths.get(outputPathname);
        if (Files.exists(path)) {
            Files.delete(Paths.get(outputPathname));
        }
    }
}
