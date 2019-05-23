package ru.wkn.repository.dao;

import org.hibernate.Session;

import java.io.Serializable;

/**
 * The interface {@code IDaoFactory} contains abstract factory method for the {@code IDao}
 * objects creating.
 *
 * @author Artem Pikalov
 */
public interface IDaoFactory<V, I extends Serializable> {

    /**
     * The abstract factory method for the {@code IDao} object creating.
     *
     * @param entityInstance the enum object represents datasource name
     * @param session the object represents connection session
     * @return newly created {@code IDao} object
     */
    IDao<V, I> createDao(EntityInstance entityInstance, Session session);
}
