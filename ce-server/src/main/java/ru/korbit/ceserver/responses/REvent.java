package ru.korbit.ceserver.responses;

import lombok.Data;

/**
 * Created by Artur Belogur on 05.11.17.
 */
@Data
public abstract class REvent {

    public abstract int getType();
}
