package ru.wkn.analyzers.syntax.util;

import ru.wkn.analyzers.exceptions.LanguageException;

public class ExpressionCompiler {

    public static String compileExpression(String expression, Language language) throws LanguageException {
        switch (language) {
            case C_SHARPE:
                return compileCSharpExpression(expression);
            default:
                throw new LanguageException("Language not found");
        }
    }

    private static String compileCSharpExpression(String expression) {
        return "using System;\n"
                .concat("using System.Collections.Generic;\n")
                .concat("using System.Linq;\n")
                .concat("using System.Text;\n")
                .concat("\n")
                .concat("namespace HelloWorld\n")
                .concat("{\n")
                .concat("\tclass Program\n")
                .concat("\t{\n")
                .concat("\t\tstatic void Main(string[] args)\n")
                .concat("\t\t{\n\t\t\t")
                .concat(expression)
                .concat("\n\t\t}\n")
                .concat("\t}\n")
                .concat("}");
    }
}
