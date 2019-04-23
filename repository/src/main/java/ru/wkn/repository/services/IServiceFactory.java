package ru.wkn.repository.services;

import ru.wkn.repository.dao.IDao;

import java.io.Serializable;

public interface IServiceFactory<V, I extends Serializable> {

    IService createService(IDao<V, I> dao);
}
