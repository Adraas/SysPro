package ru.wkn.assembler;

public class AssemblerFunctions implements IAssemblerFunctions {

    @Override
    public native String dividingFunction(String commandLine);

    @Override
    public native String xorFunction(String commandLine);
}
