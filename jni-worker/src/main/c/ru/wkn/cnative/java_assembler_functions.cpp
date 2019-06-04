#include "ru_wkn_jni_AssemblerFunctions.h"
#include <string.h>

/*
 * Class:     ru_wkn_jni_functions_AssemblerFunctions
 * Method:    dividingFunction
 * Signature: (DD)D
 */
extern "C" __declspec(dllexport) JNIEXPORT jdouble JNICALL Java_ru_wkn_jni_functions_AssemblerFunctions_dividingFunction
  (JNIEnv * jenv, jobject jobj, jdouble dividend, jdouble divisor)
{
    double result;
    asm(
        "fld %[dd]\n\t"
        "fdiv %[dr]\n\t"
        "fstp %[res]\n\t"
        : [res] "=m" (result)
        : [dr] "m" (divisor), [dd] "m" (dividend)
        : "cc");
    return result;
}

/*
 * Class:     ru_wkn_jni_functions_AssemblerFunctions
 * Method:    xorFunction
 * Signature: (II)I
 */
extern "C" __declspec(dllexport) JNIEXPORT jint JNICALL Java_ru_wkn_jni_functions_AssemblerFunctions_xorFunction
  (JNIEnv * jenv, jobject jobj, jint a, jint b)
{
    asm(
        "mov %[a], %%eax\n\t"
        "xor %%eax, %[b]\n\t"
        : "=m" (b)
        : [a] "m" (a), [b] "m" (b)
        : "cc");
    return b;
}

/*
 * Class:     ru_wkn_jni_functions_AssemblerFunctions
 * Method:    multiplicationOperation
 * Signature: (II)I
 */
extern "C" __declspec(dllexport) JNIEXPORT jint JNICALL Java_ru_wkn_jni_functions_AssemblerFunctions_multiplicationFunction
  (JNIEnv * jenv, jobject jobj, jint firstMultiplier, jint secondMultiplier)
{
    int result;
    asm(
        "mov %[fm], %%eax\n\t"
        "mov %[sm], %%ebx\n\t"
        "mul %%ebx\n\t"
        "mov %%eax, %[res]\n\t"
        : [res] "=m" (result)
        : [fm] "m" (firstMultiplier),
        [sm] "m" (secondMultiplier)
        : "cc");
    return result;
}