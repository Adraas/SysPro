package ru.wkn.repository.services;

import ru.wkn.repository.dao.IDao;

import java.io.Serializable;

public class ServiceFactory<V, I extends Serializable> implements IServiceFactory<V, I> {

    @Override
    public IService createService(IDao<V, I> dao) {
        return new Service<>(dao);
    }
}
