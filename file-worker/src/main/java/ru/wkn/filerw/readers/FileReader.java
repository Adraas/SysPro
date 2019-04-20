package ru.wkn.filerw.readers;

import ru.wkn.entries.ICSVEntry;
import ru.wkn.entries.IEntry;
import ru.wkn.filerw.files.EFile;

import java.util.List;

public class FileReader extends ru.wkn.filerw.EFileReader {

    public FileReader(EFile<ICSVEntry> eFile) {
        super(eFile);
    }

    @Override
    public IEntry read(Long entryNumber) {
        return null;
    }

    @Override
    public List<IEntry> readSome(Long startEntry, Long endEntry) {
        return null;
    }

    @Override
    public Long readFileSize() {
        return null;
    }
}
