package ru.wkn.repository.dao.h2;

import org.hibernate.Session;
import ru.wkn.entries.exceptions.EntryException;
import ru.wkn.entries.resource.csv.AccessMode;
import ru.wkn.entries.resource.csv.ResourceEntry;
import ru.wkn.repository.dao.EntityInstance;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NetworkResourceH2Dao extends H2Dao<ResourceEntry, Long> {

    public NetworkResourceH2Dao(Class<ResourceEntry> entityClass, Session session, EntityInstance entityInstance) {
        super(entityClass, session, entityInstance);
    }

    @Override
    public List<ResourceEntry> getAll() throws ParseException, EntryException {
        List<ResourceEntry> resourceEntries = new ArrayList<>();
        List allElements = super.getAll();
        for (Object allElement : allElements) {
            Object[] elements = (Object[]) allElement;
            ResourceEntry resourceEntry = new ResourceEntry(String.valueOf(elements[3]),
                    AccessMode.getInstance(String.valueOf(elements[2])), (Date) elements[1]);
            resourceEntry.setId(((BigInteger) elements[0]).longValue());
            resourceEntries.add(resourceEntry);
        }
        return resourceEntries;
    }
}
