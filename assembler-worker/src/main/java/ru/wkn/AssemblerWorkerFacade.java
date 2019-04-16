package ru.wkn;

import ru.wkn.assembler.AssemblerFunctionFactory;
import ru.wkn.assembler.IAssemblerFunctionFactory;
import ru.wkn.assembler.functions.IAssemblerFunction;
import ru.wkn.assembler.functions.TypeAssemblerFunction;

public class AssemblerWorkerFacade {

    private final IAssemblerFunctionFactory iAssemblerFunctionFactory = new AssemblerFunctionFactory();
    private TypeAssemblerFunction typeAssemblerFunction;
    private IAssemblerFunction iAssemblerFunction;

    public AssemblerWorkerFacade(TypeAssemblerFunction typeAssemblerFunction, IAssemblerFunction iAssemblerFunction) {
        this.typeAssemblerFunction = typeAssemblerFunction;
        this.iAssemblerFunction = iAssemblerFunction;
        assemblerFunctionInit();
    }

    private void assemblerFunctionInit() {
        iAssemblerFunction = iAssemblerFunctionFactory.createAssemblerFunction(typeAssemblerFunction);
    }

    public TypeAssemblerFunction getTypeAssemblerFunction() {
        return typeAssemblerFunction;
    }

    public void setTypeAssemblerFunction(TypeAssemblerFunction typeAssemblerFunction) {
        this.typeAssemblerFunction = typeAssemblerFunction;
        assemblerFunctionInit();
    }

    public IAssemblerFunction getiAssemblerFunction() {
        return iAssemblerFunction;
    }
}
