package ru.wkn.repository.services;

import ru.wkn.repository.dao.IDao;

import java.io.Serializable;

/**
 * The class {@code IServiceFactory} contains factory method implementation for the {@code IService}
 * objects creating.
 *
 * @see IServiceFactory
 * @author Artem Pikalov
 */
public class ServiceFactory<V, I extends Serializable> implements IServiceFactory<V, I> {

    /**
     * @see IServiceFactory#createService(IDao)
     */
    @Override
    public IService createService(IDao<V, I> dao) {
        return new Service<>(dao);
    }
}
