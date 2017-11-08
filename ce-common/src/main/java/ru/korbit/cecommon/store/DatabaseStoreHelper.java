package ru.korbit.cecommon.store;

import lombok.NonNull;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by Artur Belogur on 07.11.17.
 */
public interface DatabaseStoreHelper<T> {

    T addToDb(@NonNull T obj);

    Optional<T> getFromDb(@NonNull Serializable id);

    Optional<T> searchFromDb(@NonNull T obj);

    void updateInDb(@NonNull T obj);
}
