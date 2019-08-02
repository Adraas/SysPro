package ru.wkn.jni;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.wkn.jni.functions.AssemblerFunctions;
import ru.wkn.jni.functions.IAssemblerFunctions;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AssemblerFunctionsTest {

    private static IAssemblerFunctions assemblerFunctions;

    @BeforeAll
    static void initJniFunctionsManager() {
        assemblerFunctions = new AssemblerFunctions();
    }

    @Test
    void testNativeDividingFunction() {
        assertEquals(2, assemblerFunctions.dividingFunction(6, 3));
    }

    @Test
    void testNativeXorFunction() {
        assertEquals(6, assemblerFunctions.xorFunction(10, 12));
    }

    @Test
    void testNativeMultiplicationFunction() {
        assertEquals(6, assemblerFunctions.multiplicationFunction(2, 3));
    }
}
