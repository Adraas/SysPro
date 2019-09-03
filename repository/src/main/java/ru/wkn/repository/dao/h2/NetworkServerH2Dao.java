package ru.wkn.repository.dao.h2;

import org.hibernate.Session;
import ru.wkn.entries.exceptions.EntryException;
import ru.wkn.entries.server.plaintext.ProtocolType;
import ru.wkn.entries.server.plaintext.ServerEntry;
import ru.wkn.repository.dao.EntityInstance;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class NetworkServerH2Dao extends H2Dao<ServerEntry, Long> {

    public NetworkServerH2Dao(Class<ServerEntry> entityClass, Session session, EntityInstance entityInstance) {
        super(entityClass, session, entityInstance);
    }

    @Override
    public List<ServerEntry> getAll() throws ParseException, EntryException {
        List<ServerEntry> serverEntries = new ArrayList<>();
        List allElements = super.getAll();
        for (Object allElement : allElements) {
            Object[] elements = (Object[]) allElement;
            ServerEntry serverEntry = new ServerEntry(String.valueOf(elements[3]),
                    Integer.valueOf(String.valueOf(elements[1])),
                    ProtocolType.getInstance(String.valueOf(elements[2])));
            serverEntry.setId(((BigInteger) elements[0]).longValue());
            serverEntries.add(serverEntry);
        }
        return serverEntries;
    }
}
