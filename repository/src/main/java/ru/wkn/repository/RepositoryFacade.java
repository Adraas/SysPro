package ru.wkn.repository;

import lombok.Getter;
import org.hibernate.Session;
import ru.wkn.repository.dao.DaoFactory;
import ru.wkn.repository.dao.EntityInstance;
import ru.wkn.repository.dao.IDao;
import ru.wkn.repository.dao.IDaoFactory;
import ru.wkn.repository.jpa.HibernateUtil;
import ru.wkn.repository.services.IService;
import ru.wkn.repository.services.IServiceFactory;
import ru.wkn.repository.services.ServiceFactory;

import java.io.Serializable;

public class RepositoryFacade<V, I extends Serializable> {

    @Getter
    private IService service;

    public RepositoryFacade(EntityInstance entityInstance) {
        serviceReinitialize(entityInstance);
    }

    public void serviceReinitialize(EntityInstance entityInstance) {
        IDaoFactory<V, I> daoFactory = new DaoFactory<>();
        IDao<V, I> dao = daoFactory.createDao(entityInstance,
                (Session) HibernateUtil.getEntityManagerFactory().createEntityManager().getDelegate());
        IServiceFactory<V, I> serviceFactory = new ServiceFactory<>();
        service = serviceFactory.createService(dao);
    }
}
