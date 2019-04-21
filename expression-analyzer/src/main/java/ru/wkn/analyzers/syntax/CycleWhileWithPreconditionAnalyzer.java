package ru.wkn.analyzers.syntax;

import ru.wkn.analyzers.DataType;
import ru.wkn.analyzers.ActionType;
import ru.wkn.analyzers.syntax.semantics.ISemanticsAnalyzer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CycleWhileWithPreconditionAnalyzer extends IExpressionAnalyzer {

    private final String cycleConditionRegex = "";
    private final String cycleSingleLineBodyRegex = "";
    private final String cycleMultipleBodyRegex = "(".concat(cycleSingleLineBodyRegex).concat(")*");
    private final String regex = "\\s*\\n*\\r*\\t*(while)\\s*\\n*\\r*\\t*\\("
            .concat(cycleConditionRegex)
            .concat("\\)\\s*\\n*\\r*\\t*((\\{")
            .concat(cycleMultipleBodyRegex)
            .concat("\\})|(").concat(cycleSingleLineBodyRegex).concat("))");

    private Pattern pattern = Pattern.compile(regex);
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
        pattern = Pattern.compile("(.*?)\\s*\\n*\\r*\\t*=");
        String variableName = pattern.matcher(cycleBodyLine).group().split("=")[0];
        pattern = Pattern.compile("\\s*\\n*\\r*\\t*[A-Za-z]*");
        variableName = pattern.matcher(variableName).group(1).trim();
        return iSemanticsAnalyzer.isVariableNameCorrect(variableName);
    }

    private boolean isInitializationCorrect(String cycleBodyLine, DataType dataType) {
        boolean isInitializationCorrect;
        pattern = Pattern.compile("=\\s*\\n*\\r*\\t*(.*?)\\s*\\n*\\r*\\t*;");
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
