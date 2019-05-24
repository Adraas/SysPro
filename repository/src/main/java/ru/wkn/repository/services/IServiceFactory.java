package ru.wkn.repository.services;

import ru.wkn.repository.dao.IDao;

import java.io.Serializable;

/**
 * The interface {@code IServiceFactory} contains abstract factory method for the {@code IService}
 * objects creating.
 *
 * @param <V> type of the persistence object
 * @param <I> type of the key (index) for working with the persistence object
 * @author Artem Pikalov
 */
public interface IServiceFactory<V, I extends Serializable> {

    /**
     * The abstract factory method for the {@code IService} object creating.
     *
     * @param dao DAO for the connection service and repository
     * @return newly created {@code IService} object
     */
    IService createService(IDao<V, I> dao);
}
