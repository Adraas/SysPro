package ru.wkn.repository.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * The class {@code HibernateUtil} initializes configuration for JPA ORM model with Hibernate provider.
 *
 * @author Artem Pikalov
 */
public class HibernateUtil {

    /**
     * Persistence unit indicates to JPA configuration.
     */
    private static final String PERSISTENCE_UNIT;

    /**
     * {@code EntityManagerFactory} object for the ruling sessions.
     */
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY;

    static {
        PERSISTENCE_UNIT = "h2_entity_manager";
        ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    }

    /**
     * The getter for the {@code EntityManagerFactory} field.
     *
     * @return value of the {@code EntityManagerFactory} field
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        return ENTITY_MANAGER_FACTORY;
    }
}
