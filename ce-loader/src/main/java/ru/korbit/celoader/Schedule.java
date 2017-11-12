package ru.korbit.celoader;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.redisson.RedissonNode;
import org.redisson.api.CronSchedule;
import org.redisson.api.RedissonClient;
import org.redisson.api.map.event.EntryExpiredListener;
import org.redisson.config.RedissonNodeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.korbit.cecommon.dao.EventDao;
import ru.korbit.cecommon.dao.ShowtimeDao;
import ru.korbit.cecommon.store.cacheregions.RamblerCacheRegion;
import ru.korbit.celoader.tasks.RamblerTask;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;

/**
 * Created by Artur Belogur on 09.11.17.
 */
@Component
@Slf4j
public class Schedule {

    private final RedissonClient redissonClient;

    private final EventDao eventDao;
    private final ShowtimeDao showtimeDao;

    @Autowired
    public Schedule(RedissonClient redissonClient,
                    EventDao eventDao,
                    ShowtimeDao showtimeDao) {
        this.redissonClient = redissonClient;
        this.eventDao = eventDao;
        this.showtimeDao = showtimeDao;

        initCleaners();
    }

    void run() {
        RedissonNodeConfig nodeConfig = new RedissonNodeConfig(redissonClient.getConfig());
        nodeConfig.setExecutorServiceWorkers(
                Collections.singletonMap(LoaderConstants.EXECUTOR_NAME, LoaderConstants.NUMBER_RESOURCES));
        RedissonNode node = RedissonNode.create(nodeConfig);
        node.start();

        log.debug("Start loading at {}", LocalDateTime.now());

        val executorService = redissonClient.getExecutorService(LoaderConstants.EXECUTOR_NAME);
        executorService.schedule(new RamblerTask(), CronSchedule.dailyAtHourAndMinute(23, 55));
        //TODO change to weeklyOnDayAndHourAndMinute with WED
//        executorService.schedule(new RamblerTask(), CronSchedule.dailyAtHourAndMinute(23, 55));
    }

    private void initCleaners() {
        redissonClient.getMapCache(RamblerCacheRegion.EVENT.getRegion())
                .addListener((EntryExpiredListener<Object, Serializable>) entryEvent -> {
                    val event = eventDao.get(entryEvent.getValue());
                    if (event.isPresent()) {
                        eventDao.delete(event.get());
                        log.info("Expire: {}", event);
                    }
                    else {
                        log.error("Expire fail: event not exist, id = {}", entryEvent.getValue());
                    }
                });

        redissonClient.getMapCache(RamblerCacheRegion.SHOWTIME.getRegion())
                .addListener((EntryExpiredListener<Object, Serializable>) entryEvent -> {
                    val showtime = showtimeDao.get(entryEvent.getValue());
                    if (showtime.isPresent()) {
                        showtimeDao.delete(showtime.get());
                        log.info("Expire: {}", showtime);
                    }
                    else {
                        log.error("Expire fail: showtime not exist {}", entryEvent.getValue());
                    }
                });
    }
}
