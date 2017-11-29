package ru.korbit.ceadmin.controllers;

import com.cronutils.model.time.ExecutionTime;
import com.fasterxml.jackson.databind.ObjectMapper;
import jodd.io.FileNameUtil;
import jodd.util.RandomString;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.redisson.executor.CronExpression;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.korbit.ceadmin.converters.ActionScheduleConverter;
import ru.korbit.ceadmin.converters.RecurringEventConverter;
import ru.korbit.ceadmin.dto.RecurringEventDto;
import ru.korbit.cecommon.config.Constants;
import ru.korbit.cecommon.dao.*;
import ru.korbit.cecommon.domain.EventSchedule;
import ru.korbit.cecommon.exeptions.BadRequest;
import ru.korbit.cecommon.store.cacheregions.AdminCacheRegion;
import ru.korbit.cecommon.store.impl.EventStoreHelper;
import ru.korbit.cecommon.utility.DateTimeUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;

@RestController
@Slf4j
@Transactional
public class EventController extends SessionController {

    private final ObjectMapper mapper = new ObjectMapper();

    private final EventDao eventDao;
    private final CityDao cityDao;
    private final ActionScheduleDao actionScheduleDao;
    private final EventScheduleDao eventScheduleDao;
    private final EventStoreHelper eventStoreHelper;
    private final EventTypeDao eventTypeDao;

    protected EventController(UserDao userDao,
                              EventDao eventDao,
                              CityDao cityDao, ActionScheduleDao actionScheduleDao,
                              EventScheduleDao eventScheduleDao,
                              EventStoreHelper eventStoreHelper, EventTypeDao eventTypeDao) {
        super(userDao);
        this.eventDao = eventDao;
        this.cityDao = cityDao;
        this.actionScheduleDao = actionScheduleDao;
        this.eventScheduleDao = eventScheduleDao;
        this.eventStoreHelper = eventStoreHelper;
        this.eventTypeDao = eventTypeDao;
    }

    @PostMapping(value = "admin/create-recurring-event")
    public ResponseEntity<?> createRecurringEvent(HttpServletRequest request,
                                                  @RequestParam("event") MultipartFile json,
                                                  @RequestParam("image") MultipartFile image) throws IOException {
        val user = getSessionUser(request);

        val eventConverter = new RecurringEventConverter();
        val actionScheduleConverter = new ActionScheduleConverter();

        val recurringEventDto = mapper.readValue(json.getInputStream(), RecurringEventDto.class);
        val recurringEvent = eventConverter.convertFromDto(recurringEventDto);

        eventDao.save(recurringEvent);
        recurringEventDto.getEventTypes()
                .forEach(et -> {
                    val eventType = eventTypeDao.get(et.getId())
                            .orElseThrow(() -> new BadRequest("Event type is not exist"));
                    recurringEvent.getEventTypes().add(eventType);
                    eventType.getEvents().add(recurringEvent);
                    eventTypeDao.update(eventType);
                });
        recurringEvent.setOrganisation(user.getOrganisation());

        recurringEventDto.getActionSchedules()
                .forEach(actionScheduleDto -> {
                    if (!CronExpression.isValidExpression(actionScheduleDto.getCron())) {
                        throw new BadRequest("Invalid cron expression");
                    }
                    val city = cityDao.get(actionScheduleDto.getCity().getId())
                            .orElseThrow(() -> new BadRequest("City is not exist"));

                    val actionSchedule = actionScheduleConverter.convertFromDto(actionScheduleDto);
                    actionSchedule.setRecurringEvent(recurringEvent);
                    actionSchedule.setCity(city);
                    actionScheduleDao.save(actionSchedule);
                    recurringEvent.getActionSchedules().add(actionSchedule);

                    val executionTime = ExecutionTime.forCron(DateTimeUtils.parser.parse(actionScheduleDto.getCron()));

                    val start = executionTime.nextExecution(ZonedDateTime.now(city.getZoneOffset()))
                            .orElseThrow(() -> new BadRequest("Cron expression haven't start"));
                    val finish = executionTime.lastExecution(ZonedDateTime.now(city.getZoneOffset()))
                            .orElseThrow(() -> new BadRequest("Cron expression haven't finish"));
                    val eventSchedule = new EventSchedule(recurringEvent, city);
                    eventSchedule.setStart(start);
                    eventSchedule.setFinish(finish);
                    eventScheduleDao.save(eventSchedule);
                    recurringEvent.getEventSchedules().add(eventSchedule);
                });

        val expire = recurringEvent.getEventSchedules()
                .stream()
                .max((s1, s2) -> s1.getFinish().isAfter(s2.getFinish()) ? 1 : -1)
                .map(date -> date.getFinish().toEpochSecond() * 1000)
                .orElseThrow(() -> new BadRequest("Event haven't expire"));

        eventStoreHelper.addToCache(
                recurringEvent.getId(),
                recurringEvent.getId(),
                AdminCacheRegion.ADMIN_EVENT,
                expire);

        val path = Constants.IMAGE_DIR +
                RandomString.getInstance().randomAlphaNumeric(10) + "." +
                FileNameUtil.getExtension(image.getOriginalFilename());
        val file = new File(path);
        if (ImageIO.read(image.getInputStream()) == null) {
            throw new BadRequest("File is not image");
        }
//        image.transferTo(file);
        recurringEvent.setImageURL(new URL(String.format(Constants.IMAGE_BASE_URL, file.getName())));

        eventDao.update(recurringEvent);
        return new ResponseEntity<>(eventConverter.convertFromEntity(recurringEvent), HttpStatus.CREATED);
    }
}
