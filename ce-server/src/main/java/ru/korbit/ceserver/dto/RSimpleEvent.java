package ru.korbit.ceserver.dto;

import ru.korbit.cecommon.domain.Event;
import ru.korbit.cecommon.packet.KindOfEvent;

/**
 * Created by Artur Belogur on 05.11.17.
 */
public class RSimpleEvent extends REvent {

    public RSimpleEvent(Event event) {
        super(event);
    }

    @Override
    public int getType() {
        return KindOfEvent.SIMPLE.getKind();
    }
}
