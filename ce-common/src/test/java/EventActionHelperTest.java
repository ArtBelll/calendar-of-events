import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import ru.korbit.cecommon.domain.ActionSchedule;
import ru.korbit.cecommon.domain.City;
import ru.korbit.cecommon.domain.SimpleEvent;
import ru.korbit.cecommon.utility.EventActionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.cronutils.model.CronType.QUARTZ;

public class EventActionHelperTest {

    private final CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(QUARTZ));

    private final Long cityId = 1L;

    // 2017-11-23
    private final ZonedDateTime from = ZonedDateTime
            .ofInstant(Instant.ofEpochSecond(1511388000L), ZoneOffset.ofHours(2));
    // 2017-11-30
    private final ZonedDateTime to = ZonedDateTime
            .ofInstant(Instant.ofEpochSecond(1511992800L), ZoneOffset.ofHours(2));

    private final String cronExpression = "0 0 1 ? * MON-FRI";
    private final EventActionHelper helper = new EventActionHelper(cityId, from, to);
    private final ExecutionTime cron = ExecutionTime.forCron(parser.parse(cronExpression));

    @Test
    @SuppressWarnings("unchecked")
    public void getSetActiveDaysInCronTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = EventActionHelper.class
                .getDeclaredMethod("getSetActiveDaysInCron", ExecutionTime.class, Duration.class);
        method.setAccessible(true);

        val stream = (Stream<ZonedDateTime>) method.invoke(helper, cron, Duration.ofHours(2L));
        val list = stream.collect(Collectors.toList());
        Assert.assertTrue(list.size() == 5);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getSetActiveDaysTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = EventActionHelper.class
                .getDeclaredMethod("getSetActiveDays", SimpleEvent.class);
        method.setAccessible(true);

        val set = (Set<ZonedDateTime>) method.invoke(helper, getSimpleEvent());
        Assert.assertTrue(set.size() == 4);
    }

    private SimpleEvent getSimpleEvent() {
        val simpleEvent = new SimpleEvent();
        val schedule = new ActionSchedule();

        schedule.setCron(cronExpression);
        schedule.setDuration(Duration.ofHours(2));
        schedule.setSimpleEvent(simpleEvent);

        val city = new City();
        city.setId(cityId);
        schedule.setCity(city);

        simpleEvent.getActionSchedules().add(schedule);
        simpleEvent.getNoneAction().add(from.plusDays(4));
        simpleEvent.getDatesException().add(from.plusDays(5).plusHours(10).plusMinutes(30));
        return simpleEvent;
    }
}
