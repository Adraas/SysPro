package ru.wkn.entries;

import ru.wkn.entries.resource.csv.AccessMode;
import ru.wkn.entries.resource.csv.ResourceEntry;
import ru.wkn.entries.server.plaintext.ProtocolType;
import ru.wkn.entries.server.plaintext.ServerEntry;

import java.sql.Date;

public class EntryFactory implements IEntryFactory {

    @Override
    public IEntry createEntry(String parametersLine, ParametersDelimiter parametersDelimiter) {
        String[] parameters = parametersLine.split(parametersDelimiter.getParametersDelimiter());
        return parametersDelimiter.equals(ParametersDelimiter.RESOURCE_CSV_DELIMITER)
                ? new ResourceEntry(parameters[0], AccessMode.valueOf(parameters[1]), Date.valueOf(parameters[2]))
                : parametersDelimiter.equals(ParametersDelimiter.SERVER_PLAIN_TEXT_DELIMITER)
                ? new ServerEntry(parameters[0], Integer.valueOf(parameters[1]), ProtocolType.valueOf(parameters[2]))
                : null;
    }
}
