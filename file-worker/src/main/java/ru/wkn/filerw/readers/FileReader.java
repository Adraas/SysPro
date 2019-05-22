package ru.wkn.filerw.readers;

import ru.wkn.entries.IEntry;
import ru.wkn.filerw.EFileReader;
import ru.wkn.filerw.files.EFile;

import java.util.ArrayList;
import java.util.List;

/**
 * The class {@code FileReader} represents file's reading and implements {@code EFileReader} class.
 *
 * @see EFileReader
 * @author Artem Pikalov
 */
public class FileReader<T extends IEntry> extends EFileReader<T> {

    /**
     * Initializes a newly created {@code FileReader} object.
     *
     * @see EFileReader#EFileReader(EFile)
     */
    public FileReader(EFile<T> eFile) {
        super(eFile);
    }

    /**
     * @see EFileReader#read(int)
     */
    @Override
    public IEntry read(int entryNumber) {
        return getEFile().getEntries().get(entryNumber);
    }

    /**
     * @see EFileReader#readSome(int, int)
     */
    @Override
    public List<T> readSome(int startEntry, int endEntry) {
        List<T> someEntries = new ArrayList<>();
        for (int i = startEntry; i <= endEntry; i++) {
            someEntries.add(getEFile().getEntries().get(i));
        }
        return someEntries;
    }

    /**
     * @see EFileReader#readFileSize()
     */
    @Override
    public int readFileSize() {
        return getEFile().getEntries().size();
    }

    /**
     * @see EFileReader#contains(T)
     */
    @Override
    public boolean contains(T entry) {
        return getEFile().getEntries().contains(entry);
    }
}
