package ru.wkn.entries;

public interface IEntryFactory {

    IEntry createEntry(String parametersLine, ParametersDelimiter parametersDelimiter);
}
