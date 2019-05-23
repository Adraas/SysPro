package ru.wkn.repository.dao;

import org.hibernate.Session;
import ru.wkn.entries.access.bin.AccessEntry;
import ru.wkn.entries.resource.csv.ResourceEntry;
import ru.wkn.entries.server.plaintext.ServerEntry;
import ru.wkn.repository.dao.h2.H2Dao;

import java.io.Serializable;

/**
 * The interface {@code DaoFactory} contains factory method for the {@code IDao}
 * objects creating with implement.
 *
 * @see IDao
 * @author Artem Pikalov
 */
public class DaoFactory<V, I extends Serializable> implements IDaoFactory<V, I> {

    /**
     * @see IDaoFactory#createDao(EntityInstance, Session)
     */
    @Override
    public IDao<V, I> createDao(EntityInstance entityInstance, Session session) {
        return entityInstance.equals(EntityInstance.NETWORK_RESOURCE)
                ? new H2Dao<>((Class<V>) ResourceEntry.class, session, entityInstance)
                : entityInstance.equals(EntityInstance.NETWORK_SERVER)
                ? new H2Dao<>((Class<V>) ServerEntry.class, session, entityInstance)
                : entityInstance.equals(EntityInstance.NETWORK_ACCESS)
                ? new H2Dao<>((Class<V>) AccessEntry.class, session, entityInstance)
                : null;
    }
}
