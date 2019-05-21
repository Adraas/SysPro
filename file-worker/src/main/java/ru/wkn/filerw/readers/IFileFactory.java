package ru.wkn.filerw.readers;

import ru.wkn.entries.IEntry;
import ru.wkn.entries.IEntryFactory;
import ru.wkn.entries.ParametersDelimiter;
import ru.wkn.entries.exceptions.EntryException;
import ru.wkn.filerw.files.EFile;

import java.io.IOException;

/**
 * Interface {@code IFileFactory} contains factory method for the {@link EFile} objects creating.
 *
 * @param <T> extends {@link IEntry} type
 * @author Artem Pikalov
 */
public interface IFileFactory<T extends IEntry> {

    /**
     * The abstract factory method for the {@link EFile} object creating based on a real file.
     *
     * @param path path of the real file
     * @param charsetName charset for the file reading
     * @param entriesDelimiter delimiter as {@link EntriesDelimiter} object for entries/lines in the file
     * @param entryFactory {@link IEntryFactory} object for creating the new {@link IEntry} object
     * @param parametersDelimiter delimiter as {@link ParametersDelimiter} object for parameters of each entry
     * @return the newly {@link EFile} object based on a real file
     * @throws IOException thrown if some problems with specific file
     * @throws EntryException thrown if some problems with entry
     */
    EFile<T> createEFile(String path, String charsetName, EntriesDelimiter entriesDelimiter, IEntryFactory entryFactory,
                         ParametersDelimiter parametersDelimiter) throws IOException, EntryException;
}
