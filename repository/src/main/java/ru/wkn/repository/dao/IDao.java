package ru.wkn.repository.dao;

import ru.wkn.repository.exceptions.PersistenceException;

import java.io.Serializable;
import java.util.List;

public interface IDao<V, I extends Serializable> {

    boolean create(V newInstance) throws PersistenceException;

    V read(I index);

    boolean update(V transientInstance) throws PersistenceException;

    boolean delete(V transientInstance) throws PersistenceException;

    List<V> getAll();
}
