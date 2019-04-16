package ru.wkn.assembler;

import ru.wkn.assembler.functions.IAssemblerFunction;
import ru.wkn.assembler.functions.TypeAssemblerFunction;

public interface IAssemblerFunctionFactory {

    IAssemblerFunction createAssemblerFunction(TypeAssemblerFunction typeAssemblerFunction);
}
