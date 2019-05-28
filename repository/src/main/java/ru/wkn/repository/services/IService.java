package ru.wkn.repository.services;

import ru.wkn.repository.exceptions.PersistenceException;

import java.io.Serializable;
import java.util.List;

/**
 * The interface {@code IService} represents abstract business-logic service for the repository by means DAO layout.
 * Instances of this type does not works with the repository directly (only by means DAO layout) and implements
 * composite logic for base instructions of the DAO layout.
 *
 * @param <V> type of the persistence object
 * @param <I> type of the key (index) for working with the persistence object
 * @author Artem Pikalov
 */
public interface IService<V, I extends Serializable> {

    /**
     * The method for the creating new persistence object in the repository.
     *
     * @param newInstance instance of the persistence object
     * @return {@code true} if object was created success in the repository, else - {@code false}
     * @throws PersistenceException thrown if some problems with DAO layout
     */
    boolean create(V newInstance) throws PersistenceException;

    /**
     * The method for the reading persistence object by key (index) from repository.
     *
     * @param index key (index) for the searching persistence object in the repository
     * @return found value or {@code null} if it not exists
     */
    V read(I index);

    /**
     * The method for the updating persistence object in the repository.
     *
     * @param transientInstance instance of the persistence object for updating old value
     * @return {@code true} if object was updated success in the repository, else - {@code false}
     * @throws PersistenceException thrown if some problems with DAO layout
     */
    boolean update(V transientInstance) throws PersistenceException;

    /**
     * The method for the deleting persistence object from the repository.
     *
     * @param transientInstance instance of the persistence object for deleting
     * @return {@code true} if object was deleted success from the repository, else - {@code false}
     * @throws PersistenceException thrown if some problems with DAO layout
     */
    boolean delete(V transientInstance) throws PersistenceException;

    /**
     * The method for the deleting all persistence objects from the repository.
     *
     * @return {@code true} if objects was deleted success from the repository, else - {@code false}
     * @throws PersistenceException thrown if some problems with DAO layout
     */
    boolean deleteAll() throws PersistenceException;

    /**
     * The method for the full sample statement executing.
     *
     * @return collection of the {@code List} type, contains all persistence objects from the repository
     */
    List<V> getAll();
}
