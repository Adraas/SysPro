package ru.wkn.repository.dao;

import org.hibernate.Session;
import ru.wkn.entries.IEntry;
import ru.wkn.entries.access.bin.AccessEntry;
import ru.wkn.entries.resource.csv.ResourceEntry;
import ru.wkn.entries.server.plaintext.ServerEntry;
import ru.wkn.repository.dao.h2.NetworkAccessH2Dao;
import ru.wkn.repository.dao.h2.NetworkResourceH2Dao;
import ru.wkn.repository.dao.h2.NetworkServerH2Dao;

import java.io.Serializable;

/**
 * The interface {@code DaoFactory} contains factory method implementation for the {@code IDao}
 * objects creating.
 *
 * @see IDao
 * @author Artem Pikalov
 */
public class DaoFactory<V extends IEntry, I extends Serializable> implements IDaoFactory<V, I> {

    /**
     * @see IDaoFactory#createDao(EntityInstance, Session)
     */
    @SuppressWarnings(value = {"unchecked"})
    @Override
    public IDao<V, I> createDao(EntityInstance entityInstance, Session session) {
        return entityInstance.equals(EntityInstance.NETWORK_RESOURCE)
                ? (IDao<V, I>) new NetworkResourceH2Dao(ResourceEntry.class, session, entityInstance)
                : entityInstance.equals(EntityInstance.NETWORK_SERVER)
                ? (IDao<V, I>) new NetworkServerH2Dao(ServerEntry.class, session, entityInstance)
                : entityInstance.equals(EntityInstance.NETWORK_ACCESS)
                ? (IDao<V, I>) new NetworkAccessH2Dao(AccessEntry.class, session, entityInstance)
                : null;
    }
}
