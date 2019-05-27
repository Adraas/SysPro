package ru.wkn.filerw.files;

import ru.wkn.entries.IEntry;
import ru.wkn.entries.IEntryFactory;
import ru.wkn.entries.ParametersDelimiter;
import ru.wkn.entries.exceptions.EntryException;
import ru.wkn.filerw.readers.EntriesDelimiter;

import java.io.IOException;

/**
 * The interface {@code IFileFactory} contains factory method for the {@code EFile} objects creating.
 *
 * @param <T> extends {@code IEntry} type
 * @author Artem Pikalov
 */
public interface IFileFactory<T extends IEntry> {

    /**
     * The abstract factory method for the {@code EFile} object creating based on a real file.
     *
     * @param path path of the real file
     * @param charsetName charset for the file reading
     * @param entriesDelimiter delimiter as {@code EntriesDelimiter} object for entries/lines in the file
     * @param entryFactory {@code IEntryFactory} object for creating the new {@code IEntry} object
     * @param parametersDelimiter delimiter as {@code ParametersDelimiter} object for parameters of each entry
     * @return the newly {@code EFile} object based on a real file
     * @throws IOException thrown if some problems with specific file
     * @throws EntryException thrown if some problems with entry
     */
    EFile<T> createEFile(String path, String charsetName, EntriesDelimiter entriesDelimiter, IEntryFactory entryFactory,
                         ParametersDelimiter parametersDelimiter) throws IOException, EntryException;
}
