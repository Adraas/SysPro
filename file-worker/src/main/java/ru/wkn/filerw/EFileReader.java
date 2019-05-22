package ru.wkn.filerw;

import lombok.Getter;
import lombok.Setter;
import ru.wkn.entries.IEntry;
import ru.wkn.filerw.files.EFile;

import java.util.List;

/**
 * The abstract class {@code EFileReader} represents file's reading.
 *
 * @param <T> extends {@code IEntry} type
 * @author Artem Pikalov
 */
@Getter
@Setter
public abstract class EFileReader<T extends IEntry> {

    /**
     * {@code EFile} object, represented file.
     */
    private EFile<T> eFile;

    /**
     * Initializes a newly created {@code EFileReader} object with given {@code EFile} object ({@link #eFile}) value.
     *
     * @param eFile {@code EFile} object, represented file
     */
    public EFileReader(EFile<T> eFile) {
        this.eFile = eFile;
    }

    /**
     * The method for reading entry by number.
     *
     * @param entryNumber number (ID) of entry in file
     * @return {@code IEntry} object, represented entry from file
     */
    public abstract IEntry read(int entryNumber);

    /**
     * The method for reading entries from specific interval.
     *
     * @param startEntry number of first value of interval
     * @param endEntry number of last value of interval
     * @return {@code List} object, represented collection of entries from file
     */
    public abstract List<T> readSome(int startEntry, int endEntry);

    /**
     * The method for reading file size.
     *
     * @return {@code int} value, represented file size (quantity of entries)
     */
    public abstract int readFileSize();

    /**
     * The method for content checking.
     *
     * @param entry entry for search in file
     * @return {@code true} if the entry is contained in file, else - {@code false}
     */
    public abstract boolean contains(T entry);
}
