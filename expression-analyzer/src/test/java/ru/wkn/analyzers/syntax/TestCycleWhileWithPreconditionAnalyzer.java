package ru.wkn.analyzers.syntax;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.wkn.analyzers.syntax.semantics.CSharpeSemanticsAnalyzer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestCycleWhileWithPreconditionAnalyzer {

    private static CycleWhileWithPreconditionAnalyzer cycleWhileWithPreconditionAnalyzer;
    private static String correctExpressionForAnalysis = "";
    private static String incorrectExpressionForAnalysis = "";

    @BeforeAll
    static void initFields() throws URISyntaxException, IOException {
        CSharpeSemanticsAnalyzer cSharpeSemanticsAnalyzer = new CSharpeSemanticsAnalyzer();
        cycleWhileWithPreconditionAnalyzer = new CycleWhileWithPreconditionAnalyzer(cSharpeSemanticsAnalyzer, false);
        if (correctExpressionForAnalysis.trim().isEmpty()) {
            initExpressionForAnalysis(correctExpressionForAnalysis, "expressions/correct_while_expression.txt");
        }
        if (incorrectExpressionForAnalysis.trim().isEmpty()) {
            initExpressionForAnalysis(incorrectExpressionForAnalysis, "expressions/incorrect_while_expression.txt");
        }
    }

    static void initExpressionForAnalysis(String expressionForAnalysis, String path) throws URISyntaxException,
            IOException {
        Stream<String> stream = Files.lines(Paths.get(Objects.requireNonNull(TestCycleWhileWithPreconditionAnalyzer
                .class.getClassLoader().getResource(path)).toURI()));
        Iterator<String> iterator = stream.iterator();
        while (iterator.hasNext()) {
            expressionForAnalysis = expressionForAnalysis.concat(iterator.next());
        }
    }

    @Test
    void checkSyntaxToCorrectWithoutSemanticsTest() {
        assertTrue(cycleWhileWithPreconditionAnalyzer.isSyntaxCorrect(correctExpressionForAnalysis));
    }

    @Test
    void checkSyntaxToIncorrectWithoutSemanticsTest() {
        assertFalse(cycleWhileWithPreconditionAnalyzer.isSyntaxCorrect(incorrectExpressionForAnalysis));
    }

    @Test
    void checkSyntaxToCorrectWithSemanticsTest() {
        assertTrue(cycleWhileWithPreconditionAnalyzer.isSyntaxCorrect(correctExpressionForAnalysis, true));
    }

    @Test
    void checkSyntaxToIncorrectWithSemanticsTest() {
        assertFalse(cycleWhileWithPreconditionAnalyzer.isSyntaxCorrect(incorrectExpressionForAnalysis, true));
    }
}
