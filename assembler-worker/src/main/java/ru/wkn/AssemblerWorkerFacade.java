package ru.wkn;

import ru.wkn.assembler.AssemblerFunctions;
import ru.wkn.assembler.IAssemblerFunctions;

public class AssemblerWorkerFacade {

    private IAssemblerFunctions iAssemblerFunctions;

    public AssemblerWorkerFacade() {
        this.iAssemblerFunctions = new AssemblerFunctions();
    }

    public IAssemblerFunctions getiAssemblerFunctions() {
        return iAssemblerFunctions;
    }
}
