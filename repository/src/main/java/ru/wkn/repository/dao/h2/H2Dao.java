package ru.wkn.repository.dao.h2;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.wkn.entries.exceptions.EntryException;
import ru.wkn.repository.dao.EntityInstance;
import ru.wkn.repository.dao.IDao;
import ru.wkn.repository.exceptions.PersistenceException;

import javax.persistence.Query;
import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

/**
 * The interface {@code H2Dao} represents the implementation layout data access object for the H2 Database.
 *
 * @see IDao
 * @author Artem Pikalov
 */
@AllArgsConstructor
@Log
public abstract class H2Dao<V, I extends Serializable> implements IDao<V, I> {

    /**
     * The meta-data about entity object.
     */
    private Class<V> entityClass;

    /**
     * The object represents connection session.
     */
    private Session session;

    /**
     * The enum object represents datasource name.
     */
    private EntityInstance entityInstance;

    /**
     * @see IDao#create(V)
     */
    @Override
    public boolean create(V newInstance) throws PersistenceException {
        Transaction transaction = session.beginTransaction();
        try {
            session.save(newInstance);
            transaction.commit();
        } catch (HibernateException | IllegalArgumentException e) {
            String message = "Element ".concat(newInstance.toString()).concat(" not created");
            log.warning(message);
            transaction.rollback();
            throw new PersistenceException(message, e);
        }
        return true;
    }

    /**
     * @see IDao#read(I)
     */
    @Override
    public V read(I index) {
        return session.get(entityClass, index);
    }

    /**
     * @see IDao#update(V)
     */
    @Override
    public boolean update(V transientInstance) throws PersistenceException {
        Transaction transaction = session.beginTransaction();
        try {
            session.update(transientInstance);
            transaction.commit();
        } catch (HibernateException | IllegalArgumentException e) {
            String message = "Element ".concat(transientInstance.toString()).concat(" not updated");
            log.warning(message);
            transaction.rollback();
            throw new PersistenceException(message, e);
        }
        return true;
    }

    /**
     * @see IDao#delete(V)
     */
    @Override
    public boolean delete(V transientInstance) throws PersistenceException {
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(transientInstance);
            transaction.commit();
        } catch (HibernateException | IllegalArgumentException e) {
            String message = "Element ".concat(transientInstance.toString()).concat(" not deleted");
            log.warning(message);
            transaction.rollback();
            throw new PersistenceException(message, e);
        }
        return true;
    }

    /**
     * @see IDao#getAll()
     */
    @SuppressWarnings(value = {"unchecked"})
    @Override
    public List getAll() throws ParseException, EntryException {
        List vList;
        Query query = session.createNativeQuery("SELECT * FROM ".concat(entityInstance.getEntityInstance()));
        vList = query.getResultList();
        return vList;
    }
}
