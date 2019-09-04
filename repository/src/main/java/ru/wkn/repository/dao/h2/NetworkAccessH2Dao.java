package ru.wkn.repository.dao.h2;

import org.hibernate.Session;
import ru.wkn.entries.access.bin.AccessEntry;
import ru.wkn.entries.exceptions.EntryException;
import ru.wkn.repository.dao.EntityInstance;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class NetworkAccessH2Dao extends H2Dao<AccessEntry, Long> {

    public NetworkAccessH2Dao(Class<AccessEntry> entityClass, Session session, EntityInstance entityInstance) {
        super(entityClass, session, entityInstance);
    }

    @Override
    public List<AccessEntry> getAll() throws ParseException, EntryException {
        List<AccessEntry> accessEntries = new ArrayList<>();
        List allElements = super.getAll();
        for (Object allElement : allElements) {
            Object[] elements = (Object[]) allElement;
            AccessEntry accessEntry = new AccessEntry(String.valueOf(elements[2]), String.valueOf(elements[3]),
                    String.valueOf(elements[1]));
            accessEntry.setId(((BigInteger) elements[0]).longValue());
            accessEntries.add(accessEntry);
        }
        return accessEntries;
    }
}
