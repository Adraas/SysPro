package ru.wkn.repository;

import lombok.Getter;
import org.hibernate.Session;
import ru.wkn.repository.dao.DaoFactory;
import ru.wkn.repository.dao.EntityInstance;
import ru.wkn.repository.dao.IDao;
import ru.wkn.repository.dao.IDaoFactory;
import ru.wkn.repository.util.HibernateUtil;
import ru.wkn.repository.services.IService;
import ru.wkn.repository.services.IServiceFactory;
import ru.wkn.repository.services.ServiceFactory;

import java.io.Serializable;

/**
 * The class {@code RepositoryFacade} represents the facade design pattern for the repository.
 *
 * @author Artem Pikalov
 */
@Getter
public class RepositoryFacade<V, I extends Serializable> {

    /**
     * The service for the working with the repository.
     */
    private IService<V, I> service;

    /**
     * The enum object represents datasource name
     */
    private EntityInstance entityInstance;

    /**
     * Initializes a newly created {@code RepositoryFacade} object
     * @param entityInstance {@link #entityInstance}
     */
    public RepositoryFacade(EntityInstance entityInstance) {
        serviceReinitialize(entityInstance);
    }

    /**
     * The method for the initializing {@code IService} instance.
     *
     * @param entityInstance the enum object represents datasource name
     */
    public void serviceReinitialize(EntityInstance entityInstance) {
        IDaoFactory<V, I> daoFactory = new DaoFactory<>();
        IDao<V, I> dao = daoFactory.createDao(entityInstance,
                (Session) HibernateUtil.getEntityManagerFactory().createEntityManager().getDelegate());
        IServiceFactory<V, I> serviceFactory = new ServiceFactory<>();
        service = serviceFactory.createService(dao);
    }
}
