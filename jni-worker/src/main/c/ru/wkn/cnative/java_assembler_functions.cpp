#include "ru_wkn_jni_AssemblerFunctions.h"
#include <string.h>

/*
 * Class:     ru_wkn_javasm_AssemblerFunctions
 * Method:    dividingFunction
 * Signature: (DD)D
 */
extern "C" __declspec(dllexport) JNIEXPORT jdouble JNICALL Java_ru_wkn_javasm_AssemblerFunctions_dividingFunction
  (JNIEnv * jenv, jobject jobj, jdouble dividend, jdouble divisor)
{
    double result;
    asm(
        "fld %[dr]\n\t"
        "fld %[dd]\n\t"
        "fdiv %%st(0), %%st(1)\n\t"
        "fst %[res]\n\t"
        : [res] "=m" (result)
        : [dr] "m" (divisor),
        [dd] "m" (dividend)
        : "cc");
    return result;
}

/*
 * Class:     ru_wkn_javasm_AssemblerFunctions
 * Method:    xorFunction
 * Signature: (II)I
 */
extern "C" __declspec(dllexport) JNIEXPORT jint JNICALL Java_ru_wkn_jni_AssemblerFunctions_xorFunction
  (JNIEnv * jenv, jobject jobj, jint a, jint b)
{
    int result;
    asm(
        "mov %%eax, %[a]\n\t"
        "mov %%eax, %[b]\n\t"
        "mov %[res], %%eax\n\t"
        : [res] "=m" (result)
        : [a] "m" (a),
        [b] "m" (b)
        : "cc");
    return result;
}

/*
 * Class:     ru_wkn_javasm_AssemblerFunctions
 * Method:    multiplicationOperation
 * Signature: (II)I
 */
extern "C" __declspec(dllexport) JNIEXPORT jint JNICALL Java_ru_wkn_jni_AssemblerFunctions_multiplicationOperation
  (JNIEnv * jenv, jobject jobj, jint firstMultiplier, jint secondMultiplier)
{
    int result;
    asm(
        "mov %%ax, %[fm]\n\t"
        "mov %%bx, %[sm]\n\t"
        "mul %%bx\n\t"
        "mov %[res], %%ax\n\t"
        : [res] "=m" (result)
        : [fm] "m" (firstMultiplier),
        [sm] "m" (secondMultiplier)
        : "cc");
    return result;
}