package ru.korbit.cecommon.packet;

import com.cronutils.model.time.ExecutionTime;
import lombok.val;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Iterator;

public class EventCronIterator implements Iterator<ZonedDateTime> {

    private final ExecutionTime cron;
    private final Duration duration;

    private ZonedDateTime i;
    private ZonedDateTime to;

    public EventCronIterator(ExecutionTime cron, Duration duration, ZonedDateTime from, ZonedDateTime to) {
        this.cron = cron;
        this.duration = duration;
        this.i = from;
        this.to = to;
    }

    @Override
    public boolean hasNext() {
        return cron.nextExecution(i).isPresent()
                && cron.nextExecution(i).get().isBefore(to);
    }

    @Override
    public ZonedDateTime next() {
        val nextAction = cron.nextExecution(i).get();
        i = nextAction.plus(duration);
        return nextAction;
    }
}
