package ru.korbit.cecommon.dao.dbimpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Artur Belogur on 11.10.17.
 */
public abstract class SessionFactoryHolder<T> {

    private Class<T> tClass;

    @Autowired
    protected SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void save(T obj) {
        getSession().save(obj);
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
