package ru.wkn;

import ru.wkn.javasm.AssemblerFunctions;
import ru.wkn.javasm.IAssemblerFunctions;

public class AssemblerWorkerFacade {

    private IAssemblerFunctions iAssemblerFunctions;

    public AssemblerWorkerFacade() {
        this.iAssemblerFunctions = new AssemblerFunctions();
    }

    public IAssemblerFunctions getiAssemblerFunctions() {
        return iAssemblerFunctions;
    }
}
