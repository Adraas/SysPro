package ru.wkn.repository.dao;

import org.hibernate.Session;

import java.io.Serializable;

public interface IDaoFactory<V, I extends Serializable> {

    IDao<V, I> createDao(EntityInstance entityInstance, Session session);
}
