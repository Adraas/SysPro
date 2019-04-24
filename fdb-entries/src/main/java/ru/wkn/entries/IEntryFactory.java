package ru.wkn.entries;

/**
 * Interface {@code IEntryFactory} represent factory for {@link IEntry} entities.
 *
 * @author Artem Pikalov
 */
public interface IEntryFactory {

    /**
     * Method for the creating {@link IEntry} object.
     *
     * @param parametersLine - main information for the {@link IEntry} object into single {@code String} line
     * @param parametersDelimiter - delimiter for parameters line
     * @return object of {@link IEntry} type
     */
    IEntry createEntry(String parametersLine, ParametersDelimiter parametersDelimiter);
}
