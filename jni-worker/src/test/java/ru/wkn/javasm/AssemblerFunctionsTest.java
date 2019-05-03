package ru.wkn.javasm;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
        assertEquals(0b0110, assemblerFunctions.xorFunction(0b1010, 0b1100));
    }
}