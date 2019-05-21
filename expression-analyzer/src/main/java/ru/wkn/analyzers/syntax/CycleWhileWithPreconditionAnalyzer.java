package ru.wkn.analyzers.syntax;

import lombok.Getter;
import lombok.extern.java.Log;
import ru.wkn.analyzers.exceptions.ExpressionException;
import ru.wkn.analyzers.exceptions.SemanticsException;
import ru.wkn.analyzers.exceptions.messages.SemanticsErrorMessages;
import ru.wkn.analyzers.exceptions.messages.SyntaxErrorMessages;
import ru.wkn.analyzers.syntax.semantics.ISemanticsAnalyzer;
import ru.wkn.analyzers.syntax.util.ActionType;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
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
            .concat(")))*)");
    private final String variableAssignmentRegex = "(("
            .concat(anyNameCharSequenceRegex)
            .concat("((\\s*((=)|(\\+=)|(\\-=)|(\\*=)|(\\/=)|(\\|=)|(\\&=))\\s*((")
            .concat(mathExpressionRegex)
            .concat(")|(\"[^\"]*\")))|(\\+\\+)|(\\-\\-)))|(((\\+\\+)|(\\-\\-))")
            .concat(anyNameCharSequenceRegex)
            .concat("))");
    private final String variableDeclarationAndAssignmentRegex = "("
            .concat(variableDeclarationRegex)
            .concat("\\s*=\\s*((")
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
    public boolean isSyntaxCorrect(String expression) throws ExpressionException, SemanticsException {
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
                ExpressionException expressionException =
                        new ExpressionException(SyntaxErrorMessages.CONDITION_ERROR.getErrorMessage());
                log.warning(expressionException.getMessage());
                throw expressionException;
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
            return isConditionCorrect(cycleCondition, currentBodyLine) && isBodyCorrect(cycleBody, currentBodyLine);
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

    private boolean isConditionCorrect(String cycleCondition, int currentBodyLine) throws SemanticsException {
        Matcher matcher;
        int i;

        if (isSemanticsAnalyzerActivated()) {
            pattern = Pattern.compile(numberRegex);
            matcher = pattern.matcher(cycleCondition);
            i = 0;
            while (matcher.find()) {
                String currentGroup = matcher.group(i);
                if (!numberIsCorrect(currentGroup)) {
                    SemanticsException semanticsException =
                            new SemanticsException(SemanticsErrorMessages.NUMBER_FORMAT_ERROR.getErrorMessage(),
                                    currentBodyLine);
                    log.warning(semanticsException.getMessage());
                    throw semanticsException;
                }
                i++;
            }

            pattern = Pattern.compile(anyNameCharSequenceRegex);
            matcher = pattern.matcher(cycleCondition);
            i = 0;
            while (matcher.find()) {
                if (!getISemanticsAnalyzer().isVariableNameCorrect(matcher.group(i))
                        && !getISemanticsAnalyzer().isBooleanValueCorrect(matcher.group(i))) {
                    SemanticsException semanticsException =
                            new SemanticsException(SemanticsErrorMessages.VARIABLE_NAME_ERROR.getErrorMessage(),
                                    currentBodyLine);
                    log.warning(semanticsException.getMessage());
                    throw semanticsException;
                }
                i++;
            }
        }
        return true;
    }

    private boolean isBodyCorrect(String cycleBody, int currentBodyLine) throws ExpressionException, SemanticsException {
        String[] cycleBodyLines = cycleBody.split(";");
        for (String currentLine : cycleBodyLines) {
            if (!isLineCorrect(currentLine, currentBodyLine)) {
                ExpressionException expressionException =
                        new ExpressionException(SyntaxErrorMessages.LINE_ERROR.getErrorMessage()
                                .concat(String.valueOf(currentBodyLine)));
                log.warning(expressionException.getMessage());
                throw expressionException;
            }
            currentBodyLine += moveToLine(currentLine, cycleSingleBodyLineRegex);
        }
        return true;
    }

    private boolean isLineCorrect(String cycleBodyLine, int currentBodyLine) throws ExpressionException,
            SemanticsException {
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
                    return isInitializationCorrect(cycleBodyLine, currentBodyLine);
                }
                return true;
            }
            case DECLARATION_WITH_INITIALIZATION: {
                if (isSemanticsAnalyzerActivated()) {
                    return isDeclarationWithInitializationCorrect(cycleBodyLine, currentBodyLine);
                }
                return true;
            }
        }
        return false;
    }

    private ActionType getLineTypeAction(String cycleBodyLine, int currentBodyLine) throws ExpressionException {
        pattern = Pattern.compile("\\s*".concat(streamMethodOrVariableInvocationsRegex).concat("\\s*"));
        if (pattern.matcher(cycleBodyLine).matches()) {
            return ActionType.INVOCATION;
        }

        pattern = Pattern.compile("\\s*".concat(variableDeclarationRegex).concat("\\s*"));
        if (pattern.matcher(cycleBodyLine).matches()) {
            return ActionType.DECLARATION;
        }

        pattern = Pattern.compile("\\s*".concat(variableAssignmentRegex).concat("\\s*"));
        if (pattern.matcher(cycleBodyLine).matches()) {
            return ActionType.INITIALIZATION;
        }

        pattern = Pattern.compile("\\s*".concat(variableDeclarationAndAssignmentRegex).concat("\\s*"));
        if (pattern.matcher(cycleBodyLine).matches()) {
            return ActionType.DECLARATION_WITH_INITIALIZATION;
        }
        ExpressionException expressionException =
                new ExpressionException(SyntaxErrorMessages.ACTION_TYPE_ERROR.getErrorMessage()
                        .concat(String.valueOf(currentBodyLine)));
        log.warning(expressionException.getMessage());
        throw expressionException;
    }

    private boolean isDeclarationCorrect(String cycleBodyLine, int currentBodyLine) throws SemanticsException {
        pattern = Pattern.compile("\\s*[^=;]+");
        String variableName = pattern.matcher(cycleBodyLine).group();
        pattern = Pattern.compile("\\s*[A-z]+");
        variableName = pattern.matcher(variableName).group(1).trim();
        if (getISemanticsAnalyzer().isVariableNameCorrect(variableName)) {
            return true;
        } else {
            SemanticsException semanticsException =
                    new SemanticsException(SemanticsErrorMessages.VARIABLE_NAME_ERROR.getErrorMessage(),
                            currentBodyLine);
            log.warning(semanticsException.getMessage());
            throw semanticsException;
        }
    }

    private boolean isInitializationCorrect(String cycleBodyLine, int currentBodyLine) throws SemanticsException {
        boolean isInitializationCorrect = true;
        String initialization = cycleBodyLine.split("=")[1];
        pattern = Pattern.compile("\\s*.*");
        Matcher matcher = pattern.matcher(initialization);

        if (matcher.find()) {
            String valueAsString = matcher.group();

            pattern = Pattern.compile(anyNameCharSequenceRegex);
            matcher = pattern.matcher(valueAsString);
            while (matcher.find()) {
                String currentGroup = matcher.group();
                isInitializationCorrect &= (getISemanticsAnalyzer().isBooleanValueCorrect(currentGroup)
                        || getISemanticsAnalyzer().isVariableNameCorrect(currentGroup));
            }

            pattern = Pattern.compile(numberRegex);
            matcher = pattern.matcher(valueAsString);
            while (matcher.find()) {
                String currentGroup = matcher.group();
                isInitializationCorrect &= numberIsCorrect(currentGroup);
            }

            pattern = Pattern.compile("(((\")|(\'))[^\'\"]*((\")|(\')))");
            matcher = pattern.matcher(valueAsString);
            while (matcher.find()) {
                String currentGroup = matcher.group();
                isInitializationCorrect &= (getISemanticsAnalyzer().isCharacterValueCorrect(currentGroup)
                        || getISemanticsAnalyzer().isStringValueCorrect(currentGroup));
            }
        } else {
            SemanticsException semanticsException =
                    new SemanticsException(SemanticsErrorMessages.INITIALIZATION_ERROR.getErrorMessage(),
                            currentBodyLine);
            log.warning(semanticsException.getMessage());
            throw semanticsException;
        }
        if (isInitializationCorrect) {
            return true;
        } else {
            SemanticsException semanticsException =
                    new SemanticsException(SemanticsErrorMessages.INITIALIZATION_ERROR.getErrorMessage(),
                            currentBodyLine);
            log.warning(semanticsException.getMessage());
            throw semanticsException;
        }
    }

    private boolean isDeclarationWithInitializationCorrect(String cycleBodyLine, int currentBodyLine)
            throws SemanticsException {
        return isDeclarationCorrect(cycleBodyLine, currentBodyLine)
                && isInitializationCorrect(cycleBodyLine, currentBodyLine);
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

    private boolean numberIsCorrect(String currentGroup) {
        return getISemanticsAnalyzer().isByteValueCorrect(currentGroup)
                || getISemanticsAnalyzer().isShortValueCorrect(currentGroup)
                || getISemanticsAnalyzer().isIntegerValueCorrect(currentGroup)
                || getISemanticsAnalyzer().isLongValueCorrect(currentGroup)
                || getISemanticsAnalyzer().isFloatValueCorrect(currentGroup)
                || getISemanticsAnalyzer().isDoubleValueCorrect(currentGroup)
                || getISemanticsAnalyzer().isDecimalValueCorrect(currentGroup)
                || getISemanticsAnalyzer().isSbyteValueCorrect(currentGroup)
                || getISemanticsAnalyzer().isUshortValueCorrect(currentGroup)
                || getISemanticsAnalyzer().isUintegerValueCorrect(currentGroup)
                || getISemanticsAnalyzer().isUlongValueCorrect(currentGroup);
    }
}
