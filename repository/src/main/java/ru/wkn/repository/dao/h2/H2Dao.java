package ru.wkn.repository.dao.h2;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.wkn.repository.dao.EntityInstance;
import ru.wkn.repository.dao.IDao;

import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Log
public class H2Dao<V, I extends Serializable> implements IDao<V, I> {

    private Class<V> entityClass;
    private Session session;
    private EntityInstance entityInstance;

    @Override
    public boolean create(V newInstance) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(newInstance);
            transaction.commit();
        } catch (HibernateException e) {
            log.warning("Element ".concat(newInstance.toString()).concat(" not created"));
            Objects.requireNonNull(transaction).rollback();
            return false;
        }
        return true;
    }

    @Override
    public V read(I index) {
        return session.get(entityClass, index);
    }

    @Override
    public boolean update(V transientInstance) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(transientInstance);
            transaction.commit();
        } catch (HibernateException e) {
            log.warning("Element ".concat(transientInstance.toString()).concat(" not updated"));
            Objects.requireNonNull(transaction).rollback();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(V transientInstance) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(transientInstance);
            transaction.commit();
        } catch (HibernateException e) {
            log.warning("Element ".concat(transientInstance.toString()).concat(" not deleted"));
            Objects.requireNonNull(transaction).rollback();
            return false;
        }
        return true;
    }

    @Override
    public List<V> getAll() {
        List<V> vList;
        Query query = session.createQuery("SELECT * FROM ".concat(entityInstance.getEntityInstance()));
        vList = (List<V>) query.getResultList();
        return vList;
    }
}
