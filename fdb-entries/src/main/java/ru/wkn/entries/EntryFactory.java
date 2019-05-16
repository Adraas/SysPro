package ru.wkn.entries;

import ru.wkn.entries.access.bin.AccessEntry;
import ru.wkn.entries.resource.csv.AccessMode;
import ru.wkn.entries.resource.csv.ResourceEntry;
import ru.wkn.entries.server.plaintext.ProtocolType;
import ru.wkn.entries.server.plaintext.ServerEntry;

import java.sql.Date;

/**
 * Class {@code EntryFactory} represent realisation of the factory for {@link IEntry} entities.
 *
 * @see IEntry
 * @author Artem Pikalov
 */
public class EntryFactory implements IEntryFactory {

    /**
     * @see IEntryFactory#createEntry(String, ParametersDelimiter)
     */
    @Override
    public IEntry createEntry(String parametersLine, ParametersDelimiter parametersDelimiter) {
        String[] parameters = parametersLine.split(parametersDelimiter.getParametersDelimiter());
        return parametersDelimiter.equals(ParametersDelimiter.RESOURCE_CSV_DELIMITER)
                ? new ResourceEntry(parameters[0], AccessMode.valueOf(parameters[1]), Date.valueOf(parameters[2]))
                : parametersDelimiter.equals(ParametersDelimiter.SERVER_PLAIN_TEXT_DELIMITER)
                ? new ServerEntry(parameters[0], Integer.valueOf(parameters[1]), ProtocolType.valueOf(parameters[2]))
                : parametersDelimiter.equals(ParametersDelimiter.ACCESS_BIN_DELIMITER)
                ? new AccessEntry(parameters[0], parameters[1], parameters[2])
                : null;
    }
}
