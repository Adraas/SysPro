package ru.wkn.filerw.readers;

import ru.wkn.entries.IEntry;
import ru.wkn.entries.IEntryFactory;
import ru.wkn.entries.ParametersDelimiter;
import ru.wkn.filerw.files.EFile;

import java.io.IOException;

public interface IFileFactory<T extends IEntry> {

    EFile<T> createEFile(String path, String charsetName, EntriesDelimiter entriesDelimiter, IEntryFactory entryFactory,
                         ParametersDelimiter parametersDelimiter) throws IOException;
}
