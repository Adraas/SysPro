package ru.wkn.filerw.readers;

import ru.wkn.entries.IEntry;
import ru.wkn.filerw.EFileReader;
import ru.wkn.filerw.files.EFile;

import java.util.ArrayList;
import java.util.List;

public class FileReader<T extends IEntry> extends EFileReader<T> {

    public FileReader(EFile<T> eFile) {
        super(eFile);
    }

    @Override
    public IEntry read(int entryNumber) {
        return getEFile().getEntries().get(entryNumber);
    }

    @Override
    public List<T> readSome(int startEntry, int endEntry) {
        List<T> someEntries = new ArrayList<>();
        for (int i = startEntry; i <= endEntry; i++) {
            someEntries.add(getEFile().getEntries().get(i));
        }
        return someEntries;
    }

    @Override
    public int readFileSize() {
        return getEFile().getEntries().size();
    }

    @Override
    public boolean contains(T entry) {
        return getEFile().getEntries().contains(entry);
    }
}
