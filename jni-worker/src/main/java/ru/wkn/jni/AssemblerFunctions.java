package ru.wkn.jni;

import org.scijava.nativelib.NativeLoader;

import java.io.IOException;

/**
 * Class {@code AssemblerFunctions} represent invocation for assembler functions by means of JNI.
 *
 * @see IAssemblerFunctions
 * @author Alexey Konev
 */
public class AssemblerFunctions implements IAssemblerFunctions {

    static {
        try {
            NativeLoader.loadLibrary("jni_math.dll");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
