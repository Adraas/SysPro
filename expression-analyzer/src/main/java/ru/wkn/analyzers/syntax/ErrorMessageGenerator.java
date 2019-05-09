package ru.wkn.analyzers.syntax;

import ru.wkn.analyzers.exceptions.LanguageException;
import ru.wkn.analyzers.syntax.util.Language;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ErrorMessageGenerator {

    public static String generateErrorMessage(String compileMessage, Language language) throws LanguageException {
        switch (language) {
            case C_SHARPE:
                return generateCSharpErrorMessage(compileMessage);
            default:
                throw new LanguageException("Language not found");
        }
    }

    private static String generateCSharpErrorMessage(String compileMessage) {
        if (compileMessage.contains("error")) {
            Pattern pattern = Pattern.compile("\\S+\\.cs.*");
            Matcher matcher = pattern.matcher(compileMessage);
            if (matcher.find()) {
                return matcher.group();
            }
        }
        return "";
    }
}
