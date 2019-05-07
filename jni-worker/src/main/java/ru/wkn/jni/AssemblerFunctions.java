package ru.wkn.jni;

/**
 * Class {@code AssemblerFunctions} represent invocation for assembler functions by means of JNI.
 *
 * @see IAssemblerFunctions
 * @author Alexey Konev
 */
public class AssemblerFunctions implements IAssemblerFunctions {

    static {
        NarSystem.loadLibrary();
    }

    /**
     * Method for the dividing by means of JNI.
     *
     * @see IAssemblerFunctions#dividingFunction(double, double)
     */
    @Override
    public native double dividingFunction(double dividend, double divisor);

    /**
     * Method for the XOR operation by means of JNI.
     *
     * @see IAssemblerFunctions#xorFunction(int, int)
     */
    @Override
    public native int xorFunction(int a, int b);
}
