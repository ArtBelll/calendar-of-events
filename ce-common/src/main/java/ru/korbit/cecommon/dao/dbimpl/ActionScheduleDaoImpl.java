package ru.korbit.cecommon.dao.dbimpl;

import org.springframework.stereotype.Repository;
import ru.korbit.cecommon.dao.ActionScheduleDao;
import ru.korbit.cecommon.domain.ActionSchedule;

@Repository(value = "actionScheduleDao")
public class ActionScheduleDaoImpl extends SessionFactoryHolder<ActionSchedule>
        implements ActionScheduleDao {

    public ActionScheduleDaoImpl() {
        super(ActionSchedule.class);
    }
}
