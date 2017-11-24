package ru.korbit.cecommon.dao.dbimpl;

import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.dao.EventScheduleDao;
import ru.korbit.cecommon.domain.EventSchedule;

@Repository(value = "eventScheduleDao")
public class EventScheduleDaoImpl extends SessionFactoryHolder<EventSchedule> implements EventScheduleDao {

    public EventScheduleDaoImpl() {
        super(EventSchedule.class);
    }
}
