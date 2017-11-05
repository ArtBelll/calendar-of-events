package ru.korbit.ceserver.responses;

import ru.korbit.cecommon.packet.KindOfEvent;

/**
 * Created by Artur Belogur on 05.11.17.
 */
public class RSimpleEvent extends REvent {

    @Override
    public int getType() {
        return KindOfEvent.SIMPLE.getKind();
    }
}
