package ru.wkn.repository.dao;

import ru.wkn.entries.IEntry;
import ru.wkn.entries.exceptions.EntryException;
import ru.wkn.repository.exceptions.PersistenceException;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

/**
 * The interface {@code IDao} represents the abstract layout data access object.
 *
 * @param <V> type of the persistence object
 * @param <I> type of the key (index) for working with the persistence object
 * @author Artem Pikalov
 */
public interface IDao<V extends IEntry, I extends Serializable> {

    /**
     * The method for the creating new persistence object in the repository.
     *
     * @param newInstance instance of the persistence object
     * @return {@code true} if object was created success in the repository, else - {@code false}
     * @throws PersistenceException thrown if some problems with object mapping
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
     * @throws PersistenceException thrown if some problems with object mapping
     */
    boolean update(V transientInstance) throws PersistenceException;

    /**
     * The method for the deleting persistence object from the repository.
     *
     * @param transientInstance instance of the persistence object for deleting
     * @return {@code true} if object was deleted success from the repository, else - {@code false}
     * @throws PersistenceException thrown if some problems with object mapping
     */
    boolean delete(V transientInstance) throws PersistenceException;

    /**
     * The method for the full sample statement executing.
     *
     * @return collection of the {@code List} type, contains all persistence objects from the repository
     */
    List<V> getAll() throws ParseException, EntryException;
}
