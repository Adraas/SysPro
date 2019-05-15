package ru.wkn.jni.functions;

/**
 * Interface {@code IAssemblerFunctions} represents invocation for assembler functions.
 *
 * @author Alexey Konev
 */
public interface IAssemblerFunctions {

    /**
     * Method for the dividing.
     *
     * @param dividend - dividend for dividing function
     * @param divisor - divisor for dividing function
     * @return result {@code double} of this operation
     */
    double dividingFunction(double dividend, double divisor);

    /**
     * Method for the XOR operation.
     *
     * @param a - first value for XOR operation
     * @param b - second value for XOR operation
     * @return result {@code double} of this operation
     */
    int xorFunction(int a, int b);
}
