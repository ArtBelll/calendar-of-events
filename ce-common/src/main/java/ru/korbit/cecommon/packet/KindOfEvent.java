package ru.korbit.cecommon.packet;

/**
 * Created by Artur Belogur on 05.11.17.
 */
public enum  KindOfEvent {

    CINEMA(0),
    SIMPLE(1);

    private int kind = -1;

    KindOfEvent(int kind) {
        this.kind = kind;
    }

    public int getKind() {
        return kind;
    }
}
