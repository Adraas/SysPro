package ru.wkn.entries;

import ru.wkn.entries.exceptions.EntryException;

/**
 * The interface {@code IEntryFactory} represent factory for {@code IEntry} entities.
 *
 * @author Artem Pikalov
 */
public interface IEntryFactory {

    /**
     * Method for the creating {@code IEntry} object.
     *
     * @param parametersLine main information for the {@code IEntry} object into single {@code String} line
     * @param parametersDelimiter delimiter for parameters line
     * @return object of {@code IEntry} type
     */
    IEntry createEntry(String parametersLine, ParametersDelimiter parametersDelimiter) throws EntryException;
}
