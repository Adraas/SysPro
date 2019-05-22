package ru.wkn.jni.functions;

/**
 * The interface {@code IAssemblerFunctions} represents invocation for assembler functions.
 *
 * @author Artem Pikalov
 * @author Alexey Konev
 */
public interface IAssemblerFunctions {

    /**
     * Method for the dividing.
     *
     * @param dividend dividend for dividing function
     * @param divisor divisor for dividing function
     * @return result of {@code double} type of this operation
     */
    double dividingFunction(double dividend, double divisor);

    /**
     * Method for the XOR operation.
     *
     * @param a first value for XOR operation
     * @param b second value for XOR operation
     * @return result of {@code double} type of this operation
     */
    int xorFunction(int a, int b);

    /**
     * Method for the multiplication operation.
     *
     * @param firstMultiplier first multiplier for multiplication operation
     * @param secondMultiplier second multiplier for multiplication operation
     * @return result of {@code int} type of this operation
     */
    int multiplicationFunction(int firstMultiplier, int secondMultiplier);
}
