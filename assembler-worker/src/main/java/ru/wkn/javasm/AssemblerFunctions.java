package ru.wkn.javasm;

/**
 * Class {@code AssemblerFunctions} represent invocation for assembler functions by means of JNI.
 *
 * @author Alexey Konev
 */
public class AssemblerFunctions implements IAssemblerFunctions {

    /**
     * Method for the dividing by means of JNI.
     *
     * @param commandLine - line with separator into first position and following values
     * @return result {@code double} of this operation at the {@code String}
     */
    @Override
    public native String dividingFunction(String commandLine);

    /**
     * Method for the XOR operation by means of JNI.
     *
     * @param commandLine - line with separator into first position and following values
     * @return result {@code double} of this operation at the {@code String}
     */
    @Override
    public native String xorFunction(String commandLine);
}
