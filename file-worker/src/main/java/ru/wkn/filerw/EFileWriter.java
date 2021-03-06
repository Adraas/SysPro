package ru.wkn.filerw;

import lombok.Getter;
import lombok.Setter;
import ru.wkn.entries.IEntry;
import ru.wkn.filerw.files.EFile;

import java.io.IOException;
import java.util.List;

/**
 * The abstract class {@code EFileWriter} represents file's writing.
 *
 * @param <T> extends {@code IEntry} type
 * @author Artem Pikalov
 */
@Getter
@Setter
public abstract class EFileWriter<T extends IEntry> {

    /**
     * {@code EFile} object, represented file.
     */
    private EFile<T> eFile;
    /**
     * {@code String} object, represented charset.
     */
    private String charsetName;

    /**
     * Initializes a newly created {@code EFileWriter} object with given {@code EFile} object ({@link #eFile})
     * and {@code String} object ({@link #charsetName}) values.
     *
     * @param eFile {@link #eFile}
     * @param charsetName {@link #charsetName}
     */
    public EFileWriter(EFile<T> eFile, String charsetName) {
        this.eFile = eFile;
        this.charsetName = charsetName;
    }

    /**
     * The method for appending entry to collections for writing.
     *
     * @param entry entry for writing
     * @return {@code true} if writing is success, {@code false} - else
     */
    public abstract boolean append(T entry);

    /**
     * The method for appending entries to collections for writing.
     *
     * @param entries entries for writing
     * @return {@code true} if writing is success, {@code false} - else
     */
    public abstract boolean append(List<T> entries);

    /**
     * The method for deleting entry by value from collection from file.
     *
     * @param entry entry for deleting
     * @return {@code true} if deleting is success, {@code false} - else
     */
    public abstract boolean delete(T entry);

    /**
     * The method for deleting entry by number from collection from file.
     *
     * @param entryNumber number (ID) of entry for deleting
     * @return {@code true} if deleting is success, {@code false} - else
     */
    public abstract boolean delete(int entryNumber);

    /**
     * The method for deleting entries by values from collection from file.
     *
     * @param entries entries for deleting
     * @return {@code true} if deleting is success, {@code false} - else
     */
    public abstract boolean deleteSome(List<T> entries);

    /**
     * The method for deleting entries from specific interval from collection from file.
     *
     * @param startEntry number of first value of interval
     * @param endEntry number of last value of interval
     * @return {@code true} if deleting is success, {@code false} - else
     */
    public abstract boolean deleteSome(int startEntry, int endEntry);

    /**
     * The method for saving entries from temporary collection to file.
     *
     * @return {@code true} if saving is success, {@code false} - else
     * @throws IOException thrown if some problems with specific file
     */
    public abstract boolean saveFile() throws IOException;

    /**
     * The method for saving entries from temporary collection to new file.
     *
     * @param filename path indicated new absolute file name
     * @return {@code true} if saving is success, {@code false} - else
     * @throws IOException thrown if some problems with specific file
     */
    public abstract boolean saveFile(String filename) throws IOException;
}
