package ru.wkn.repository.services;

import lombok.AllArgsConstructor;
import ru.wkn.repository.dao.IDao;
import ru.wkn.repository.exceptions.PersistenceException;

import java.io.Serializable;
import java.util.List;

/**
 * The class {@code IService} represents business-logic service implementation for the repository by means DAO layout.
 * Instances of this type does not works with the repository directly (only by means DAO layout) and implements
 * composite logic for base instructions of the DAO layout.
 *
 * @see IService
 * @author Artem Pikalov
 */
@AllArgsConstructor
public class Service<V, I extends Serializable> implements IService<V, I> {

    /**
     * The DAO for the connection service and repository
     */
    private IDao<V, I> dao;

    /**
     * @see IService#create(V)
     */
    @Override
    public boolean create(V newInstance) throws PersistenceException {
        return dao.create(newInstance);
    }

    /**
     * @see IService#read(I)
     */
    @Override
    public V read(I index) {
        return dao.read(index);
    }

    /**
     * @see IService#update(V)
     */
    @Override
    public boolean update(V transientInstance) throws PersistenceException {
        return dao.update(transientInstance);
    }

    /**
     * @see IService#delete(V)
     */
    @Override
    public boolean delete(V transientInstance) throws PersistenceException {
        return dao.delete(transientInstance);
    }

    /**
     * @see IService#deleteAll()
     */
    @Override
    public boolean deleteAll() throws PersistenceException {
        List<V> persistenceObjects = getAll();
        for (V persistenceObject : persistenceObjects) {
            if (!delete(persistenceObject)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @see IService#getAll()
     */
    @Override
    public List<V> getAll() {
        return dao.getAll();
    }
}
