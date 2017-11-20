package ru.korbit.celoader.tasks;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.redisson.api.RedissonClient;
import org.redisson.api.annotation.RInject;
import ru.korbit.cecommon.config.Constants;
import ru.korbit.cecommon.services.RamblerKassaAsyncService;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Artur Belogur on 09.11.17.
 */

@Slf4j
@NoArgsConstructor
public class RamblerTask implements Runnable, Serializable {

    @RInject
    private RedissonClient redissonClient;

    @Override
    public void run() {
        try {
            val startTime = LocalDateTime.now();
            log.info("Start load Rambler.Kassa data");
            redissonClient.getRemoteService(Constants.QUEUE_NAME)
                    .get(RamblerKassaAsyncService.class, 1, TimeUnit.HOURS).load().get();
            log.info("Load Rambler.Kassa successful. Load time = {}", Duration.between(startTime, LocalDateTime.now()));
        }
        catch (NullPointerException e) {
            log.error("Rambler Kassa loader wasn't run");
        }
        catch (InterruptedException | ExecutionException e) {
            log.error("Load Rambler.Kassa fail", e);
        }
    }
}
