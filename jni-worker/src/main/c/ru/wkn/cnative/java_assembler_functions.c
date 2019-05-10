#include "ru_wkn_jni_AssemblerFunctions.h"
#include <string.h>

/*
 * Class:     ru_wkn_javasm_AssemblerFunctions
 * Method:    dividingFunction
 * Signature: (DD)D
 */
JNIEXPORT jdouble JNICALL Java_ru_wkn_javasm_AssemblerFunctions_dividingFunction
  (JNIEnv * jenv, jobject jobj, jdouble dividend, jdouble divisor)
{
    double result;
    __asm {
        fld divisor
        fld dividend
        fdiv st(0), st(1)
        fst result
    }
    return result;
}

/*
 * Class:     ru_wkn_javasm_AssemblerFunctions
 * Method:    xorFunction
 * Signature: (DD)D
 */
JNIEXPORT jint JNICALL Java_ru_wkn_jni_AssemblerFunctions_xorFunction
  (JNIEnv * jenv, jobject jobj, jint a, jint b)
{
    int result;
    __asm {
        Mov eax, a
        Xor eax, b
        Mov result, eax
    }
    return result;
}