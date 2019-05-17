package ru.wkn.analyzers.syntax;

import lombok.Getter;
import ru.wkn.analyzers.exceptions.ExpressionException;
import ru.wkn.analyzers.syntax.semantics.ISemanticsAnalyzer;
import ru.wkn.analyzers.syntax.util.ActionType;
import ru.wkn.analyzers.syntax.util.CSharpeDataType;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class CycleWhileWithPreconditionAnalyzer extends ExpressionAnalyzer {

    private final String allSpacesRegex = "\\s";
    private final String anyNameCharSequenceRegex = "(((_*[A-z])+[0-9]*)+)";
    private final String variableDeclarationRegex = "("
            .concat(anyNameCharSequenceRegex)
            .concat("\\s*")
            .concat(anyNameCharSequenceRegex)
            .concat(")");
    private final String numberRegex = "((([1-9][0-9]*)|(0))(\\.[0-9]+)?)";
    private final String singleMethodInvocationRegex = "("
            .concat(anyNameCharSequenceRegex)
            .concat("\\s*\\.\\s*)?")
            .concat(anyNameCharSequenceRegex)
            .concat("(\\s*\\((\\s*(")
            .concat(anyNameCharSequenceRegex)
            .concat("\\s*)|((")
            .concat(anyNameCharSequenceRegex)
            .concat("\\s*)(,\\s*")
            .concat(anyNameCharSequenceRegex)
            .concat("\\s*)*))?\\s*\\))");
    private final String streamMethodInvocationsRegex = "(("
            .concat(singleMethodInvocationRegex)
            .concat(")(\\s*\\.\\s*")
            .concat(singleMethodInvocationRegex)
            .concat(")*)");
    private final String variableAssignmentRegex = "("
            .concat(anyNameCharSequenceRegex)
            .concat("\\s*=\\s*((")
            .concat(numberRegex)
            .concat(")|(")
            .concat(streamMethodInvocationsRegex)
            .concat(")|(")
            .concat(anyNameCharSequenceRegex)
            .concat(")|(\".*\")))");
    private final String variableDeclarationAndAssignmentRegex = "("
            .concat(variableDeclarationRegex)
            .concat("\\s*=\\s*((")
            .concat(numberRegex)
            .concat(")|(")
            .concat(streamMethodInvocationsRegex)
            .concat(")|(")
            .concat(anyNameCharSequenceRegex)
            .concat(")|(\".*\")))");
    private final String cycleSingleLineBodyRegex = "((("
            .concat(variableDeclarationAndAssignmentRegex)
            .concat(")|(")
            .concat(variableAssignmentRegex)
            .concat(")|(")
            .concat(variableDeclarationRegex)
            .concat(")|(")
            .concat(streamMethodInvocationsRegex)
            .concat("))\\s*;)");
    private final String cycleMultipleBodyRegex = "(("
            .concat(cycleSingleLineBodyRegex)
            .concat(")*)");
    private final String comparisonOperationRegex = "((("
            .concat(anyNameCharSequenceRegex)
            .concat(")|(")
            .concat(numberRegex)
            .concat(")|(")
            .concat(streamMethodInvocationsRegex)
            .concat("))\\s*(>|<|(>=)|(<=)|(==))\\s*((")
            .concat(anyNameCharSequenceRegex)
            .concat(")|(")
            .concat(numberRegex)
            .concat(")|(")
            .concat(streamMethodInvocationsRegex)
            .concat(")))");
    private final String singleBooleanExpressionRegex = "((true)|(false)|("
            .concat(comparisonOperationRegex)
            .concat(")|(")
            .concat(streamMethodInvocationsRegex)
            .concat(")|(")
            .concat(anyNameCharSequenceRegex)
            .concat("))");
    private final String cycleConditionRegex = "("
            .concat(singleBooleanExpressionRegex)
            .concat("(\\s*((&&)|(\\|\\|))\\s*")
            .concat(singleBooleanExpressionRegex)
            .concat(")*)");
    private final String cycleWhileWithPreconditionRegex = "(\\s*(while)\\s*\\(\\s*"
            .concat(cycleConditionRegex)
            .concat("\\s*\\)\\s*((\\{\\s*")
            .concat(cycleMultipleBodyRegex)
            .concat("\\s*\\})|(")
            .concat(cycleSingleLineBodyRegex)
            .concat("))\\s*)");

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
    public boolean isSyntaxCorrect(String expression) throws ExpressionException {
        return isSyntaxCorrect(expression, isSemanticsAnalyzerActivated);
    }

    @Override
    public boolean isSyntaxCorrect(String expression, boolean isSemanticsAnalyzerActivated) throws ExpressionException {
        Matcher matcher = pattern.matcher(expression);
        if (matcher.matches()) {
            if (isSemanticsAnalyzerActivated) {
                String cycleCondition;
                String cycleBody;

                pattern = Pattern.compile(cycleConditionRegex);
                cycleCondition = pattern.matcher(expression).group();

                pattern = Pattern.compile(cycleMultipleBodyRegex);
                cycleBody = pattern.matcher(expression).group();
                return isConditionCorrect(cycleCondition) && isBodyCorrect(cycleBody);
            }
            return true;
        }
        throw new ExpressionException("syntax is incorrect");
    }

    private boolean isConditionCorrect(String cycleCondition) throws ExpressionException {
        Matcher matcher;
        int i;

        pattern = Pattern.compile(numberRegex);
        matcher = pattern.matcher(cycleCondition);
        i = 0;
        while (matcher.find()) {
            if (!iSemanticsAnalyzer.isByteValueCorrect(matcher.group(i))
                    && !iSemanticsAnalyzer.isShortValueCorrect(matcher.group(i))
                    && !iSemanticsAnalyzer.isIntegerValueCorrect(matcher.group(i))
                    && !iSemanticsAnalyzer.isLongValueCorrect(matcher.group(i))
                    && !iSemanticsAnalyzer.isFloatValueCorrect(matcher.group(i))
                    && !iSemanticsAnalyzer.isDoubleValueCorrect(matcher.group(i))) {
                throw new ExpressionException("cycle's condition is incorrect");
            }
            i++;
        }

        pattern = Pattern.compile(anyNameCharSequenceRegex);
        matcher = pattern.matcher(cycleCondition);
        i = 0;
        while (matcher.find()) {
            if (!iSemanticsAnalyzer.isVariableNameCorrect(matcher.group(i))
                    && !iSemanticsAnalyzer.isBooleanValueCorrect(matcher.group(i))) {
                throw new ExpressionException("cycle's condition is incorrect");
            }
            i++;
        }
        return true;
    }

    private boolean isBodyCorrect(String cycleBody) throws ExpressionException {
        String[] cycleBodyLines = cycleBody.split(";");
        for (String currentLine : cycleBodyLines) {
            if (!isLineCorrect(currentLine)) {
                throw new ExpressionException("cycle's body is incorrect");
            }
        }
        return true;
    }

    private boolean isLineCorrect(String cycleBodyLine) throws ExpressionException {
        ActionType actionType = getLineTypeAction(cycleBodyLine);
        switch (Objects.requireNonNull(actionType)) {
            case INVOCATION:
                return true;
            case DECLARATION:
                return isDeclarationCorrect(cycleBodyLine);
            case INITIALIZATION:
                return isInitializationCorrect(cycleBodyLine, getDeclarationDataType(cycleBodyLine));
            case DECLARATION_WITH_INITIALIZATION:
                return isDeclarationWithInitializationCorrect(cycleBodyLine, getDeclarationDataType(cycleBodyLine));
        }
        return false;
    }

    private ActionType getLineTypeAction(String cycleBodyLine) throws ExpressionException {
        pattern = Pattern.compile(streamMethodInvocationsRegex);
        if (pattern.matcher(cycleBodyLine).matches()) {
            return ActionType.INVOCATION;
        }

        pattern = Pattern.compile(variableDeclarationRegex);
        if (pattern.matcher(cycleBodyLine).matches()) {
            return ActionType.DECLARATION;
        }

        pattern = Pattern.compile(variableAssignmentRegex);
        if (pattern.matcher(cycleBodyLine).matches()) {
            return ActionType.INITIALIZATION;
        }

        pattern = Pattern.compile(variableDeclarationAndAssignmentRegex);
        if (pattern.matcher(cycleBodyLine).matches()) {
            return ActionType.DECLARATION_WITH_INITIALIZATION;
        }
        throw new ExpressionException("unknown action type");
    }

    private CSharpeDataType getDeclarationDataType(String cycleBodyLine) {
        pattern = Pattern.compile("\\s*(\\S+)");
        String dataType = pattern.matcher(cycleBodyLine).group().trim().toLowerCase();
        CSharpeDataType[] CSharpeDataTypes = CSharpeDataType.values();
        for (CSharpeDataType currentCSharpeDataType : CSharpeDataTypes) {
            if (currentCSharpeDataType.getDataType().equals(dataType)) {
                return currentCSharpeDataType;
            }
        }
        return CSharpeDataType.COMPOSITE_DATA_TYPE;
    }

    private boolean isDeclarationCorrect(String cycleBodyLine) throws ExpressionException {
        pattern = Pattern.compile("\\s*[^=;]+");
        String variableName = pattern.matcher(cycleBodyLine).group();
        pattern = Pattern.compile("\\s*[A-z]+");
        variableName = pattern.matcher(variableName).group(1).trim();
        if (iSemanticsAnalyzer.isVariableNameCorrect(variableName)) {
            return true;
        } else {
            throw new ExpressionException("variable declaration is incorrect");
        }
    }

    private boolean isInitializationCorrect(String cycleBodyLine, CSharpeDataType CSharpeDataType) throws ExpressionException {
        boolean isInitializationCorrect;
        pattern = Pattern.compile("=\\s*[^;]+\\s*;");
        String valueAsString = pattern.matcher(cycleBodyLine).group().trim().split("=")[1].split(";")[0];
        switch (CSharpeDataType) {
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
            case DECIMAL:
                isInitializationCorrect = iSemanticsAnalyzer.isDecimalValueCorrect(valueAsString);
                break;
            case SBYTE:
                isInitializationCorrect = iSemanticsAnalyzer.isSbyteValueCorrect(valueAsString);
                break;
            case USHORT:
                isInitializationCorrect = iSemanticsAnalyzer.isUshortValueCorrect(valueAsString);
                break;
            case UINTEGER:
                isInitializationCorrect = iSemanticsAnalyzer.isUintegerValueCorrect(valueAsString);
                break;
            case ULONG:
                isInitializationCorrect = iSemanticsAnalyzer.isUlongValueCorrect(valueAsString);
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
            default: {
                throw new ExpressionException("unknown data type");
            }
        }
        return isInitializationCorrect;
    }

    private boolean isDeclarationWithInitializationCorrect(String cycleBodyLine, CSharpeDataType CSharpeDataType)
            throws ExpressionException {
        return isDeclarationCorrect(cycleBodyLine) && isInitializationCorrect(cycleBodyLine, CSharpeDataType);
    }
}
