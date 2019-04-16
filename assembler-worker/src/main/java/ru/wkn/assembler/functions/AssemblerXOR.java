package ru.wkn.assembler.functions;

public class AssemblerXOR implements IAssemblerFunction {

    @Override
    public native String runFunction(String commandLine);
}
