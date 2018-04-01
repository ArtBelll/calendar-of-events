package ru.korbit.cecommon.dao.dbimpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.korbit.cecommon.dao.GenericDao;

import javax.persistence.EntityManagerFactory;
import java.io.Serializable;
import java.util.Optional;

/**
 * Created by Artur Belogur on 11.10.17.
 */
public abstract class SessionFactoryHolder<T> implements GenericDao<T>  {

    private Class<T> tClass;

    private SessionFactory sessionFactory;

    protected SessionFactoryHolder() {}

    protected SessionFactoryHolder(Class<T> tClass) {
        this.tClass = tClass;
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Autowired
    protected void setSessionFactory(EntityManagerFactory entityManagerFactory) {
        this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    }

    public T save(T obj) {
        getSession().save(obj);
        return obj;
    }

    public Optional<T> get(Serializable id) {
        return getSession().byId(tClass).loadOptional(id);
    }

    public void update(T obj) {
        getSession().update(obj);
    }

    public void delete(T obj) {
        getSession().delete(obj);
    }
}
