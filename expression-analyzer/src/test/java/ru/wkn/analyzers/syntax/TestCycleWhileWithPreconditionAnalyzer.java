package ru.wkn.analyzers.syntax;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestCycleWhileWithPreconditionAnalyzer {

    private static CycleWhileWithPreconditionAnalyzer cycleWhileWithPreconditionAnalyzer;
    private static String correctExpressionForAnalysis = "";
    private static String incorrectExpressionForAnalysis = "";

    @BeforeAll
    static void initFields() throws URISyntaxException, IOException {
        ClassLoader classLoader = TestCycleWhileWithPreconditionAnalyzer.class.getClassLoader();
        CSharpeSemanticsAnalyzer cSharpeSemanticsAnalyzer = new CSharpeSemanticsAnalyzer();
        cycleWhileWithPreconditionAnalyzer = new CycleWhileWithPreconditionAnalyzer(cSharpeSemanticsAnalyzer, false);
        if (correctExpressionForAnalysis.trim().isEmpty()) {
            initExpressionForAnalysis(correctExpressionForAnalysis, "expressions/correct_while_expression.txt",
                    classLoader);
        }
        if (incorrectExpressionForAnalysis.trim().isEmpty()) {
            initExpressionForAnalysis(incorrectExpressionForAnalysis, "expressions/incorrect_while_expression.txt",
                    classLoader);
        }
        setParametersIntoCSVFile(classLoader);
    }

    static void initExpressionForAnalysis(String expressionForAnalysis, String path, ClassLoader classLoader)
            throws URISyntaxException, IOException {
        Stream<String> stream = Files.lines(Paths.get(Objects.requireNonNull(classLoader
                .getResource(path)).toURI()));
        Iterator<String> iterator = stream.iterator();
        while (iterator.hasNext()) {
            expressionForAnalysis = expressionForAnalysis.concat(iterator.next());
        }
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
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getSingleMethodInvocationRegex()
                .concat("`_say4Me_Hello(say, me ,hello)\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getSingleMethodInvocationRegex()
                .concat("`_hello2me.sayMe2(asd, df,fsd    )\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getSingleMethodInvocationRegex()
                .concat("`hello2me(        )\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getStreamMethodInvocationsRegex()
                .concat("`_hello2me.sayMe2(asd, df,fsd    ).sayMe2(asd, df,fsd    )\n"));
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
                .concat("`HelloType hello2me   = 2344.235 \n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getCycleSingleLineBodyRegex()
                .concat("`HelloType hello2me   = 2344.235 ;\n"));
        bufferedWriter.write(cycleWhileWithPreconditionAnalyzer.getCycleMultipleBodyRegex()
                .concat("`HelloType hello2me   = 2344.235 ;sayMe2(asd, df,fsd    );hello2me =  sayMe2(asd, df,fsd  );"
                        .concat("\n")));

        bufferedWriter.close();
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

    @ParameterizedTest
    @CsvFileSource(resources = {"/expressions/elements.csv"}, delimiter = '`')
    void checkElementsSyntaxToCorrectWithoutSemanticsTest(String elementRegex, String element) {
        Pattern pattern = Pattern.compile(elementRegex);
        assertTrue(pattern.matcher(element).matches());
    }
}
