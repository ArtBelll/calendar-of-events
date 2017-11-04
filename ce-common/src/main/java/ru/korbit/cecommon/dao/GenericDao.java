package ru.korbit.cecommon.dao;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by Artur Belogur on 04.11.17.
 */
public interface GenericDao<T> {

    T save(T obj);

    Optional<T> get(Serializable id);

    void update(T obj);

    void delete(T obj);
}
