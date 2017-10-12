package ru.korbit.cecommon.dao.dbimpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Artur Belogur on 11.10.17.
 */
public abstract class SessionFactoryHolder {
    @Autowired
    protected SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    protected <T> void save(T obj) {
        getSession().save(obj);
    }

    protected <T> void update(T obj) {
        getSession().update(obj);
    }

    protected <T> void delete(T obj) {
        getSession().delete(obj);
    }
}
