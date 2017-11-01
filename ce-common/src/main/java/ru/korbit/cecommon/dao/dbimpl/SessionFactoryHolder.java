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

    protected SessionFactoryHolder() {}

    protected SessionFactoryHolder(Class<T> tClass) {
        this.tClass = tClass;
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    protected void save(T obj) {
        getSession().save(obj);
    }

    protected Optional<T> get(Serializable id) {
        return getSession().byId(tClass).loadOptional(id);
    }

    protected void update(T obj) {
        getSession().update(obj);
    }

    protected void delete(T obj) {
        getSession().delete(obj);
    }
}
