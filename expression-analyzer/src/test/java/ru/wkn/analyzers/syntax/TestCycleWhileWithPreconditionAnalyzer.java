package ru.wkn.analyzers.syntax;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.wkn.analyzers.exceptions.ExpressionException;
import ru.wkn.analyzers.exceptions.SemanticsException;
import ru.wkn.analyzers.syntax.semantics.CSharpeSemanticsAnalyzer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestCycleWhileWithPreconditionAnalyzer {

    private static CycleWhileWithPreconditionAnalyzer cycleWhileWithPreconditionAnalyzer;
    private static String correctExpressionForAnalysis = "";
    private static String incorrectExpressionForAnalysis = "";
    private static String correctExpressionWithIncorrectSemanticsForAnalyzer = "";
    private static String correctExpressionWithTrueConditionForAnalyzer = "";
    private static String correctExpressionWithFalseConditionForAnalyzer = "";

    @BeforeAll
    static void initFields() throws URISyntaxException, IOException {
        ClassLoader classLoader = TestCycleWhileWithPreconditionAnalyzer.class.getClassLoader();
        CSharpeSemanticsAnalyzer cSharpeSemanticsAnalyzer = new CSharpeSemanticsAnalyzer();
        cycleWhileWithPreconditionAnalyzer = new CycleWhileWithPreconditionAnalyzer(cSharpeSemanticsAnalyzer, false);
        if (correctExpressionForAnalysis.trim().isEmpty()) {
            correctExpressionForAnalysis = initExpressionForAnalysis(correctExpressionForAnalysis,
                    "expressions/correct_while_expression.txt", classLoader);
        }
        if (incorrectExpressionForAnalysis.trim().isEmpty()) {
            incorrectExpressionForAnalysis = initExpressionForAnalysis(incorrectExpressionForAnalysis,
                    "expressions/incorrect_while_expression.txt", classLoader);
        }
        if (correctExpressionWithIncorrectSemanticsForAnalyzer.trim().isEmpty()) {
            correctExpressionWithIncorrectSemanticsForAnalyzer =
                    initExpressionForAnalysis(correctExpressionWithIncorrectSemanticsForAnalyzer,
                            "expressions/correct_while_expression_with_incorrect_semantics.txt", classLoader);
        }
        if (correctExpressionWithTrueConditionForAnalyzer.trim().isEmpty()) {
            correctExpressionWithTrueConditionForAnalyzer =
                    initExpressionForAnalysis(correctExpressionWithTrueConditionForAnalyzer,
                            "expressions/correct_while_expression_with_true_condition.txt", classLoader);
        }
        if (correctExpressionWithFalseConditionForAnalyzer.trim().isEmpty()) {
            correctExpressionWithFalseConditionForAnalyzer =
                    initExpressionForAnalysis(correctExpressionWithFalseConditionForAnalyzer,
                            "expressions/correct_while_expression_with_false_condition.txt", classLoader);
        }
        setParametersIntoCSVFile(classLoader);
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

    static void setParametersIntoCSVFile(ClassLoader classLoader) throws URISyntaxException, IOException {
        Path path = Paths.get(Objects.requireNonNull(classLoader
                .getResource("expressions/elements.csv")).toURI());
        if (Files.exists(path)) {
            Files.delete(path);
        }
        Files.createFile(path);

        BufferedWriter bufferedWriter = Files.newBufferedWriter(path);
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getAnyNameCharSequenceRegex()
                .concat("`_say4Me_Hello\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getVariableDeclarationRegex()
                .concat("`HelloType     _say4Me_Hello\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getNumberRegex()
                .concat("`2344.235\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getSingleMethodOrVariableInvocationRegex()
                .concat("`_say4Me_Hello(say, me ,hello)\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getSingleMethodOrVariableInvocationRegex()
                .concat("`_hello2me.sayMe2(asd, df,fsd    )\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getSingleMethodOrVariableInvocationRegex()
                .concat("`hello2me(        )\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getStreamMethodOrVariableInvocationsRegex()
                .concat("`_hello2me.sayMe2(asd, df,fsd    ).sayMe2(asd, df,fsd    )\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getMathExpressionRegex()
                .concat("`i + hello().get()\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getMathExpressionRegex()
                .concat("`i + hello().get\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getMathExpressionRegex()
                .concat("`i/3\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getVariableAssignmentRegex()
                .concat("`i=i + hello().get\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getVariableAssignmentRegex()
                .concat("`i=i + i/3\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getVariableAssignmentRegex()
                .concat("`hello2me =    yes\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getVariableAssignmentRegex()
                .concat("`hello2me    =    2344.235\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getVariableAssignmentRegex()
                .concat("`hello2me =    sayMe2(asd, df,fsd    )\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getVariableDeclarationAndAssignmentRegex()
                .concat("`HelloType hello2me   =  sayMe2(asd, df,fsd    )\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getVariableDeclarationAndAssignmentRegex()
                .concat("`HelloType hello2me   =  sayMe2\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getVariableDeclarationAndAssignmentRegex()
                .concat("`HelloType hello2me   = 2344.235\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getCycleSingleBodyLineRegex()
                .concat("`HelloType hello2me   = 2344.235 ;\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getCycleMultipleBodyRegex()
                .concat("`HelloType hello2me  = 2344.235 ;sayMe2(asd, df,fsd   );hello2me = sayMe2(asd, df,fsd );\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getComparisonOperationRegex()
                .concat("`hello2me   == 2344.235 \n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getComparisonOperationRegex()
                .concat("`hello2me   >= _hello2me.sayMe2(asd, df,fsd    ).sayMe2(asd, df,fsd    )\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getCycleConditionRegex()
                .concat("`hello2me   == 2344.235\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getCycleConditionRegex()
                .concat("`hello2me   == 2344.235 && isCorrectSyntax\n"));

        bufferedWriter.close();
    }

    @Test
    void checkSyntaxToCorrectWithoutSemanticsTest() throws ExpressionException, SemanticsException {
        assertTrue(cycleWhileWithPreconditionAnalyzer.isSyntaxCorrect(correctExpressionForAnalysis));
    }

    @Test
    void checkSyntaxToIncorrectWithoutSemanticsTest() {
        assertThrows(ExpressionException.class, () -> cycleWhileWithPreconditionAnalyzer
                .isSyntaxCorrect(incorrectExpressionForAnalysis));
    }

    @Test
    void checkSyntaxToCorrectWithSemanticsTest() throws ExpressionException, SemanticsException {
        assertTrue(cycleWhileWithPreconditionAnalyzer.isSyntaxCorrect(correctExpressionForAnalysis, true));
    }

    @Test
    void checkSyntaxToIncorrectWithSemanticsTest() {
        assertThrows(SemanticsException.class, () -> cycleWhileWithPreconditionAnalyzer
                .isSyntaxCorrect(correctExpressionWithIncorrectSemanticsForAnalyzer, true));
    }

    @Test
    void checkExpressionToSolvabilityTest() throws ExpressionException {
        assertTrue(cycleWhileWithPreconditionAnalyzer.expressionIsSolved(correctExpressionWithTrueConditionForAnalyzer));
    }

    @Test
    void checkExpressionToUnsolvabilityTest() throws ExpressionException {
        assertFalse(cycleWhileWithPreconditionAnalyzer.expressionIsSolved(correctExpressionWithFalseConditionForAnalyzer));
    }

    @Test
    void checkExpressionToUncertaintyTest() {
        assertThrows(ExpressionException.class, () -> cycleWhileWithPreconditionAnalyzer
                .expressionIsSolved(correctExpressionForAnalysis));
    }

    @ParameterizedTest
    @CsvFileSource(resources = {"/expressions/elements.csv"}, delimiter = '`')
    void checkElementsSyntaxToCorrectWithoutSemanticsTest(String elementRegex, String element) {
        Pattern pattern = Pattern.compile(elementRegex);
        assertTrue(pattern.matcher(element).matches());
    }
}
