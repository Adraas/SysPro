package ru.wkn.analyzers.syntax;

import lombok.Getter;
import ru.wkn.analyzers.exceptions.ExpressionException;
import ru.wkn.analyzers.exceptions.messages.ErrorMessages;
import ru.wkn.analyzers.syntax.semantics.ISemanticsAnalyzer;
import ru.wkn.analyzers.syntax.util.ActionType;
import ru.wkn.analyzers.syntax.util.CSharpeDataType;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class CycleWhileWithPreconditionAnalyzer extends ExpressionAnalyzer {

    private final String anyNameCharSequenceRegex = "(((_*[A-z])+[0-9]*)+)";
    private final String variableDeclarationRegex = "("
            .concat(anyNameCharSequenceRegex)
            .concat("\\s*")
            .concat(anyNameCharSequenceRegex)
            .concat(")");
    private final String numberRegex = "((([1-9][0-9]*)|(0))(\\.[0-9]+)?)";
    private final String singleMethodOrVariableInvocationRegex = "(("
            .concat(anyNameCharSequenceRegex)
            .concat("\\s*\\.\\s*)?")
            .concat(anyNameCharSequenceRegex)
            .concat("(\\s*(\\((\\s*(((")
            .concat(anyNameCharSequenceRegex)
            .concat(")|(")
            .concat(numberRegex)
            .concat("))\\s*)|((((")
            .concat(anyNameCharSequenceRegex)
            .concat(")|(")
            .concat(numberRegex)
            .concat("))\\s*)(,\\s*((")
            .concat(anyNameCharSequenceRegex)
            .concat(")|(")
            .concat(numberRegex)
            .concat("))\\s*)*))?\\s*\\))?))");
    private final String streamMethodOrVariableInvocationsRegex = "(("
            .concat(singleMethodOrVariableInvocationRegex)
            .concat(")(\\s*\\.\\s*")
            .concat(singleMethodOrVariableInvocationRegex)
            .concat(")*)");
    private final String mathExpressionRegex = "((("
            .concat(numberRegex)
            .concat(")|(")
            .concat(streamMethodOrVariableInvocationsRegex)
            .concat(")|(")
            .concat(anyNameCharSequenceRegex)
            .concat("))(\\s*[\\+\\-\\*\\/]\\s*((")
            .concat(numberRegex)
            .concat(")|(")
            .concat(streamMethodOrVariableInvocationsRegex)
            .concat(")|(")
            .concat(anyNameCharSequenceRegex)
            .concat(")))+)");
    private final String variableAssignmentRegex = "(("
            .concat(anyNameCharSequenceRegex)
            .concat("((\\s*((=)|(\\+=)|(\\-=)|(\\*=)|(\\/=))\\s*((")
            .concat(numberRegex)
            .concat(")|(")
            .concat(mathExpressionRegex)
            .concat(")|(")
            .concat(streamMethodOrVariableInvocationsRegex)
            .concat(")|(")
            .concat(anyNameCharSequenceRegex)
            .concat(")|(\"[^\"]*\")))|(\\+\\+)|(\\-\\-)))|(((\\+\\+)|(\\-\\-))")
            .concat(anyNameCharSequenceRegex)
            .concat("))");
    private final String variableDeclarationAndAssignmentRegex = "("
            .concat(variableDeclarationRegex)
            .concat("\\s*((=)|(\\+=)|(\\-=)|(\\*=)|(\\/=))\\s*((")
            .concat(numberRegex)
            .concat(")|(")
            .concat(mathExpressionRegex)
            .concat(")|(")
            .concat(streamMethodOrVariableInvocationsRegex)
            .concat(")|(")
            .concat(anyNameCharSequenceRegex)
            .concat(")|(\"[^\"]*\")))");
    private final String cycleSingleBodyLineRegex = "((("
            .concat(variableDeclarationAndAssignmentRegex)
            .concat(")|(")
            .concat(variableAssignmentRegex)
            .concat(")|(")
            .concat(variableDeclarationRegex)
            .concat(")|(")
            .concat(streamMethodOrVariableInvocationsRegex)
            .concat("))\\s*;)");
    private final String cycleMultipleBodyRegex = "(("
            .concat(cycleSingleBodyLineRegex)
            .concat("\\s*)*)");
    private final String comparisonOperationRegex = "((("
            .concat(anyNameCharSequenceRegex)
            .concat(")|(")
            .concat(numberRegex)
            .concat(")|(")
            .concat(streamMethodOrVariableInvocationsRegex)
            .concat("))\\s*(>|<|(>=)|(<=)|(==))\\s*((")
            .concat(anyNameCharSequenceRegex)
            .concat(")|(")
            .concat(numberRegex)
            .concat(")|(")
            .concat(streamMethodOrVariableInvocationsRegex)
            .concat(")))");
    private final String singleBooleanExpressionRegex = "((true)|(false)|("
            .concat(comparisonOperationRegex)
            .concat(")|(")
            .concat(streamMethodOrVariableInvocationsRegex)
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
            .concat("\\s*\\)\\s*((\\{\\s*(")
            .concat(cycleMultipleBodyRegex)
            .concat(")?\\})|(")
            .concat(cycleSingleBodyLineRegex)
            .concat("))\\s*)");

    private Pattern pattern = Pattern.compile(cycleWhileWithPreconditionRegex);

    public CycleWhileWithPreconditionAnalyzer(ISemanticsAnalyzer iSemanticsAnalyzer,
                                              boolean isSemanticsAnalyzerActivated) {
        super(iSemanticsAnalyzer, isSemanticsAnalyzerActivated);
    }

    @Override
    public boolean isSyntaxCorrect(String expression) throws ExpressionException {
        Matcher matcher = pattern.matcher(expression);
        if (expression.trim().isEmpty()) {
            return true;
        }
        if (!matcher.matches()) {
            String regex = "\\(\\s*"
                    .concat(cycleConditionRegex)
                    .concat("\\s*\\)");
            String cycleCondition = getElementOfExpression(expression, regex);
            if (cycleCondition.isEmpty()) {
                throw new ExpressionException(ErrorMessages.CONDITION_ERROR.getErrorMessage());
            }
            cycleCondition = cycleCondition.substring(1, cycleCondition.length() - 1);

            String cycleBody = getElementOfExpression(expression,"((\\{\\s*("
                    .concat(cycleMultipleBodyRegex)
                    .concat("\\s*)?\\})|(")
                    .concat(cycleSingleBodyLineRegex)
                    .concat("))"));
            if (!cycleBody.isEmpty()) {
                cycleBody = cycleBody.substring(1, cycleBody.length() - 1);
            } else {
                cycleBody = expression.split(regex.concat("\\s*"))[1];
            }

            int currentBodyLine = 1;
            currentBodyLine += moveToLine(expression, "\\s*") + moveToLine(expression, cycleConditionRegex);
            return isConditionCorrect(cycleCondition) && isBodyCorrect(cycleBody, currentBodyLine);
        }
        return true;
    }

    private String getElementOfExpression(String expression, String regex) {
        pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(expression);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    private boolean isConditionCorrect(String cycleCondition) throws ExpressionException {
        Matcher matcher;
        int i;

        pattern = Pattern.compile(numberRegex);
        matcher = pattern.matcher(cycleCondition);
        i = 0;
        while (matcher.find()) {
            if (!getISemanticsAnalyzer().isByteValueCorrect(matcher.group(i))
                    && !getISemanticsAnalyzer().isShortValueCorrect(matcher.group(i))
                    && !getISemanticsAnalyzer().isIntegerValueCorrect(matcher.group(i))
                    && !getISemanticsAnalyzer().isLongValueCorrect(matcher.group(i))
                    && !getISemanticsAnalyzer().isFloatValueCorrect(matcher.group(i))
                    && !getISemanticsAnalyzer().isDoubleValueCorrect(matcher.group(i))) {
                throw new ExpressionException(ErrorMessages.CONDITION_ERROR.getErrorMessage());
            }
            i++;
        }

        pattern = Pattern.compile(anyNameCharSequenceRegex);
        matcher = pattern.matcher(cycleCondition);
        i = 0;
        while (matcher.find()) {
            if (!getISemanticsAnalyzer().isVariableNameCorrect(matcher.group(i))
                    && !getISemanticsAnalyzer().isBooleanValueCorrect(matcher.group(i))) {
                throw new ExpressionException(ErrorMessages.CONDITION_ERROR.getErrorMessage());
            }
            i++;
        }
        return true;
    }

    private boolean isBodyCorrect(String cycleBody, int currentBodyLine) throws ExpressionException {
        String[] cycleBodyLines = cycleBody.split(";");
        for (String currentLine : cycleBodyLines) {
            if (!isLineCorrect(currentLine, currentBodyLine)) {
                throw new ExpressionException(ErrorMessages.LINE_ERROR.getErrorMessage()
                        .concat(String.valueOf(currentBodyLine)));
            }
            currentBodyLine += moveToLine(currentLine, cycleSingleBodyLineRegex);
        }
        return true;
    }

    private boolean isLineCorrect(String cycleBodyLine, int currentBodyLine) throws ExpressionException {
        ActionType actionType = getLineTypeAction(cycleBodyLine, currentBodyLine);
        switch (Objects.requireNonNull(actionType)) {
            case INVOCATION:
                return true;
            case DECLARATION: {
                if (isSemanticsAnalyzerActivated()) {
                    return isDeclarationCorrect(cycleBodyLine, currentBodyLine);
                }
                return true;
            }
            case INITIALIZATION: {
                if (isSemanticsAnalyzerActivated()) {
                    return isInitializationCorrect(cycleBodyLine, getDeclarationDataType(cycleBodyLine),
                            currentBodyLine);
                }
                return true;
            }
            case DECLARATION_WITH_INITIALIZATION: {
                if (isSemanticsAnalyzerActivated()) {
                    return isDeclarationWithInitializationCorrect(cycleBodyLine, getDeclarationDataType(cycleBodyLine),
                            currentBodyLine);
                }
                return true;
            }
        }
        return false;
    }

    // TODO: check and fix patterns for action types definition
    private ActionType getLineTypeAction(String cycleBodyLine, int currentBodyLine) throws ExpressionException {
        pattern = Pattern.compile(streamMethodOrVariableInvocationsRegex);
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
        throw new ExpressionException(ErrorMessages.ACTION_TYPE_ERROR.getErrorMessage()
                .concat(String.valueOf(currentBodyLine)));
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

    private boolean isDeclarationCorrect(String cycleBodyLine, int currentBodyLine) throws ExpressionException {
        pattern = Pattern.compile("\\s*[^=;]+");
        String variableName = pattern.matcher(cycleBodyLine).group();
        pattern = Pattern.compile("\\s*[A-z]+");
        variableName = pattern.matcher(variableName).group(1).trim();
        if (getISemanticsAnalyzer().isVariableNameCorrect(variableName)) {
            return true;
        } else {
            throw new ExpressionException(ErrorMessages.VARIABLE_DECLARATION_ERROR.getErrorMessage()
                    .concat(String.valueOf(currentBodyLine)));
        }
    }

    private boolean isInitializationCorrect(String cycleBodyLine, CSharpeDataType CSharpeDataType, int currentBodyLine)
            throws ExpressionException {
        boolean isInitializationCorrect;
        pattern = Pattern.compile("=\\s*[^;]+\\s*;");
        String valueAsString = pattern.matcher(cycleBodyLine).group().trim().split("=")[1].split(";")[0];
        switch (CSharpeDataType) {
            case BYTE:
                isInitializationCorrect = getISemanticsAnalyzer().isByteValueCorrect(valueAsString);
                break;
            case SHORT:
                isInitializationCorrect = getISemanticsAnalyzer().isShortValueCorrect(valueAsString);
                break;
            case INTEGER:
                isInitializationCorrect = getISemanticsAnalyzer().isIntegerValueCorrect(valueAsString);
                break;
            case LONG:
                isInitializationCorrect = getISemanticsAnalyzer().isLongValueCorrect(valueAsString);
                break;
            case FLOAT:
                isInitializationCorrect = getISemanticsAnalyzer().isFloatValueCorrect(valueAsString);
                break;
            case DOUBLE:
                isInitializationCorrect = getISemanticsAnalyzer().isDoubleValueCorrect(valueAsString);
                break;
            case DECIMAL:
                isInitializationCorrect = getISemanticsAnalyzer().isDecimalValueCorrect(valueAsString);
                break;
            case SBYTE:
                isInitializationCorrect = getISemanticsAnalyzer().isSbyteValueCorrect(valueAsString);
                break;
            case USHORT:
                isInitializationCorrect = getISemanticsAnalyzer().isUshortValueCorrect(valueAsString);
                break;
            case UINTEGER:
                isInitializationCorrect = getISemanticsAnalyzer().isUintegerValueCorrect(valueAsString);
                break;
            case ULONG:
                isInitializationCorrect = getISemanticsAnalyzer().isUlongValueCorrect(valueAsString);
                break;
            case CHARACTER:
                isInitializationCorrect = getISemanticsAnalyzer().isCharacterValueCorrect(valueAsString);
                break;
            case BOOLEAN:
                isInitializationCorrect = getISemanticsAnalyzer().isBooleanValueCorrect(valueAsString);
                break;
            case STRING:
                isInitializationCorrect = getISemanticsAnalyzer().isStringValueCorrect(valueAsString);
                break;
            case COMPOSITE_DATA_TYPE:
                isInitializationCorrect = true;
                break;
            default: {
                throw new ExpressionException(ErrorMessages.DATA_TYPE_ERROR.getErrorMessage()
                        .concat(String.valueOf(currentBodyLine)));
            }
        }
        return isInitializationCorrect;
    }

    private boolean isDeclarationWithInitializationCorrect(String cycleBodyLine, CSharpeDataType CSharpeDataType,
                                                           int currentBodyLine) throws ExpressionException {
        return isDeclarationCorrect(cycleBodyLine, currentBodyLine)
                && isInitializationCorrect(cycleBodyLine, CSharpeDataType, currentBodyLine);
    }

    private int moveToLine(String subexpression, String pattern) {
        int currentBodyLine = 0;
        this.pattern = Pattern.compile(pattern);
        Matcher matcher = this.pattern.matcher(subexpression);
        if (matcher.find()) {
            char[] subSubexpression = matcher.group().toCharArray();
            for (char currentChar : subSubexpression) {
                if (currentChar == '\n') {
                    currentBodyLine++;
                }
            }
        }
        return currentBodyLine;
    }
}
