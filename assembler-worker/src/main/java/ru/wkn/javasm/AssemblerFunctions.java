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
     * @param dividend - dividend for dividing function
     * @param divisor - divisor for dividing function
     * @return result {@code double} of this operation
     */
    @Override
    public native double dividingFunction(double dividend, double divisor);

    /**
     * Method for the XOR operation by means of JNI.
     *
     * @param a - first value for XOR operation
     * @param b - second value for XOR operation
     * @return result {@code double} of this operation
     */
    @Override
    public native double xorFunction(double a, double b);
}
