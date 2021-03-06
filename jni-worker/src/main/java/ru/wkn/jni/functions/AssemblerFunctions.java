package ru.wkn.jni.functions;

/**
 * The class {@code AssemblerFunctions} represents invocation for assembler functions by means of JNI
 * and implements {@code IAssemblerFunctions}.
 *
 * @see IAssemblerFunctions
 * @author Artem Pikalov
 * @author Alexey Konev
 */
public class AssemblerFunctions implements IAssemblerFunctions {

    static {
        NarSystem.loadLibrary();
    }

    /**
     * @see IAssemblerFunctions#dividingFunction(double, double)
     */
    @Override
    public native double dividingFunction(double dividend, double divisor);

    /**
     * @see IAssemblerFunctions#xorFunction(int, int)
     */
    @Override
    public native int xorFunction(int a, int b);

    /**
     * @see IAssemblerFunctions#multiplicationFunction(int, int)
     */
    @Override
    public native int multiplicationFunction(int firstMultiplier, int secondMultiplier);
}
