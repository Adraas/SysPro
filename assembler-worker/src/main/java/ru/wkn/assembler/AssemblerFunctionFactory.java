package ru.wkn.assembler;

import ru.wkn.assembler.functions.AssemblerDividing;
import ru.wkn.assembler.functions.AssemblerXOR;
import ru.wkn.assembler.functions.IAssemblerFunction;
import ru.wkn.assembler.functions.TypeAssemblerFunction;

public class AssemblerFunctionFactory implements IAssemblerFunctionFactory {

    @Override
    public IAssemblerFunction createAssemblerFunction(TypeAssemblerFunction typeAssemblerFunction) {
        return typeAssemblerFunction.equals(TypeAssemblerFunction.DIVIDING) ? new AssemblerDividing()
                : typeAssemblerFunction.equals(TypeAssemblerFunction.XOR) ? new AssemblerXOR()
                : null;
    }
}
