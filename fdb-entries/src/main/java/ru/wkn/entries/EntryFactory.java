package ru.wkn.entries;

import ru.wkn.entries.access.bin.AccessEntry;
import ru.wkn.entries.exceptions.EntryException;
import ru.wkn.entries.resource.csv.AccessMode;
import ru.wkn.entries.resource.csv.ResourceEntry;
import ru.wkn.entries.server.plaintext.ProtocolType;
import ru.wkn.entries.server.plaintext.ServerEntry;

import java.sql.Date;

/**
 * The class {@code EntryFactory} represent implementation of the factory for {@code IEntry} entities.
 *
 * @see IEntry
 * @author Artem Pikalov
 */
public class EntryFactory implements IEntryFactory {

    /**
     * @see IEntryFactory#createEntry(String, ParametersDelimiter)
     */
    @Override
    public IEntry createEntry(String parametersLine, ParametersDelimiter parametersDelimiter) throws EntryException {
        if (parametersLine.replaceAll(parametersDelimiter.getParametersDelimiter(), "").trim().isEmpty()) {
            throw new EntryException("parameters is absent");
        }
        String[] parameters = parametersLine.split(parametersDelimiter.getParametersDelimiter());
        if (parameters.length < 3) {
            throw new EntryException("parameters is incorrect");
        }
        IEntry entry = parametersDelimiter.equals(ParametersDelimiter.RESOURCE_CSV_DELIMITER)
                ? new ResourceEntry(parameters[0], AccessMode.valueOf(parameters[1]), Date.valueOf(parameters[2]))
                : parametersDelimiter.equals(ParametersDelimiter.SERVER_PLAIN_TEXT_DELIMITER)
                ? new ServerEntry(parameters[0], Integer.valueOf(parameters[1]), ProtocolType.valueOf(parameters[2]))
                : parametersDelimiter.equals(ParametersDelimiter.ACCESS_BIN_DELIMITER)
                ? new AccessEntry(parameters[0], parameters[1], parameters[2])
                : null;
        if (entry == null) {
            throw new EntryException("entry type not found");
        }
        return entry;
    }
}
