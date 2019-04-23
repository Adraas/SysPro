package ru.wkn.repository.services;

import lombok.AllArgsConstructor;
import ru.wkn.repository.dao.IDao;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
public class Service<V, I extends Serializable> implements IService<V, I> {

    private IDao<V, I> dao;

    @Override
    public boolean create(V newInstance) {
        return dao.create(newInstance);
    }

    @Override
    public V read(I index) {
        return dao.read(index);
    }

    @Override
    public boolean update(V transientInstance) {
        return dao.update(transientInstance);
    }

    @Override
    public boolean delete(V transientInstance) {
        return dao.delete(transientInstance);
    }

    @Override
    public List<V> getAll() {
        return dao.getAll();
    }
}
