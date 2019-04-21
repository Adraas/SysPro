package ru.wkn.analyzers.syntax;

import ru.wkn.analyzers.DataType;
import ru.wkn.analyzers.ActionType;
import ru.wkn.analyzers.syntax.semantics.ISemanticsAnalyzer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CycleWhileWithPreconditionAnalyzer extends IExpressionAnalyzer {

    private final String allSpacesRegex = "(\\s*\\n*\\r*\\t*)";
    private final String variableDeclarationRegex = "([A-Za-z]*"
            .concat(allSpacesRegex)
            .concat("[A-Za-z]*)");
    private final String numberRegex = "((([1-9][0-9]*)|(0))(\\.[0-9]+)?)";
    private final String singleMethodInvocationRegex = "([A-Za-z]*\\.)?[A-Za-z]*("
            .concat(allSpacesRegex)
            .concat("\\(([A-Za-z]")
            .concat(allSpacesRegex)
            .concat(")(,")
            .concat(allSpacesRegex)
            .concat("[A-Za-z])*\\))");
    private final String streamMethodInvocationsRegex = "(("
            .concat(singleMethodInvocationRegex)
            .concat(")(")
            .concat(allSpacesRegex)
            .concat("\\.")
            .concat(singleMethodInvocationRegex)
            .concat(")*)");
    private final String variableAssignmentRegex = "(="
            .concat(allSpacesRegex)
            .concat("((")
            .concat(numberRegex)
            .concat(")|(")
            .concat(streamMethodInvocationsRegex)
            .concat(")|([A-Za-z]*))")
            .concat(allSpacesRegex)
            .concat(")");
    private final String variableDeclarationAndAssignmentRegex = "("
            .concat(variableDeclarationRegex)
            .concat(allSpacesRegex)
            .concat(variableAssignmentRegex)
            .concat(allSpacesRegex)
            .concat(")");
    private final String cycleSingleLineBodyRegex = "(("
            .concat(variableDeclarationAndAssignmentRegex)
            .concat(")|(")
            .concat(variableAssignmentRegex)
            .concat(")|(")
            .concat(variableDeclarationRegex)
            .concat(")|(")
            .concat(streamMethodInvocationsRegex)
            .concat(")")
            .concat(allSpacesRegex)
            .concat(";)");
    private final String cycleMultipleBodyRegex = "(("
            .concat(cycleSingleLineBodyRegex)
            .concat(")*)");
    private final String comparisonOperationRegex = "((([A-Za-z]+)|("
            .concat(numberRegex)
            .concat("))")
            .concat(allSpacesRegex)
            .concat("(")
            .concat(">|<|(>=)|(<=)|(==)")
            .concat(")")
            .concat(allSpacesRegex)
            .concat("(([A-Za-z]+)|(")
            .concat(numberRegex)
            .concat(")))");
    private final String singleBooleanExpressionRegex = "(([A-Za-z]+)|("
            .concat(variableDeclarationAndAssignmentRegex)
            .concat(")|(")
            .concat(comparisonOperationRegex)
            .concat(")|(")
            .concat(streamMethodInvocationsRegex)
            .concat("))");
    private final String cycleConditionRegex = "("
            .concat(singleBooleanExpressionRegex)
            .concat("(")
            .concat(allSpacesRegex)
            .concat("((&&)|(\\|\\|)|([&|]))")
            .concat(singleBooleanExpressionRegex)
            .concat(")*)");
    private final String cycleWhileWithPreconditionRegex = "("
            .concat(allSpacesRegex)
            .concat("(while)")
            .concat(allSpacesRegex)
            .concat("\\(")
            .concat(allSpacesRegex)
            .concat(cycleConditionRegex)
            .concat("\\)")
            .concat(allSpacesRegex)
            .concat("((\\{")
            .concat(cycleMultipleBodyRegex)
            .concat("\\})|(")
            .concat(cycleSingleLineBodyRegex)
            .concat(")))");

    private Pattern pattern = Pattern.compile(cycleWhileWithPreconditionRegex);
    private ISemanticsAnalyzer iSemanticsAnalyzer;
    private boolean isSemanticsAnalyzerActivated;

    public CycleWhileWithPreconditionAnalyzer(ISemanticsAnalyzer iSemanticsAnalyzer,
                                              boolean isSemanticsAnalyzerActivated) {
        super(iSemanticsAnalyzer, isSemanticsAnalyzerActivated);
        this.iSemanticsAnalyzer = iSemanticsAnalyzer;
        this.isSemanticsAnalyzerActivated = isSemanticsAnalyzerActivated;
    }

    @Override
    public boolean isSyntaxCorrect(String expression) {
        return isSyntaxCorrect(expression, isSemanticsAnalyzerActivated);
    }

    @Override
    public boolean isSyntaxCorrect(String expression, boolean isSemanticsAnalyzerActivated) {
        Matcher matcher = pattern.matcher(expression);
        if (matcher.lookingAt()) {
            if (isSemanticsAnalyzerActivated) {
                String cycleCondition;
                String cycleBody;

                pattern = Pattern.compile(cycleConditionRegex);
                cycleCondition = pattern.matcher(expression).group();

                pattern = Pattern.compile(cycleMultipleBodyRegex);
                cycleBody = pattern.matcher(expression).group();

                return isConditionCorrect(cycleCondition) && isBodyCorrect(cycleBody);
            }
        }
        return false;
    }

    private boolean isConditionCorrect(String cycleCondition) {
        return false;
    }

    private boolean isBodyCorrect(String cycleBody) {
        String[] cycleBodyLines = cycleBody.split(";");
        for (String currentLine : cycleBodyLines) {
            if (!isLineCorrect(currentLine)) {
                return false;
            }
        }
        return true;
    }

    private boolean isLineCorrect(String cycleBodyLine) {
        ActionType actionType = getLineTypeAction(cycleBodyLine);
        switch (actionType) {
            case INVOCATION:
                return true;
            case DECLARATION:
                return isDeclarationCorrect(cycleBodyLine);
            case INITIALIZATION:
                return isInitializationCorrect(cycleBodyLine, getDeclarationDataType(cycleBodyLine));
        }
        return false;
    }

    private ActionType getLineTypeAction(String cycleBodyLine) {
        return null;
    }

    private DataType getDeclarationDataType(String cycleBodyLine) {
        return null;
    }

    private boolean isDeclarationCorrect(String cycleBodyLine) {
        pattern = Pattern.compile("(.*?)".concat(allSpacesRegex).concat("="));
        String variableName = pattern.matcher(cycleBodyLine).group().split("=")[0];
        pattern = Pattern.compile(allSpacesRegex.concat("[A-Za-z]*"));
        variableName = pattern.matcher(variableName).group(1).trim();
        return iSemanticsAnalyzer.isVariableNameCorrect(variableName);
    }

    private boolean isInitializationCorrect(String cycleBodyLine, DataType dataType) {
        boolean isInitializationCorrect;
        pattern = Pattern.compile("=".concat(allSpacesRegex).concat("(.*?)").concat(allSpacesRegex).concat(";"));
        String valueAsString = pattern.matcher(cycleBodyLine).group().split("=")[1].split(";")[0].trim();
        switch (dataType) {
            case BYTE:
                isInitializationCorrect = iSemanticsAnalyzer.isByteValueCorrect(valueAsString);
                break;
            case SHORT:
                isInitializationCorrect = iSemanticsAnalyzer.isShortValueCorrect(valueAsString);
                break;
            case INTEGER:
                isInitializationCorrect = iSemanticsAnalyzer.isIntegerValueCorrect(valueAsString);
                break;
            case LONG:
                isInitializationCorrect = iSemanticsAnalyzer.isLongValueCorrect(valueAsString);
                break;
            case FLOAT:
                isInitializationCorrect = iSemanticsAnalyzer.isFloatValueCorrect(valueAsString);
                break;
            case DOUBLE:
                isInitializationCorrect = iSemanticsAnalyzer.isDoubleValueCorrect(valueAsString);
                break;
            case CHARACTER:
                isInitializationCorrect = iSemanticsAnalyzer.isCharacterValueCorrect(valueAsString);
                break;
            case BOOLEAN:
                isInitializationCorrect = iSemanticsAnalyzer.isBooleanValueCorrect(valueAsString);
                break;
            case STRING:
                isInitializationCorrect = iSemanticsAnalyzer.isStringValueCorrect(valueAsString);
                break;
            case COMPOSITE_DATA_TYPE:
                isInitializationCorrect = true;
                break;
            default:
                isInitializationCorrect = false;
                break;
        }
        return isDeclarationCorrect(cycleBodyLine) && isInitializationCorrect;
    }

    private boolean isVariableDeclared(String variableName) {
        return false;
    }
}
