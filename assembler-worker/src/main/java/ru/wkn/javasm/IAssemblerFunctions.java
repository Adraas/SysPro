package ru.wkn.javasm;

/**
 * Interface {@code IAssemblerFunctions} represent invocation for assembler functions.
 * Methods receive parameters at the single line of {@code String} with separator into first position of this line.
 *
 * @author Alexey Konev
 */
public interface IAssemblerFunctions {

    /**
     * Method for the dividing.
     *
     * @param commandLine - line with separator into first position and following values
     * @return result {@code double} of this operation at the {@code String}
     */
    String dividingFunction(String commandLine);

    /**
     * Method for the XOR operation.
     *
     * @param commandLine - line with separator into first position and following values
     * @return result {@code double} of this operation at the {@code String}
     */
    String xorFunction(String commandLine);
}
