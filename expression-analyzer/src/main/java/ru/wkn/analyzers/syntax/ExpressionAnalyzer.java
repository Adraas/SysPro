package ru.wkn.analyzers.syntax;

import org.codehaus.plexus.compiler.AbstractCompiler;
import org.codehaus.plexus.compiler.Compiler;
import org.codehaus.plexus.compiler.CompilerConfiguration;
import org.codehaus.plexus.compiler.CompilerException;
import org.codehaus.plexus.compiler.CompilerMessage;
import org.codehaus.plexus.compiler.CompilerResult;
import ru.wkn.analyzers.exceptions.CompilationException;
import ru.wkn.analyzers.exceptions.LanguageException;
import ru.wkn.analyzers.syntax.util.ExpressionCompiler;
import ru.wkn.analyzers.syntax.util.Language;
import ru.wkn.analyzers.util.CompilerStatus;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExpressionAnalyzer {

    private Language language;
    private Compiler compiler;

    public ExpressionAnalyzer(Language language, AbstractCompiler compiler) {
        this.language = language;
        this.compiler = compiler;
    }

    public List<String> compile(String expression, String tempSourcePathname, String outputFilename, String compilerPath)
            throws CompilationException, LanguageException {
        CompilerConfiguration compilerConfiguration = performCompile(expression, tempSourcePathname, outputFilename);
        CompilerResult compilerResult;
        List<String> compilerStringMessages = new ArrayList<>();
        try {
            String[] commandLine = compiler.createCommandLine(compilerConfiguration);
            commandLine = getCompilerCommandLine(commandLine, compilerPath);
            compilerResult = compiler.performCompile(compilerConfiguration);
            if (compilerResult.isSuccess()) {
                compilerStringMessages.add(CompilerStatus.PERFORM_SUCCESSFULLY.getCompilerMessage());
                compilerStringMessages.add(getCompilerMessage(commandLine));
                return compilerStringMessages;
            } else {
                compilerStringMessages.add(CompilerStatus.PERFORM_UNSUCCESSFULLY.getCompilerMessage());
                List<CompilerMessage> compilerMessages = compilerResult.getCompilerMessages();
                for (CompilerMessage compilerMessage : compilerMessages) {
                    compilerStringMessages.add(compilerMessage.getMessage());
                }
                return compilerStringMessages;
            }
        } catch (CompilerException | IOException e) {
            String message = "";
            for (String currentMessage : compilerStringMessages) {
                message = message.concat(currentMessage).concat("\n");
            }
            throw new CompilationException(message, e);
        }
    }

    private CompilerConfiguration performCompile(String expression, String tempSourcePathname, String outputFilename)
            throws CompilationException, LanguageException {
        String resultSource = ExpressionCompiler.compileExpression(expression, language);
        Set<File> sources = prepareSourceFiles(resultSource, tempSourcePathname);
        return prepareCompileConfiguration(sources, outputFilename);
    }

    private Set<File> prepareSourceFiles(String resultSource, String tempSourcePathname) throws CompilationException {
        Path path = Paths.get(tempSourcePathname);
        BufferedWriter bufferedWriter;
        try {
            bufferedWriter = Files.newBufferedWriter(path, Charset.forName("UTF-8"));
            bufferedWriter.write(resultSource);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new CompilationException(e.getMessage(), e);
        }
        Set<File> sources = new HashSet<>();
        sources.add(new File(tempSourcePathname));
        return sources;
    }

    private CompilerConfiguration prepareCompileConfiguration(Set<File> sources, String outputFilename) {
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setSourceFiles(sources);
        compilerConfiguration.setOutputLocation("");
        compilerConfiguration.setOutputFileName(outputFilename);
        compilerConfiguration.setVerbose(true);
        return compilerConfiguration;
    }

    private String getCompilerMessage(String[] commandLine) throws IOException, CompilationException,
            LanguageException {
        ProcessBuilder processBuilder = new ProcessBuilder(commandLine);
        Process process = processBuilder.redirectErrorStream(true).start();
        InputStream inputStream = process.getInputStream();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        byte[] compileMessageAsBytes = new byte[inputStream.available()];
        int result = inputStream.read(compileMessageAsBytes);
        String compileMessage = "";
        if (result != -1) {
            compileMessage = new String(compileMessageAsBytes);
        }
        process.getOutputStream().close();
        compileMessage = ErrorMessageGenerator.generateErrorMessage(compileMessage, language);
        if (!compileMessage.trim().isEmpty()) {
            throw new CompilationException(compileMessage, new CompilerException(compileMessage));
        }
        return compileMessage;
    }

    private String[] getCompilerCommandLine(String[] commandLine, String compilerPath) {
        String[] compilerCommandLine = new String[commandLine.length + 1];
        compilerCommandLine[0] = compilerPath;
        System.arraycopy(commandLine, 0, compilerCommandLine, 1, compilerCommandLine.length - 1);
        return compilerCommandLine;
    }
}
