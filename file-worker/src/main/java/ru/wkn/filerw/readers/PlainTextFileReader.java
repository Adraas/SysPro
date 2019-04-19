package ru.wkn.filerw.readers;

import ru.wkn.entries.types.IEntry;
import ru.wkn.entries.types.IPlainTextEntry;
import ru.wkn.filerw.EFileReader;
import ru.wkn.filerw.files.EFile;

import java.io.IOException;
import java.util.List;

public class PlainTextFileReader extends EFileReader {

    public PlainTextFileReader(EFile<IPlainTextEntry> eFile, String charsetName) throws IOException {
        super(eFile, charsetName);
    }

    @Override
    public IEntry read(Long entryNumber) throws IOException {
        return null;
    }

    @Override
    public List<IEntry> readSome(Long startEntry, Long endEntry) throws IOException {
        return null;
    }

    @Override
    public Long readFileSize() throws IOException {
        return null;
    }
}
