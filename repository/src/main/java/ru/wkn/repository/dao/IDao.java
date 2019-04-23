package ru.wkn.repository.dao;

import java.io.Serializable;
import java.util.List;

public interface IDao<V, I extends Serializable> {

    boolean create(V newInstance);

    V read(I index);

    boolean update(V transientInstance);

    boolean delete(V transientInstance);

    List<V> getAll();
}
